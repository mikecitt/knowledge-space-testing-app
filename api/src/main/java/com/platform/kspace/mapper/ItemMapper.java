package com.platform.kspace.mapper;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper implements MapperInterface<Item, ItemDTO> {

    private AnswerMapper answerMapper;

    public ItemMapper() {
        answerMapper = new AnswerMapper();
    }

    @Override
    public Item toEntity(ItemDTO dto) {
        return new Item(
                dto.getText(),
                dto.getImgName(),
                answerMapper.toEntityList(dto.getAnswers())
        );
    }

    @Override
    public List<Item> toEntityList(List<ItemDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public ItemDTO toDto(Item entity) {
        return new ItemDTO(
                entity.getId(),
                entity.getText(),
                entity.getImgName(),
                answerMapper.toDtoList(entity.getAnswers()),
                entity.getSection().getId()
        );
    }

    @Override
    public List<ItemDTO> toDtoList(List<Item> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
