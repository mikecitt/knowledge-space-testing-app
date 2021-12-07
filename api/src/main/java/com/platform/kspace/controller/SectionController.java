package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.dto.SectionDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.service.SectionService;

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
@RequestMapping("api/section")
public class SectionController {
    
    @Autowired
    private SectionService sectionService;

    @GetMapping
    public ResponseEntity<List<SectionDTO>> getTestSections(@RequestParam Integer testId) {
        return ResponseEntity.ok(sectionService.getTestSections(testId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSection(@PathVariable("id") Integer id) {
        if(sectionService.findOne(id) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        sectionService.deleteSection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add")
    public ResponseEntity<SectionDTO> addSection(@RequestBody SectionDTO section, @RequestParam Integer testId) {
        try {
            return ResponseEntity.ok(sectionService.addSection(section, testId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSection(@RequestBody SectionDTO dto, @PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(sectionService.updateSection(dto, id));
        } catch (KSpaceException kse) {
            return new ResponseEntity<>(kse.getMessage(), kse.getHttpStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
