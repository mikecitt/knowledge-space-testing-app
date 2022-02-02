package com.platform.kspace.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.platform.kspace.dto.DomainDTO;
import com.platform.kspace.model.Domain;

public class DomainMapper implements MapperInterface<Domain, DomainDTO> {

    @Override
    public Domain toEntity(DomainDTO dto) {
        return new Domain(dto.getText());
    }

    @Override
    public List<Domain> toEntityList(List<DomainDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public DomainDTO toDto(Domain entity) {
        if (entity == null) {
            return null;
        }
        return new DomainDTO(entity.getId(), entity.getName());
    }

    @Override
    public List<DomainDTO> toDtoList(List<Domain> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
