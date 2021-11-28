package com.platform.kspace.mapper;

import com.platform.kspace.dto.TakenTestDTO;
import com.platform.kspace.model.Answer;
import com.platform.kspace.model.TakenTest;
import org.hibernate.cfg.NotYetImplementedException;

import java.util.List;
import java.util.stream.Collectors;

public class TakenTestMapper implements MapperInterface<TakenTest, TakenTestDTO>{
    @Override
    public TakenTest toEntity(TakenTestDTO dto) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<TakenTest> toEntityList(List<TakenTestDTO> dtoList) {
        throw new NotYetImplementedException();
    }

    @Override
    public TakenTestDTO toDto(TakenTest entity) {
        double score = entity.getAnswers().stream().mapToDouble(Answer::getPoints).sum();
        return new TakenTestDTO(
                entity.getId(),
                entity.getTest().getName(),
                entity.getTest().getTimer(),
                entity.getStart(),
                entity.getEnd(),
                score
        );
    }

    @Override
    public List<TakenTestDTO> toDtoList(List<TakenTest> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
