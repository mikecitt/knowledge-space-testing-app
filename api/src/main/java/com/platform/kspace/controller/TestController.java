package com.platform.kspace.controller;

import java.util.List;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.dto.WorkingTestDTO;
import com.platform.kspace.model.Test;
import com.platform.kspace.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/start")
    public ResponseEntity<WorkingTestDTO> startTest(
            @RequestParam Integer studentId,
            @RequestParam Integer testId) {
        try {
            return ResponseEntity.ok(testService.startTest(testId, studentId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/question/next")
    public ResponseEntity<ItemDTO> getNextQuestion(@RequestParam Integer workingTestId) {
        // TODO: Finish this endpoint
        return null;
    }
}
