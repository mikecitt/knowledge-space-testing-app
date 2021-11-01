package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.model.Test;
import com.platform.kspace.repository.TestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    
    @Autowired
    private TestRepository testRepository;

    public List<Test> findAll() {
        return (List<Test>)testRepository.findAll();
    }

    public Test findOne(int id) {
        return testRepository.findById(id).orElseGet(null);
    }

    public Test save(Test test) {
        return testRepository.save(test);
    }
}
