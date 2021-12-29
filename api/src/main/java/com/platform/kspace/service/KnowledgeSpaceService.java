package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.dto.KnowledgeSpaceDTO;
import com.platform.kspace.model.KnowledgeSpace;

public interface KnowledgeSpaceService {
    KnowledgeSpace save(KnowledgeSpace knowledgeSpace);
    List<KnowledgeSpace> findAll();
    KnowledgeSpaceDTO addKnowledgeSpace(KnowledgeSpaceDTO dto, Integer domainId);
    List<KnowledgeSpaceDTO> getKnowledgeSpaces();
}
