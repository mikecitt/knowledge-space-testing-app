package com.platform.kspace.repository;

import com.platform.kspace.model.TakenTest;
import com.platform.kspace.model.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;


public interface TakenTestRepository extends JpaRepository<TakenTest, Integer> {
    @Query(value = "SELECT TOP (1) * FROM TAKEN_TEST WHERE END IS NULL AND TAKEN_BY_ID = ?1",
            nativeQuery = true
    )
    TakenTest getUnfinishedTest(UUID userId);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TAKEN_TEST" +
            " WHERE TAKEN_BY_ID = ?1",
            nativeQuery = true
    )
    boolean checkIfTestIsAlreadyTaken(UUID userId);

    @Query(
            value = "SELECT TT.* FROM TAKEN_TEST TT JOIN TEST T ON T.ID = TT.TEST_ID" +
                    " AND T.NAME LIKE %?1% AND TT.TAKEN_BY_ID = ?2" +
                    " ORDER BY CASE WHEN TT.END IS NULL THEN 1 ELSE 2 END, TT.END DESC",
            countQuery = "SELECT TT.* FROM TAKEN_TEST TT JOIN TEST T ON T.ID = TT.TEST_ID" +
                    " AND T.NAME LIKE %?1% AND TT.TAKEN_BY_ID = ?2",
            nativeQuery = true
    )
    Page<TakenTest> searchTakenTestsByStudent(String searchKeyword, UUID userId, Pageable pageable);
}
