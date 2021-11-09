package com.platform.kspace.repository;

import java.util.List;

import com.platform.kspace.model.Section;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Integer> {
    List<Section> findByTestId(Integer id);
}
