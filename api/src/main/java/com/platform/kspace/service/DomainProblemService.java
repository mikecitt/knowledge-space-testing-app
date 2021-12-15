package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.model.DomainProblem;

public interface DomainProblemService {
    DomainProblem save(DomainProblem domainProblem);
    List<DomainProblem> findAll();
}
