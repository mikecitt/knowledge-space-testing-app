package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.dto.DomainDTO;
import com.platform.kspace.model.Domain;

public interface DomainService {
    Domain save(Domain domain);
    DomainDTO addDomain(DomainDTO dto);
    List<Domain> findAll();
    List<DomainDTO> getDomains();
}
