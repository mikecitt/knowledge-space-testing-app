package com.platform.kspace.repository;

import com.platform.kspace.model.DomainProblem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainProblemRepository extends JpaRepository<DomainProblem, Integer> {
    
}
