package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.dto.ItemProblemDTO;
import com.platform.kspace.dto.KnowledgeSpaceDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.service.KnowledgeSpaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/knowledge-space")
public class KnowledgeSpaceController {
    @Autowired
    private KnowledgeSpaceService knowledgeSpaceService;

    @PostMapping
    public ResponseEntity<KnowledgeSpaceDTO> addKnowledgeSpace(@RequestBody KnowledgeSpaceDTO dto) {
        try {
            return ResponseEntity.ok(knowledgeSpaceService.addKnowledgeSpace(dto));
        } catch (KSpaceException ex) {
            return new ResponseEntity<>(ex.getHttpStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeSpaceDTO> updateKnowledgeSpace(@RequestBody KnowledgeSpaceDTO dto, @PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(knowledgeSpaceService.updateKnowledgeSpace(dto, id));
        } catch (KSpaceException ex) {
            return new ResponseEntity<>(ex.getHttpStatus());
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<KnowledgeSpaceDTO> deleteKnowledgeSpace(@PathVariable("id") Integer id) {
        try {
            knowledgeSpaceService.deleteKnowledgeSpace(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (KSpaceException ex) {
            return new ResponseEntity<>(ex.getHttpStatus());
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<KnowledgeSpaceDTO>> getDomainKnowledgeSpaces(@RequestParam(required=false) Integer domainId) {
        if (domainId == null) {
            return ResponseEntity.ok(knowledgeSpaceService.getKnowledgeSpaces());
        }
        return ResponseEntity.ok(knowledgeSpaceService.getKnowledgeSpaces(domainId));
    }

    @GetMapping("/assigned-problems")
    public ResponseEntity<List<ItemProblemDTO>> getAssignedProblems(@RequestParam Integer kSpaceId, @RequestParam Integer testId) {
        return ResponseEntity.ok(knowledgeSpaceService.getAllDomainProblemsFromKSpace(kSpaceId, testId));
    }
}
