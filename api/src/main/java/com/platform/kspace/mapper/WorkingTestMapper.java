package com.platform.kspace.mapper;

import com.platform.kspace.dto.WorkingTestDTO;
import com.platform.kspace.model.TakenTest;
import org.hibernate.cfg.NotYetImplementedException;

import java.util.List;
import java.util.stream.Collectors;

public class WorkingTestMapper implements MapperInterface<TakenTest, WorkingTestDTO>{

    @Override
    public TakenTest toEntity(WorkingTestDTO dto) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<TakenTest> toEntityList(List<WorkingTestDTO> dtoList) {
        throw new NotYetImplementedException();
    }

    @Override
    public WorkingTestDTO toDto(TakenTest entity) {
        return new WorkingTestDTO(
                entity.getId(),
                entity.getStart(),
                entity.getEnd()
        );
    }

    @Override
    public List<WorkingTestDTO> toDtoList(List<TakenTest> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
