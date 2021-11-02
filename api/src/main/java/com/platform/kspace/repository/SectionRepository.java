package com.platform.kspace.repository;

import com.platform.kspace.model.Section;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Integer> {
    
}
