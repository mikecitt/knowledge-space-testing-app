package com.platform.kspace.service;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.dto.TestDTO;
import com.platform.kspace.dto.WorkingTestDTO;
import com.platform.kspace.model.Test;

import java.util.List;

public interface TestService {

    List<Test> findAll();
    List<TestDTO> getTests();
    Test findOne(int id);
    Test save(Test test);
    TestDTO addTest(TestDTO test, Integer professorId) throws Exception;
    WorkingTestDTO startTest(Integer testId, Integer studentId);
    ItemDTO getNextQuestion(Integer takenTestId, Integer currentItemId) throws Exception;
}
