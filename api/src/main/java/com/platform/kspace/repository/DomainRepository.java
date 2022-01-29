package com.platform.kspace.repository;

import com.platform.kspace.model.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain, Integer> {
    @Query(value = "SELECT d.* FROM DOMAIN d LEFT JOIN KNOWLEDGE_SPACE " +
            "ks ON ks.DOMAIN_ID = d.ID WHERE ks.Id IS NULL",
            nativeQuery = true
    )
    List<Domain> getNotAssignedDomains();

    @Modifying
    @Query(value = "DELETE FROM DOMAIN_PROBLEM dp WHERE dp.ID IN (" +
            "SELECT dp.ID FROM DOMAIN_PROBLEM dp " +
            "LEFT JOIN EDGE e ON e.FROM_ID = dp.ID OR e.TO_ID = dp.ID " +
            "WHERE e.FROM_ID IS NULL OR e.TO_ID IS NULL)",
            nativeQuery = true
    )
    void deleteUnusedDomainProblems();
}
