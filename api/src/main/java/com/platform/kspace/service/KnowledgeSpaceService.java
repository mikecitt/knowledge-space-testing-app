package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.dto.ItemProblemDTO;
import com.platform.kspace.dto.KnowledgeSpaceDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.model.KnowledgeSpace;

public interface KnowledgeSpaceService {
    KnowledgeSpace save(KnowledgeSpace knowledgeSpace);
    List<KnowledgeSpace> findAll();
    KnowledgeSpaceDTO addKnowledgeSpace(KnowledgeSpaceDTO dto) throws KSpaceException;
    List<KnowledgeSpaceDTO> getKnowledgeSpaces();
    List<KnowledgeSpaceDTO> getKnowledgeSpaces(Integer id);
    void deleteKnowledgeSpace(Integer id) throws KSpaceException;
    KnowledgeSpaceDTO updateKnowledgeSpace(KnowledgeSpaceDTO dto, Integer id) throws KSpaceException;
    List<ItemProblemDTO> getAllDomainProblemsFromKSpace(Integer kSpaceId, Integer testId);
}
