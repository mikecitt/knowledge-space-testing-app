package com.platform.kspace.repository;

import com.platform.kspace.model.Professor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    Professor getById(UUID id);
}
