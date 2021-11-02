package com.platform.kspace.mapper;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper implements MapperInterface<Item, ItemDTO> {

    private PossibleAnswerMapper possibleAnswerMapper;

    public ItemMapper() {
        possibleAnswerMapper = new PossibleAnswerMapper();
    }

    @Override
    public Item toEntity(ItemDTO dto) {
        return new Item(
                dto.getText(),
                dto.getPicture()
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
                entity.getPicture(),
                possibleAnswerMapper.toDtoList(entity.getAnswers()),
                entity.getSection().getId()
        );
    }

    @Override
    public List<ItemDTO> toDtoList(List<Item> entityList) {
        return null;
    }
}
