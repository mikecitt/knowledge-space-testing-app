package com.platform.kspace.service.impl;

import java.util.List;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.mapper.AnswerMapper;
import com.platform.kspace.mapper.ItemMapper;
import com.platform.kspace.model.Item;
import com.platform.kspace.model.Section;
import com.platform.kspace.repository.ItemRepository;
import com.platform.kspace.repository.SectionRepository;
import com.platform.kspace.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SectionRepository sectionRepository;

    private final ItemMapper itemMapper;

    private final AnswerMapper answerMapper;

    public ItemServiceImpl() {
        this.itemMapper = new ItemMapper();
        this.answerMapper = new AnswerMapper();
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item findOne(Integer id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public ItemDTO addItem(ItemDTO dto, Integer sectionId) {
        Section section = sectionRepository.findById(sectionId).orElse(null);
        Item item = itemMapper.toEntity(dto);
        item.setSection(section);
        return itemMapper.toDto(itemRepository.save(item));
    }

    @Override
    public List<ItemDTO> getItems() {
        return itemMapper.toDtoList(itemRepository.findAll());
    }

    @Override
    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
        return;
    }

    @Override
    public ItemDTO updateItem(ItemDTO dto, Integer itemId) throws Exception {
        Item i = itemRepository.getById(itemId);
        if(i == null)
            throw new Exception("Section doesn't exist.");
        i.setText(dto.getText());
        i.setAnswers(answerMapper.toEntityList(dto.getAnswers()));
        i.fixAnswers();
        return itemMapper.toDto(itemRepository.save(i));
    }
    
}
