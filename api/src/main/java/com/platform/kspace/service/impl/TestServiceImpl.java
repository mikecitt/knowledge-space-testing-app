package com.platform.kspace.service.impl;

import java.util.Date;
import java.util.List;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.dto.WorkingTestDTO;
import com.platform.kspace.mapper.WorkingTestMapper;
import com.platform.kspace.model.Answer;
import com.platform.kspace.model.Student;
import com.platform.kspace.model.TakenTest;
import com.platform.kspace.model.Test;
import com.platform.kspace.repository.TakenTestRepository;
import com.platform.kspace.repository.TestRepository;

import com.platform.kspace.repository.StudentRepository;
import com.platform.kspace.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TakenTestRepository takenTestRepository;

    private WorkingTestMapper workingTestMapper;

    public TestServiceImpl() {
        workingTestMapper = new WorkingTestMapper();
    }

    @Override
    public List<Test> findAll() {
        return (List<Test>)testRepository.findAll();
    }

    @Override
    public Test findOne(int id) {
        return testRepository.findById(id).orElseGet(null);
    }

    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Override
    public WorkingTestDTO startTest(int testId, int studentId) {
        Test test = testRepository.getById(testId);
        Student s = studentRepository.getById(studentId);
        TakenTest takenTest = new TakenTest(
                new Date(),
                s,
                test
        );
        return workingTestMapper.toDto(takenTestRepository.save(takenTest));
    }

    @Override
    public ItemDTO getNextQuestion(int takenTestId) {
        TakenTest takenTest = takenTestRepository.getById(takenTestId);
        Answer lastAnswer = takenTest.getAnswers()
                .get(takenTest.getAnswers().size() - 1);

        // TODO: Finish this method
        return null;
    }
}
