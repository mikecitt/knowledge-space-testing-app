package com.platform.kspace.service.impl;

import java.util.List;

import com.platform.kspace.model.KnowledgeSpace;
import com.platform.kspace.repository.KnowledgeSpaceRepository;
import com.platform.kspace.service.KnowledgeSpaceService;

import org.springframework.beans.factory.annotation.Autowired;

public class KnowledgeSpaceServiceImpl implements KnowledgeSpaceService {

    @Autowired
    private KnowledgeSpaceRepository knowledgeSpaceRepository;

    @Override
    public KnowledgeSpace save(KnowledgeSpace knowledgeSpace) {
        return knowledgeSpaceRepository.save(knowledgeSpace);
    }

    @Override
    public List<KnowledgeSpace> findAll() {
        return knowledgeSpaceRepository.findAll();
    }
    
}
