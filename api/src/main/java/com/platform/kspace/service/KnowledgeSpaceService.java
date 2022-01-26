package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.dto.KnowledgeSpaceDTO;
import com.platform.kspace.model.KnowledgeSpace;

public interface KnowledgeSpaceService {
    KnowledgeSpace save(KnowledgeSpace knowledgeSpace);
    List<KnowledgeSpace> findAll();
    KnowledgeSpaceDTO addKnowledgeSpace(KnowledgeSpaceDTO dto);
    List<KnowledgeSpaceDTO> getKnowledgeSpaces();
    List<KnowledgeSpaceDTO> getKnowledgeSpaces(Integer id);
}
