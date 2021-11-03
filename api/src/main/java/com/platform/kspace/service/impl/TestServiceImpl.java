package com.platform.kspace.service.impl;

import java.util.Date;
import java.util.List;

import com.platform.kspace.dto.ItemDTO;
import com.platform.kspace.dto.TestDTO;
import com.platform.kspace.dto.WorkingTestDTO;
import com.platform.kspace.mapper.ItemMapper;
import com.platform.kspace.mapper.TestMapper;
import com.platform.kspace.mapper.WorkingTestMapper;
import com.platform.kspace.model.Item;
import com.platform.kspace.model.Professor;
import com.platform.kspace.model.Student;
import com.platform.kspace.model.TakenTest;
import com.platform.kspace.model.Test;
import com.platform.kspace.repository.ItemRepository;
import com.platform.kspace.repository.ProfessorRepository;
import com.platform.kspace.repository.StudentRepository;
import com.platform.kspace.repository.TakenTestRepository;
import com.platform.kspace.repository.TestRepository;
import com.platform.kspace.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class TestServiceImpl implements TestService {
    
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private TakenTestRepository takenTestRepository;

    @Autowired
    private ItemRepository itemRepository;

    private WorkingTestMapper workingTestMapper;

    private TestMapper testMapper;

    private ItemMapper itemMapper;

    public TestServiceImpl() {
        workingTestMapper = new WorkingTestMapper();
        testMapper = new TestMapper();
        itemMapper = new ItemMapper();
    }

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public Test findOne(int id) {
        return testRepository.findById(id).orElse(null);
    }

    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Override
    public WorkingTestDTO startTest(Integer testId, Integer studentId) {
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
    public TestDTO addTest(TestDTO dto, Integer professorId) throws Exception {
        Professor p = professorRepository.findById(professorId).orElseGet(null);
        if(p == null)
            throw new Exception("Professor doesn't exist.");
        Test test = testMapper.toEntity(dto);
        test.setCreatedBy(p);
        return testMapper.toDto(testRepository.save(test));
    }

    @Override
    public ItemDTO getNextQuestion(Integer takenTestId, Integer currentItemId) throws Exception {
        TakenTest takenTest = takenTestRepository.getById(takenTestId);
        if(takenTest.getEnd() != null) {
            throw new Exception("Test is finished");
        }
        List<Item> items = itemRepository.findAllTestItems(takenTest.getTest().getId());
        if(currentItemId == null) {
            if(items.size() == 0) {
                return null;
            }
            return itemMapper.toDto(items.get(0));
        } else {
            try {
                for(Item i : items) {
                    if(i.getId().equals(currentItemId)) {
                        int index = items.indexOf(i);
                        return itemMapper.toDto(items.get(index + 1));
                    }
                }
                throw new NotFoundException("Item not in current test");
            } catch (IndexOutOfBoundsException | NotFoundException ex) {
                return null;
            }
        }
    }

    @Override
    public List<TestDTO> getTests() {
        return testMapper.toDtoList(testRepository.findAll());
    }
}
