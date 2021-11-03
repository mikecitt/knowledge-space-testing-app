package com.platform.kspace.repository;

import com.platform.kspace.model.Professor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    
}
