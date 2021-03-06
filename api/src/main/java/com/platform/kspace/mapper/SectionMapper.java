package com.platform.kspace.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.platform.kspace.dto.SectionDTO;
import com.platform.kspace.model.Section;

public class SectionMapper implements MapperInterface<Section, SectionDTO> {

    private ItemMapper itemMapper;

    public SectionMapper() {
        itemMapper = new ItemMapper();
    }

    @Override
    public Section toEntity(SectionDTO dto) {
        return new Section(dto.getName());
    }

    @Override
    public List<Section> toEntityList(List<SectionDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public SectionDTO toDto(Section entity) {
        return new SectionDTO(entity.getId(), entity.getName(), entity.getTest().getId(), itemMapper.toDtoList(entity.getItems()));
    }

    @Override
    public List<SectionDTO> toDtoList(List<Section> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
