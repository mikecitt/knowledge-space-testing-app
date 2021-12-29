package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.dto.KnowledgeSpaceDTO;
import com.platform.kspace.service.KnowledgeSpaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/knowledge-space")
public class KnowledgeSpaceController {
    @Autowired
    private KnowledgeSpaceService knowledgeSpaceService;

    @PostMapping
    public ResponseEntity<KnowledgeSpaceDTO> addKnowledgeSpace(@RequestBody KnowledgeSpaceDTO dto, @RequestParam Integer domainId) {
        try {
            return ResponseEntity.ok(knowledgeSpaceService.addKnowledgeSpace(dto, domainId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // @GetMapping
    // public ResponseEntity<List<KnowledgeSpaceDTO>> getKnowledgeSpaces() {
    //     return ResponseEntity.ok(knowledgeSpaceService.getKnowledgeSpaces());
    // }
    // unnecesarry

    @GetMapping
    public ResponseEntity<List<KnowledgeSpaceDTO>> getDomainKnowledgeSpaces(@RequestParam Integer domainId) {
        return ResponseEntity.ok(knowledgeSpaceService.getKnowledgeSpaces(domainId));
    }
}
