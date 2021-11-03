package com.platform.kspace.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.platform.kspace.dto.SectionDTO;
import com.platform.kspace.model.Section;

import org.hibernate.cfg.NotYetImplementedException;

public class SectionMapper implements MapperInterface<Section, SectionDTO> {

    @Override
    public Section toEntity(SectionDTO dto) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Section> toEntityList(List<SectionDTO> dtoList) {
        throw new NotYetImplementedException();
    }

    @Override
    public SectionDTO toDto(Section entity) {
        return new SectionDTO(entity.getId(), entity.getName(), entity.getTest().getId());
    }

    @Override
    public List<SectionDTO> toDtoList(List<Section> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
