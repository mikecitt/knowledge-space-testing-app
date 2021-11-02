package com.platform.kspace.service;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.dto.WorkingTestDTO;
import com.platform.kspace.model.Test;

import java.util.List;

public interface TestService {

    List<Test> findAll();
    Test findOne(int id);
    Test save(Test test);
    WorkingTestDTO startTest(int testId, int studentId);
    ItemDTO getNextQuestion(int takenTestId);
}
