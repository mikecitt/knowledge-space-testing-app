package com.platform.kspace.repository;

import com.platform.kspace.model.DomainProblem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DomainProblemRepository extends JpaRepository<DomainProblem, Integer> {
    @Query(
            value = "SELECT DISTINCT dp.* FROM KNOWLEDGE_SPACE ks " +
                    "JOIN EDGE e ON e.KNOWLEDGE_SPACE_ID = ks.ID " +
                    "JOIN DOMAIN_PROBLEM dp ON dp.ID = e.FROM_ID OR dp.ID = e.TO_ID " +
                    "WHERE ks.ID = ?1",
            nativeQuery = true
    )
    List<DomainProblem> findKnowledgeSpaceDomainProblems(Integer kSpaceId);
    @Query(
            value = "SELECT dp.* FROM DOMAIN_PROBLEM dp " +
                    "WHERE dp.ID IN (SELECT FROM_ID FROM EDGE e WHERE e.KNOWLEDGE_SPACE_ID = ?1) " +
                    "AND dp.ID NOT IN (SELECT TO_ID FROM EDGE e WHERE e.KNOWLEDGE_SPACE_ID = ?1) " +
                    "ORDER BY dp.ID ASC",
            nativeQuery = true
    )
    DomainProblem findRootProblem(Integer knowledgeSpaceId);
    Optional<DomainProblem> findById(UUID id);
}
