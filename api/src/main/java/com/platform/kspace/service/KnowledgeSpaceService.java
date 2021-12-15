package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.model.KnowledgeSpace;

public interface KnowledgeSpaceService {
    KnowledgeSpace save(KnowledgeSpace knowledgeSpace);
    List<KnowledgeSpace> findAll();
}
