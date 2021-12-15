package com.platform.kspace.service.impl;

import java.util.List;

import com.platform.kspace.dto.DomainDTO;
import com.platform.kspace.mapper.DomainMapper;
import com.platform.kspace.model.Domain;
import com.platform.kspace.repository.DomainRepository;
import com.platform.kspace.service.DomainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainService {

    private DomainMapper domainMapper;

    @Autowired
    private DomainRepository domainRepository;

    public DomainServiceImpl() {
        this.domainMapper = new DomainMapper();
    }

    @Override
    public Domain save(Domain domain) {
        return domainRepository.save(domain);
    }

    @Override
    public List<Domain> findAll() {
        return domainRepository.findAll();
    }

    @Override
    public DomainDTO addDomain(DomainDTO dto) {
        return domainMapper.toDto(domainRepository.save(domainMapper.toEntity(dto)));
    }

    @Override
    public List<DomainDTO> getDomains() {
        return domainMapper.toDtoList(domainRepository.findAll());
    }
    
}
