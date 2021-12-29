package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.dto.DomainProblemDTO;
import com.platform.kspace.service.DomainProblemService;

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
@RequestMapping("api/domain-problem")
public class DomainProblemController {
    @Autowired
    private DomainProblemService domainProblemService;

    @PostMapping
    public ResponseEntity<DomainProblemDTO> addDomainProblem(@RequestBody DomainProblemDTO dto) {
        try {
            return ResponseEntity.ok(domainProblemService.addDomainProblem(dto));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<DomainProblemDTO>> getDomainProblems() {
        return ResponseEntity.ok(domainProblemService.getDomainProblems());
    }
}
