package com.platform.kspace.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.platform.kspace.dto.EdgeDTO;
import com.platform.kspace.model.Edge;

public class EdgeMapper implements MapperInterface<Edge, EdgeDTO> {

    private DomainProblemMapper domainProblemMapper;

    public EdgeMapper() {
        domainProblemMapper = new DomainProblemMapper();
    }

    @Override
    public Edge toEntity(EdgeDTO dto) {
        return new Edge(domainProblemMapper.toEntity(dto.getFrom()), domainProblemMapper.toEntity(dto.getTo()));
    }

    @Override
    public List<Edge> toEntityList(List<EdgeDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public EdgeDTO toDto(Edge entity) {
        return new EdgeDTO(domainProblemMapper.toDto(entity.getFrom()), domainProblemMapper.toDto(entity.getTo()));
    }

    @Override
    public List<EdgeDTO> toDtoList(List<Edge> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());

    }
    
}
