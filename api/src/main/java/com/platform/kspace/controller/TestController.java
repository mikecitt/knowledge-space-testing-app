package com.platform.kspace.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import com.platform.kspace.dto.*;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.service.TestService;

import com.platform.kspace.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    @PatchMapping
    public ResponseEntity<?> searchTests(
            @RequestParam String searchKeyword,
            Pageable pageable,
            Principal principal
    ) {
        try {
            return ResponseEntity.ok(this.testService.searchTests(searchKeyword,
                    userService.findUserByEmail(principal.getName()).getId(), pageable));
        } catch (KSpaceException kse) {
            return new ResponseEntity<>(kse.getMessage(), kse.getHttpStatus());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTest(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.testService.deleteTest(id));
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
    public ResponseEntity<?> startTest(
            @RequestParam Integer testId,
            Principal principal) {
        try {
            return ResponseEntity.ok(testService.startTest(
                    testId, userService.findUserByEmail(principal.getName()).getId()));
        } catch (KSpaceException kse) {
            return new ResponseEntity<>(kse.getMessage(), kse.getHttpStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/taken")
    public ResponseEntity<PagedEntity<TakenTestDTO>> searchTakenTests(
            @RequestParam String searchKeyword,
            Principal principal,
            Pageable pageable
    ) {
        try {
            return ResponseEntity.ok(testService.searchTakenTests(searchKeyword,
                    userService.findUserByEmail(principal.getName()).getId(), pageable));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/question/next")
    public ResponseEntity<StudentItemDTO> getNextQuestion(
            Principal principal,
            @RequestParam(required = false) Integer itemId) {
        try {
            return ResponseEntity.ok(testService.getNextQuestion(
                    userService.findUserByEmail(principal.getName()).getId(), itemId));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/taken/current")
    public ResponseEntity<WorkingTestDTO> getNextQuestion(Principal principal) {
        try {
            return ResponseEntity.ok(testService.getCurrentWorkingTest(
                    userService.findUserByEmail(principal.getName()).getId()
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/taken/answer")
    public ResponseEntity<?> answerItem(
            @RequestBody ItemAnswersDTO itemAnswersDTO,
            Principal principal
    ) {
        try {
            testService.answerOnItem(
                    userService.findUserByEmail(principal.getName()).getId(),
                    itemAnswersDTO
            );

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (KSpaceException kse) {
            return new ResponseEntity<>(kse.getMessage(), kse.getHttpStatus());
        }
    }
}
