package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.dto.DomainDTO;
import com.platform.kspace.service.DomainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public ResponseEntity<List<DomainDTO>> getItems() {
        return ResponseEntity.ok(domainService.getDomains());
    }
}
