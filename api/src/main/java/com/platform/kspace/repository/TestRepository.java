package com.platform.kspace.repository;

import com.platform.kspace.model.Test;

import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test, Integer> {
    
}
