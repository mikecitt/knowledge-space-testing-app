package com.platform.kspace.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.platform.kspace.dto.DomainProblemDTO;
import com.platform.kspace.model.DomainProblem;

public class DomainProblemMapper implements MapperInterface<DomainProblem, DomainProblemDTO> {

    @Override
    public DomainProblem toEntity(DomainProblemDTO dto) {
        return new DomainProblem(dto.getText());
    }

    @Override
    public List<DomainProblem> toEntityList(List<DomainProblemDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public DomainProblemDTO toDto(DomainProblem entity) {
        return new DomainProblemDTO(entity.getId(), entity.getName());
    }

    @Override
    public List<DomainProblemDTO> toDtoList(List<DomainProblem> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
