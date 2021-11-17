package com.platform.kspace.service;

import java.util.List;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.model.Item;

public interface ItemService {
    List<Item> findAll();
    List<ItemDTO> getItems();
    Item findOne(Integer id);
    Item save(Item item);
    ItemDTO addItem(ItemDTO dto, Integer sectionId);
    void deleteItem(Integer id);
}
