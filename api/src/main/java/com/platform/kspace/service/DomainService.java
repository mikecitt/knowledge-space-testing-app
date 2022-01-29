package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.dto.DomainDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.model.Domain;

public interface DomainService {
    Domain save(Domain domain);
    DomainDTO addDomain(DomainDTO dto);
    DomainDTO updateDomain(DomainDTO newDto, Integer id) throws KSpaceException;
    void deleteDomain(Integer id) throws KSpaceException;
    List<Domain> findAll();
    List<DomainDTO> getDomains();
    List<DomainDTO> getUnassignedDomains();
}
