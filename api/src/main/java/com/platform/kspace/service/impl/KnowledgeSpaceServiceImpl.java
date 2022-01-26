package com.platform.kspace.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.platform.kspace.dto.KnowledgeSpaceDTO;
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
import org.springframework.stereotype.Service;

@Service
public class KnowledgeSpaceServiceImpl implements KnowledgeSpaceService {

    private KnowledgeSpaceMapper knowledgeSpaceMapper;

    @Autowired
    private KnowledgeSpaceRepository knowledgeSpaceRepository;

    @Autowired
    private DomainProblemRepository domainProblemRepository;

    @Autowired
    private DomainRepository domainRepository;

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
    public KnowledgeSpaceDTO addKnowledgeSpace(KnowledgeSpaceDTO dto) {
        KnowledgeSpace knowledgeSpace = knowledgeSpaceMapper.toEntity(dto);
        Domain domain = domainRepository.findById(dto.getDomain().getId()).orElse(null);
        // TODO: add exception for this
        if(domain == null)
            return null;
        knowledgeSpace.setDomain(domain);
        Set<DomainProblem> domainProblems = knowledgeSpace
                .getEdges()
                .stream()
                .map(Edge::getFrom)
                .collect(Collectors.toSet());
        domainProblems.addAll(knowledgeSpace
                .getEdges()
                .stream()
                .map(Edge::getTo)
                .collect(Collectors.toSet()));
        List<DomainProblem> saved = domainProblems.stream().map(x -> domainProblemRepository.save(x)).collect(Collectors.toList());
        for(Edge e : knowledgeSpace.getEdges()) {
            saved.stream()
                    .filter(x -> x.equals(e.getFrom()))
                    .findAny()
                    .ifPresent(e::setFrom);
            saved.stream()
                    .filter(x -> x.equals(e.getTo()))
                    .findAny()
                    .ifPresent(e::setTo);
        }

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

}
