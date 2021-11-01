package com.platform.kspace.repository;

import com.platform.kspace.model.Item;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    
}
