package com.platform.kspace.service.impl;

import java.util.List;

import com.platform.kspace.dto.DomainProblemDTO;
import com.platform.kspace.mapper.DomainProblemMapper;
import com.platform.kspace.model.DomainProblem;
import com.platform.kspace.repository.DomainProblemRepository;
import com.platform.kspace.service.DomainProblemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainProblemServiceImpl implements DomainProblemService {

    private final DomainProblemMapper domainProblemMapper;

    @Autowired
    private DomainProblemRepository domainProblemRepository;

    public DomainProblemServiceImpl() {
        this.domainProblemMapper = new DomainProblemMapper();
    }

    @Override
    public DomainProblem save(DomainProblem domainProblem) {
        return domainProblemRepository.save(domainProblem);
    }

    @Override
    public List<DomainProblem> findAll() {
        return domainProblemRepository.findAll();
    }

    @Override
    public DomainProblemDTO addDomainProblem(DomainProblemDTO dto) {
        return domainProblemMapper.toDto(domainProblemRepository.save(domainProblemMapper.toEntity(dto)));

    }

    @Override
    public List<DomainProblemDTO> getDomainProblems() {
        return domainProblemMapper.toDtoList(domainProblemRepository.findAll());

    }
    
}
