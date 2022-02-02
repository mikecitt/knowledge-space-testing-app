package com.platform.kspace.repository;

import com.platform.kspace.model.Test;

import com.platform.kspace.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TestRepository extends JpaRepository<Test, Integer> {
    @Query(
            value = "SELECT DISTINCT t.* FROM TEST t " +
                    "JOIN DOMAIN d ON d.ID = t.DOMAIN_ID " +
                    "JOIN KNOWLEDGE_SPACE ks ON ks.DOMAIN_ID = d.ID " +
                    "JOIN EDGE e ON e.KNOWLEDGE_SPACE_ID = ks.ID " +
                    "JOIN DOMAIN_PROBLEM dp ON dp.ID = e.FROM_ID OR dp.ID = e.TO_ID " +
                    "JOIN ITEM i ON i.DOMAIN_PROBLEM_ID = dp.ID " +
                    "WHERE t.NAME LIKE %?1% " +
                    "AND t.ID NOT IN " +
                    "(SELECT TEST_ID FROM TAKEN_TEST WHERE TAKEN_BY_ID = ?2) " +
                    "ORDER BY t.ID ASC",
            countQuery =  "SELECT DISTINCT t.* FROM TEST t " +
                    "JOIN DOMAIN d ON d.ID = t.DOMAIN_ID " +
                    "JOIN KNOWLEDGE_SPACE ks ON ks.DOMAIN_ID = d.ID " +
                    "JOIN EDGE e ON e.KNOWLEDGE_SPACE_ID = ks.ID " +
                    "JOIN DOMAIN_PROBLEM dp ON dp.ID = e.FROM_ID OR dp.ID = e.TO_ID " +
                    "JOIN ITEM i ON i.DOMAIN_PROBLEM_ID = dp.ID " +
                    "WHERE t.NAME LIKE %?1% " +
                    "AND t.ID NOT IN " +
                    "(SELECT TEST_ID FROM TAKEN_TEST WHERE TAKEN_BY_ID = ?2)",
            nativeQuery = true
    )
    Page<Test> searchTests(String searchKeyword, UUID studentId, Pageable pageable);
    List<Test> findAllByCreatedBy(User createdBy);
}
