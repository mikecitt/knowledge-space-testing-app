package com.platform.kspace.repository;

import com.platform.kspace.model.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain, Integer> {
    @Query(value = "SELECT d.* FROM DOMAIN d LEFT JOIN KNOWLEDGE_SPACE " +
            "ks ON ks.DOMAIN_ID = d.ID WHERE ks.Id IS NULL",
            nativeQuery = true
    )
    List<Domain> getNotAssignedDomains();
}
