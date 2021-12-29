package com.platform.kspace.repository;

import java.util.List;

import com.platform.kspace.model.KnowledgeSpace;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeSpaceRepository extends JpaRepository<KnowledgeSpace, Integer> {
    List<KnowledgeSpace> findByDomainId(Integer id);
}
