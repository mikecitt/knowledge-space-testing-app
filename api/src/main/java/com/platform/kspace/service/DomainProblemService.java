package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.dto.DomainProblemDTO;
import com.platform.kspace.model.DomainProblem;

public interface DomainProblemService {
    DomainProblem save(DomainProblem domainProblem);
    List<DomainProblem> findAll();
    DomainProblemDTO addDomainProblem(DomainProblemDTO dto);
    List<DomainProblemDTO> getDomainProblems();
}
