package com.platform.kspace.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.platform.kspace.dto.KnowledgeSpaceDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.mapper.KnowledgeSpaceMapper;
import com.platform.kspace.model.Domain;
import com.platform.kspace.model.DomainProblem;
import com.platform.kspace.model.Edge;
import com.platform.kspace.model.KnowledgeSpace;
import com.platform.kspace.repository.DomainProblemRepository;
import com.platform.kspace.repository.DomainRepository;
import com.platform.kspace.repository.KnowledgeSpaceRepository;
import com.platform.kspace.service.KnowledgeSpaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class KnowledgeSpaceServiceImpl implements KnowledgeSpaceService {

    private final KnowledgeSpaceMapper knowledgeSpaceMapper;

    @Autowired
    private KnowledgeSpaceRepository knowledgeSpaceRepository;

    @Autowired
    private DomainProblemRepository domainProblemRepository;

    @Autowired
    private DomainRepository domainRepository;

    private List<DomainProblem> saveDomainsFromEdges(List<Edge> edges) {
        Set<DomainProblem> domainProblems = edges
                .stream()
                .map(Edge::getFrom)
                .collect(Collectors.toSet());
        domainProblems.addAll(edges
                .stream()
                .map(Edge::getTo)
                .collect(Collectors.toSet()));
        return domainProblems.stream().map(x -> domainProblemRepository.save(x)).collect(Collectors.toList());
    }

    private void updateEdgesWithLiveData(List<Edge> currentEdges, List<DomainProblem> liveData) {
        for(Edge e : currentEdges) {
            liveData.stream()
                    .filter(x -> x.equals(e.getFrom()))
                    .findAny()
                    .ifPresent(e::setFrom);
            liveData.stream()
                    .filter(x -> x.equals(e.getTo()))
                    .findAny()
                    .ifPresent(e::setTo);
        }
    }

    public KnowledgeSpaceServiceImpl() {
        this.knowledgeSpaceMapper = new KnowledgeSpaceMapper();
    }

    @Override
    public KnowledgeSpace save(KnowledgeSpace knowledgeSpace) {
        return knowledgeSpaceRepository.save(knowledgeSpace);
    }

    @Override
    public List<KnowledgeSpace> findAll() {
        return knowledgeSpaceRepository.findAll();
    }

    @Override
    public KnowledgeSpaceDTO addKnowledgeSpace(KnowledgeSpaceDTO dto) throws KSpaceException {
        KnowledgeSpace knowledgeSpace = knowledgeSpaceMapper.toEntity(dto);
        Optional<Domain> domain = domainRepository.findById(dto.getDomain().getId());
        if (domain.isEmpty()) {
            throw new KSpaceException(HttpStatus.BAD_REQUEST, "Given domain is not recognized.");
        }
        knowledgeSpace.setDomain(domain.get());
        List<DomainProblem> saved = saveDomainsFromEdges(knowledgeSpace.getEdges());
        updateEdgesWithLiveData(knowledgeSpace.getEdges(), saved);

        return knowledgeSpaceMapper.toDto(knowledgeSpaceRepository.save(knowledgeSpace));
    }

    @Override
    public List<KnowledgeSpaceDTO> getKnowledgeSpaces() {
        return knowledgeSpaceMapper.toDtoList(knowledgeSpaceRepository.findAll());
    }

    @Override
    public List<KnowledgeSpaceDTO> getKnowledgeSpaces(Integer id) {
        return knowledgeSpaceMapper.toDtoList(knowledgeSpaceRepository.findByDomainId(id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteKnowledgeSpace(Integer id) throws KSpaceException {
        Optional<KnowledgeSpace> ks = knowledgeSpaceRepository.findById(id);

        if (ks.isEmpty()) {
            throw new KSpaceException(HttpStatus.NOT_FOUND, "Knowledge space not found with given id.");
        }

        knowledgeSpaceRepository.delete(ks.get());
        domainRepository.deleteUnusedDomainProblems();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public KnowledgeSpaceDTO updateKnowledgeSpace(KnowledgeSpaceDTO dto, Integer id) throws KSpaceException {
        Optional<KnowledgeSpace> ks = knowledgeSpaceRepository.findById(id);
        KnowledgeSpace knowledgeSpaceNew = knowledgeSpaceMapper.toEntity(dto);

        if (ks.isEmpty()) {
            throw new KSpaceException(HttpStatus.NOT_FOUND, "Knowledge space not found with given id.");
        }

        Optional<Domain> domain = domainRepository.findById(dto.getDomain().getId());
        if (domain.isEmpty()) {
            throw new KSpaceException(HttpStatus.BAD_REQUEST, "Given domain is not recognized.");
        }
        ks.get().setDomain(domain.get());
        ks.get().setName(dto.getName());
        ks.get().setDomain(domain.get());
        List<Edge> newEdges = new ArrayList<>();
        List<Edge> oldEdges = new ArrayList<>();
        for(Edge e : knowledgeSpaceNew.getEdges()) {
            if (!ks.get().getEdges().contains(e)) {
                e.setKnowledgeSpace(ks.get());
                newEdges.add(e);
            } else {
                ks.get().getEdges().stream()
                        .filter(x -> x.equals(e))
                        .findAny()
                        .ifPresent(oldEdges::add);
            }
        }
        updateEdgesWithLiveData(newEdges, saveDomainsFromEdges(newEdges));
        ks.get().getEdges().removeIf(x -> !oldEdges.contains(x));
        ks.get().getEdges().addAll(newEdges);
        KnowledgeSpace saved = knowledgeSpaceRepository.save(ks.get());
        domainRepository.deleteUnusedDomainProblems();
        return knowledgeSpaceMapper.toDto(saved);
    }

}
