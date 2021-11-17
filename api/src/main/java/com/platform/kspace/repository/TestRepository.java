package com.platform.kspace.repository;

import com.platform.kspace.model.Test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestRepository extends JpaRepository<Test, Integer> {
    @Query(value = "SELECT * FROM Test T WHERE T.NAME LIKE %?1%",
            countQuery = "SELECT * FROM Test T WHERE T.NAME LIKE %?1%",
            nativeQuery = true
    )
    Page<Test> searchTests(String searchKeyword, Pageable pageable);
}
