package com.platform.kspace.service.impl;

import java.util.List;

import com.platform.kspace.dto.KnowledgeSpaceDTO;
import com.platform.kspace.mapper.KnowledgeSpaceMapper;
import com.platform.kspace.model.Domain;
import com.platform.kspace.model.KnowledgeSpace;
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
    public KnowledgeSpaceDTO addKnowledgeSpace(KnowledgeSpaceDTO dto, Integer domainId) {
        KnowledgeSpace knowledgeSpace = knowledgeSpaceMapper.toEntity(dto);
        Domain domain = domainRepository.findById(domainId).orElse(null);
        if(domain == null)
            return null; 
        knowledgeSpace.setDomain(domain);
        return knowledgeSpaceMapper.toDto(knowledgeSpaceRepository.save(knowledgeSpace));
    }

    @Override
    public List<KnowledgeSpaceDTO> getKnowledgeSpaces() {
        return knowledgeSpaceMapper.toDtoList(knowledgeSpaceRepository.findAll());
    }
    
}
