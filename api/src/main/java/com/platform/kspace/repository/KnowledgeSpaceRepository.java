package com.platform.kspace.repository;

import java.util.List;
import java.util.Optional;

import com.platform.kspace.model.Domain;
import com.platform.kspace.model.KnowledgeSpace;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeSpaceRepository extends JpaRepository<KnowledgeSpace, Integer> {
    List<KnowledgeSpace> findByDomainId(Integer id);
    Optional<KnowledgeSpace> findByIsRealAndDomain(boolean isReal, Domain domain);
}
