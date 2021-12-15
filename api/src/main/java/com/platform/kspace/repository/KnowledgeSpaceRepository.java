package com.platform.kspace.repository;

import com.platform.kspace.model.KnowledgeSpace;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeSpaceRepository extends JpaRepository<KnowledgeSpace, Integer> {
    
}
