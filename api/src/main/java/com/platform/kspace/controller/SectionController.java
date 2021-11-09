package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.dto.SectionDTO;
import com.platform.kspace.model.Section;
import com.platform.kspace.service.SectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    // not in use for now
    /*@GetMapping
    public ResponseEntity<List<SectionDTO>> getSections() {
        return ResponseEntity.ok(sectionService.getSections());
    }*/

    @GetMapping
    public ResponseEntity<List<SectionDTO>> getTestSections(@RequestParam Integer testId) {
        return ResponseEntity.ok(sectionService.getTestSections(testId));
    }

    @PostMapping(
        path = "/add", 
        consumes = { MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
)
    public ResponseEntity<SectionDTO> addSection(@RequestBody Section section, @RequestParam Integer testId) {
        try {
            return ResponseEntity.ok(sectionService.addSection(section, testId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
