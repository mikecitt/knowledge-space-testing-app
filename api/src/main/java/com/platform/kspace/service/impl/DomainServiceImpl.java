package com.platform.kspace.service.impl;

import java.util.List;
import java.util.Optional;

import com.platform.kspace.dto.DomainDTO;
import com.platform.kspace.dto.KnowledgeSpaceComparisonDTO;
import com.platform.kspace.dto.SpaceType;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.mapper.DomainMapper;
import com.platform.kspace.model.Domain;
import com.platform.kspace.model.Edge;
import com.platform.kspace.model.KnowledgeSpace;
import com.platform.kspace.repository.DomainRepository;
import com.platform.kspace.service.DomainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainService {

    private final DomainMapper domainMapper;

    @Autowired
    private DomainRepository domainRepository;

    public DomainServiceImpl() {
        this.domainMapper = new DomainMapper();
    }

    @Override
    public Domain save(Domain domain) {
        return domainRepository.save(domain);
    }

    @Override
    public List<Domain> findAll() {
        return domainRepository.findAll();
    }

    @Override
    public DomainDTO addDomain(DomainDTO dto) {
        return domainMapper.toDto(domainRepository.save(domainMapper.toEntity(dto)));
    }

    @Override
    public DomainDTO updateDomain(DomainDTO newDto, Integer id) throws KSpaceException {
        Optional<Domain> domain = domainRepository.findById(id);

        if (domain.isEmpty()) {
            throw new KSpaceException(HttpStatus.NOT_FOUND, "Domain not found with given id.");
        }

        domain.get().setName(newDto.getText());
        return domainMapper.toDto(domainRepository.save(domain.get()));
    }

    @Override
    public void deleteDomain(Integer id) throws KSpaceException {
        Optional<Domain> domain = domainRepository.findById(id);

        if (domain.isEmpty()) {
            throw new KSpaceException(HttpStatus.NOT_FOUND, "Domain not found with given id.");
        }

        domainRepository.delete(domain.get());
    }

    @Override
    public List<DomainDTO> getDomains() {
        return domainMapper.toDtoList(domainRepository.findAll());
    }

    @Override
    public List<DomainDTO> getUnassignedDomains() {
        return domainMapper.toDtoList(domainRepository.getNotAssignedDomains());
    }

    @Override
    public KnowledgeSpaceComparisonDTO getDomainKnowledgeSpaceComparison(Integer id) throws KSpaceException {
        Optional<Domain> domain = domainRepository.findById(id);

        if (domain.isEmpty()) {
            throw new KSpaceException(HttpStatus.NOT_FOUND, "Domain not found with given id.");
        }

        if(domain.get().getKnowledgeSpaces().size() < 2)
            throw new KSpaceException(HttpStatus.NOT_FOUND, "Domain doesn't have both expected and real knowledge space.");

        KnowledgeSpaceComparisonDTO result = new KnowledgeSpaceComparisonDTO();
        KnowledgeSpace first = domain.get().getKnowledgeSpaces().iterator().next();
        SpaceType type = SpaceType.EXPECTED;
        if(first.getIsReal())
            type = SpaceType.REAL;
        for(Edge e : first.getEdges()) {
            result.addNode(e.getFrom().getId(), e.getFrom().getName(), type);
            result.addNode(e.getTo().getId(), e.getTo().getName(), type);
            result.addEdge(e.getFrom().getId(), e.getTo().getId(), type);
        }

        if(type == SpaceType.EXPECTED)
            type = SpaceType.REAL;
        else
            type = SpaceType.EXPECTED;
        KnowledgeSpace second = domain.get().getKnowledgeSpaces().iterator().next();
        for(Edge e : second.getEdges()) {
            result.addNode(e.getFrom().getId(), e.getFrom().getName(), type);
            result.addNode(e.getTo().getId(), e.getTo().getName(), type);
            result.addEdge(e.getFrom().getId(), e.getTo().getId(), type);
        }

        return result;
    }

}
