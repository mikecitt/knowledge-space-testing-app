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
            value = "SELECT * FROM TEST WHERE NAME LIKE %?1% AND ID NOT IN" +
                    " (SELECT TEST_ID FROM TAKEN_TEST WHERE TAKEN_BY_ID = ?2)",
            countQuery = "SELECT * FROM TEST WHERE NAME LIKE %?1% AND ID NOT " +
                    " (SELECT TEST_ID FROM TAKEN_TEST WHERE TAKEN_BY_ID = ?2)",
            nativeQuery = true
    )
    Page<Test> searchTests(String searchKeyword, UUID studentId, Pageable pageable);
    List<Test> findAllByCreatedBy(User createdBy);
}
