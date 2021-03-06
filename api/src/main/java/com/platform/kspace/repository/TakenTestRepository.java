package com.platform.kspace.repository;

import com.platform.kspace.model.TakenTest;
import com.platform.kspace.model.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


public interface TakenTestRepository extends JpaRepository<TakenTest, Integer> {
    @Query(value = "SELECT TOP (1) * FROM TAKEN_TEST WHERE END IS NULL AND TAKEN_BY_ID = ?1",
            nativeQuery = true
    )
    TakenTest getUnfinishedTest(UUID userId);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM TAKEN_TEST" +
            " WHERE TAKEN_BY_ID = ?1 AND END IS NULL",
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

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM TAKEN_TEST_ANSWERS WHERE ANSWERS_ID IN " +
                    "(SELECT ID FROM ANSWER WHERE ITEM_ID = ?1) AND TAKEN_TEST_ID = ?2",
            nativeQuery = true
    )
    void deleteAnswersOfItem(Integer itemId, Integer takenTestId);
    List<TakenTest> findAllByTest(Test test);
    @Query(
            value = "SELECT DISTINCT tt.* FROM TAKEN_TEST tt " +
                    "JOIN TEST t ON tt.TEST_ID = t.ID " +
                    "JOIN DOMAIN d ON d.ID = t.DOMAIN_ID " +
                    "WHERE d.ID = ?1",
            nativeQuery = true
    )
    List<TakenTest> findAllByDomainId(Integer domainId);
    @Query(
            value = "SELECT COUNT(*) FROM TAKEN_TEST tt " +
                    "JOIN TEST t ON tt.TEST_ID = t.ID " +
                    "JOIN DOMAIN d ON d.ID = t.DOMAIN_ID " +
                    "WHERE d.ID = ?1",
            nativeQuery = true
    )
    int countTakenTestsForDomain(Integer domainId);
}
