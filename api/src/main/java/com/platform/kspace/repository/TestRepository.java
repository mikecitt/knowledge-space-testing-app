package com.platform.kspace.repository;

import com.platform.kspace.model.Test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
    
}
