package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/item")
public class ItemController {
    
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDTO> addItem(@RequestBody ItemDTO dto, @RequestParam Integer sectionId) {
        try {
            return ResponseEntity.ok(itemService.addItem(dto, sectionId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@RequestBody ItemDTO dto, @PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(itemService.updateItem(dto, id));
        } catch (KSpaceException kse) {
            return new ResponseEntity<>(kse.getMessage(), kse.getHttpStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable("id") Integer id) {
        if(itemService.findOne(id) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getItems() {
        return ResponseEntity.ok(itemService.getItems());
    }
}
