package com.platform.kspace.mapper;

import com.platform.kspace.dto.PossibleAnswerDTO;
import com.platform.kspace.model.Answer;
import org.hibernate.cfg.NotYetImplementedException;

import java.util.List;
import java.util.stream.Collectors;

public class PossibleAnswerMapper implements MapperInterface<Answer, PossibleAnswerDTO> {
    @Override
    public Answer toEntity(PossibleAnswerDTO dto) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Answer> toEntityList(List<PossibleAnswerDTO> dtoList) {
        throw new NotYetImplementedException();
    }

    @Override
    public PossibleAnswerDTO toDto(Answer entity) {
        return new PossibleAnswerDTO(
                entity.getId(),
                entity.getText()
        );
    }

    @Override
    public List<PossibleAnswerDTO> toDtoList(List<Answer> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
