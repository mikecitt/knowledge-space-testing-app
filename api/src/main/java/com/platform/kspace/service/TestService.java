package com.platform.kspace.service;

import com.platform.kspace.dto.*;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.model.Test;
import javassist.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TestService {

    List<Test> findAll();
    List<TestDTO> getTests();
    PagedEntity<TestDTO> searchTests(String searchKeyword, UUID studentId, Pageable pageable);
    TestDTO getTest(Integer id);
    Test findOne(int id);
    Test save(Test test);
    boolean deleteTest(Integer id);
    TestDTO updateTest(TestDTO test, Integer testId) throws Exception;
    TestDTO addTest(TestDTO test, UUID professorId) throws Exception;
    WorkingTestDTO startTest(Integer testId, UUID studentId) throws KSpaceException;
    StudentItemDTO getNextQuestion(UUID studentId, Integer currentItemId) throws KSpaceException;
    PagedEntity<TakenTestDTO> searchTakenTests(String searchKeyword, UUID studentId, Pageable pageable);
    WorkingTestDTO getCurrentWorkingTest(UUID studentId);
    void answerOnItem(UUID studentId, ItemAnswersDTO itemAnswersDTO) throws NotFoundException;
}
