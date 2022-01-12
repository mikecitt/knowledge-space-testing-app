package com.platform.kspace.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.platform.kspace.dto.KnowledgeSpaceDTO;
import com.platform.kspace.model.KnowledgeSpace;

public class KnowledgeSpaceMapper implements MapperInterface<KnowledgeSpace, KnowledgeSpaceDTO> {

    private EdgeMapper edgeMapper;

    public KnowledgeSpaceMapper() {
        this.edgeMapper = new EdgeMapper();
    }

    @Override
    public KnowledgeSpace toEntity(KnowledgeSpaceDTO dto) {
        KnowledgeSpace knowledgeSpace = new KnowledgeSpace(
                dto.getName(),
                dto.getIsReal(),
                edgeMapper.toEntityList(dto.getEdges())
        );
        knowledgeSpace.getEdges().forEach(x -> x.setKnowledgeSpace(knowledgeSpace));
        return knowledgeSpace;
    }

    @Override
    public List<KnowledgeSpace> toEntityList(List<KnowledgeSpaceDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public KnowledgeSpaceDTO toDto(KnowledgeSpace entity) {
        return new KnowledgeSpaceDTO(entity.getId(), entity.getName(), entity.getIsReal(), edgeMapper.toDtoList(entity.getEdges()));
    }

    @Override
    public List<KnowledgeSpaceDTO> toDtoList(List<KnowledgeSpace> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
