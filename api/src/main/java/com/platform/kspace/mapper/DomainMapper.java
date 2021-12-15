package com.platform.kspace.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.platform.kspace.dto.DomainDTO;
import com.platform.kspace.model.Domain;

public class DomainMapper implements MapperInterface<Domain, DomainDTO> {

    private DomainProblemMapper domainProblemMapper;

    public DomainMapper() {
        domainProblemMapper = new DomainProblemMapper();
    }

    @Override
    public Domain toEntity(DomainDTO dto) {
        return new Domain(dto.getId(), dto.getText(), domainProblemMapper.toEntityList(dto.getDomainProblems()));
    }

    @Override
    public List<Domain> toEntityList(List<DomainDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public DomainDTO toDto(Domain entity) {
        return new DomainDTO(entity.getId(), entity.getName(), domainProblemMapper.toDtoList(entity.getDomainProblems()));
    }

    @Override
    public List<DomainDTO> toDtoList(List<Domain> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
