package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.dto.DomainDTO;
import com.platform.kspace.dto.KnowledgeSpaceComparisonDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.service.DomainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/domain")
public class DomainController {
    @Autowired
    private DomainService domainService;

    @PostMapping
    public ResponseEntity<DomainDTO> addDomain(@RequestBody DomainDTO dto) {
        try {
            return ResponseEntity.ok(domainService.addDomain(dto));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DomainDTO> updateDomain(@RequestBody DomainDTO dto, @PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(domainService.updateDomain(dto, id));
        } catch (KSpaceException ex) {
            return new ResponseEntity<>(ex.getHttpStatus());
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomain(@PathVariable("id") Integer id) {
        try {
            domainService.deleteDomain(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (KSpaceException ex) {
            return new ResponseEntity<>(ex.getHttpStatus());
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<DomainDTO>> getDomains() {
        return ResponseEntity.ok(domainService.getDomains());
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<DomainDTO>> getUnassignedDomains() {
        return ResponseEntity.ok(domainService.getUnassignedDomains());
    }

    @GetMapping("/knowledge-space-comparison/{domainId}")
    public ResponseEntity<KnowledgeSpaceComparisonDTO> getDomainKnowledgeSpaceComparison(@PathVariable("domainId") Integer domainId) {
        try {
            return ResponseEntity.ok(domainService.getDomainKnowledgeSpaceComparison(domainId));
        } catch (KSpaceException ex) {
            return new ResponseEntity<>(ex.getHttpStatus());
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
