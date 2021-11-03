package com.platform.kspace.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.platform.kspace.dto.TestDTO;
import com.platform.kspace.model.Test;

public class TestMapper implements MapperInterface<Test, TestDTO> {

    @Override
    public Test toEntity(TestDTO dto) {
        return new Test(dto.getName(), dto.getTimer(), dto.getValidFrom(), dto.getValidUntil());
    }

    @Override
    public List<Test> toEntityList(List<TestDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public TestDTO toDto(Test entity) {
        return new TestDTO(
            entity.getId(),
            entity.getName(),
            entity.getTimer(),
            entity.getValidFrom(),
            entity.getValidUntil(),
            entity.getCreatedBy().getId()
        );
    }

    @Override
    public List<TestDTO> toDtoList(List<Test> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
