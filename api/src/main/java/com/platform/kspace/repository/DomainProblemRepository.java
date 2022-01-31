package com.platform.kspace.repository;

import com.platform.kspace.model.DomainProblem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DomainProblemRepository extends JpaRepository<DomainProblem, Integer> {
    @Query(
            value = "SELECT DISTINCT dp.* FROM KNOWLEDGE_SPACE ks " +
                    "JOIN EDGE e ON e.KNOWLEDGE_SPACE_ID = ks.ID " +
                    "JOIN DOMAIN_PROBLEM dp ON dp.ID = e.FROM_ID OR dp.ID = e.TO_ID " +
                    "WHERE ks.ID = ?1",
            nativeQuery = true
    )
    List<DomainProblem> findKnowledgeSpaceDomainProblems(Integer kSpaceId);
}
