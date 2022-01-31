package com.platform.kspace.mapper;

import com.platform.kspace.dto.StudentItemDTO;
import com.platform.kspace.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class StudentItemMapper implements MapperInterface<Item, StudentItemDTO> {

    private PossibleAnswerMapper possibleAnswerMapper;

    public StudentItemMapper() {
        possibleAnswerMapper = new PossibleAnswerMapper();
    }

    @Override
    public Item toEntity(StudentItemDTO dto) {
        return new Item(
                dto.getText(),
                dto.getImgName()
        );
    }

    @Override
    public List<Item> toEntityList(List<StudentItemDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public StudentItemDTO toDto(Item entity) {
        return new StudentItemDTO(
                entity.getId(),
                entity.getText(),
                entity.getImgName(),
                possibleAnswerMapper.toDtoList(entity.getAnswers()),
                entity.getSection().getId()
        );
    }

    @Override
    public List<StudentItemDTO> toDtoList(List<Item> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
