package com.platform.kspace.mapper;

import com.platform.kspace.dto.AnswerDTO;
import com.platform.kspace.model.Answer;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerMapper implements MapperInterface<Answer, AnswerDTO> {
    @Override
    public Answer toEntity(AnswerDTO dto) {
        return new Answer(dto.getText(), dto.getPoints());
    }

    @Override
    public List<Answer> toEntityList(List<AnswerDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public AnswerDTO toDto(Answer entity) {
        return new AnswerDTO(
                entity.getId(),
                entity.getText(),
                entity.getPoints()
        );
    }

    @Override
    public List<AnswerDTO> toDtoList(List<Answer> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
