package com.platform.kspace.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import com.platform.kspace.dto.StudentItemDTO;
import com.platform.kspace.dto.TestDTO;
import com.platform.kspace.dto.WorkingTestDTO;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.service.TestService;

import com.platform.kspace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/test")
public class TestController {
    
    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<TestDTO>> getTests() {
        return ResponseEntity.ok(this.testService.getTests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTest(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.testService.getTest(id));
    }

    @PostMapping
    public ResponseEntity<?> addTest(@RequestBody TestDTO dto, Principal principal) {
        try {
            return ResponseEntity.ok(testService.addTest(
                    dto,
                    userService.findUserByEmail(principal.getName()).getId())
            );
        } catch (KSpaceException kse) {
            return new ResponseEntity<>(kse.getMessage(), kse.getHttpStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/start")
    public ResponseEntity<WorkingTestDTO> startTest(
            @RequestParam UUID studentId,
            @RequestParam Integer testId) {
        try {
            return ResponseEntity.ok(testService.startTest(testId, studentId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/question/next")
    public ResponseEntity<StudentItemDTO> getNextQuestion(
            @RequestParam Integer workingTestId,
            @RequestParam(required = false) Integer itemId) {
        try {
            return ResponseEntity.ok(testService.getNextQuestion(workingTestId, itemId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
