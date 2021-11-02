package com.platform.kspace.repository;

import com.platform.kspace.model.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "SELECT I.* FROM Item I " +
            "JOIN Section S ON I.SECTION_ID = S.ID " +
            "JOIN Test T ON T.ID = S.TEST_ID AND T.ID = ?1 ORDER BY I.SECTION_ID",
            nativeQuery = true
    )
    List<Item> findAllTestItems(Integer testId);
}
