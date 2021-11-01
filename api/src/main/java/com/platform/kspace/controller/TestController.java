package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.model.Test;
import com.platform.kspace.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private TestService testService;

    @GetMapping
    public ResponseEntity<List<Test>> getTests() {
        return ResponseEntity.ok(this.testService.findAll());
    }
}
