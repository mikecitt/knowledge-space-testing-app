package com.platform.kspace.service;

import com.platform.kspace.dto.PagedEntity;
import com.platform.kspace.dto.StudentItemDTO;
import com.platform.kspace.dto.TestDTO;
import com.platform.kspace.dto.WorkingTestDTO;
import com.platform.kspace.model.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TestService {

    List<Test> findAll();
    List<TestDTO> getTests();
    PagedEntity<TestDTO> searchTests(String searchKeyword, Pageable pageable);
    TestDTO getTest(Integer id);
    Test findOne(int id);
    Test save(Test test);
    boolean deleteTest(Integer id);
    TestDTO addTest(TestDTO test, UUID professorId) throws Exception;
    WorkingTestDTO startTest(Integer testId, UUID studentId);
    StudentItemDTO getNextQuestion(Integer takenTestId, Integer currentItemId) throws Exception;
}
