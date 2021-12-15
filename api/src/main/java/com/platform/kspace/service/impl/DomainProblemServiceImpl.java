package com.platform.kspace.service.impl;

import java.util.List;

import com.platform.kspace.model.DomainProblem;
import com.platform.kspace.repository.DomainProblemRepository;
import com.platform.kspace.service.DomainProblemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainProblemServiceImpl implements DomainProblemService {

    @Autowired
    private DomainProblemRepository domainProblemRepository;

    @Override
    public DomainProblem save(DomainProblem domainProblem) {
        return domainProblemRepository.save(domainProblem);
    }

    @Override
    public List<DomainProblem> findAll() {
        return domainProblemRepository.findAll();
    }
    
}
