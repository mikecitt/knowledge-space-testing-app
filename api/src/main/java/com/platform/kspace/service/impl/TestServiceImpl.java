package com.platform.kspace.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.platform.kspace.dto.*;
import com.platform.kspace.exceptions.KSpaceException;
import com.platform.kspace.mapper.StudentItemMapper;
import com.platform.kspace.mapper.TakenTestMapper;
import com.platform.kspace.mapper.TestMapper;
import com.platform.kspace.mapper.WorkingTestMapper;
import com.platform.kspace.model.*;
import com.platform.kspace.repository.*;
import com.platform.kspace.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private AnswerRepository answerRepository;

    @Autowired
    private ItemRepository itemRepository;

    private final WorkingTestMapper workingTestMapper;

    private final TestMapper testMapper;

    private final StudentItemMapper itemMapper;

    private final TakenTestMapper takenTestMapper;

    private boolean isTestStartable(Test test) {
        Calendar now = Calendar.getInstance();
        return test.getValidFrom().before(now.getTime()) &&
                test.getValidUntil().after(now.getTime());
    }

    public TestServiceImpl() {
        workingTestMapper = new WorkingTestMapper();
        testMapper = new TestMapper();
        itemMapper = new StudentItemMapper();
        takenTestMapper = new TakenTestMapper();
    }

    @Override
    public List<TestDTO> findAllByUser(User user) {
        return testMapper.toDtoList(testRepository.findAllByCreatedBy(user));
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
    public WorkingTestDTO startTest(Integer testId, UUID studentId) throws KSpaceException {
        TakenTest alreadyTaken = takenTestRepository.getUnfinishedTest(studentId);
        Test test = testRepository.getById(testId);

        if (alreadyTaken != null)
            throw new KSpaceException(HttpStatus.CONFLICT, "You have already started another test.");
        if (!isTestStartable(test))
            throw new KSpaceException(HttpStatus.BAD_REQUEST, "Selected test is not open.");
        if(takenTestRepository.checkIfTestIsAlreadyTaken(studentId))
            throw new KSpaceException(HttpStatus.CONFLICT, "You have already taken this test.");

        Student s = studentRepository.getById(studentId);
        TakenTest takenTest = new TakenTest(
                new Date(),
                s,
                test
        );
        return workingTestMapper.toDto(takenTestRepository.save(takenTest));
    }

    @Override
    public TestDTO addTest(TestDTO dto, UUID professorId) throws Exception {
        Professor p = professorRepository.getById(professorId);
        if(p == null)
            throw new Exception("Professor doesn't exist.");
        Test test = testMapper.toEntity(dto);
        test.setCreatedBy(p);
        Calendar c = Calendar.getInstance();
        c.setTime(test.getValidUntil());
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        test.setValidUntil(c.getTime());
        return testMapper.toDto(testRepository.save(test));
    }

    @Override
    public TestDTO updateTest(TestDTO test, Integer testId) throws Exception {
        Test t = testRepository.getById(testId);
        if(t == null)
            throw new Exception("Test doesn't exist.");
        t.setName(test.getName());
        t.setTimer(test.getTimer());
        t.setValidFrom(test.getValidFrom());
        t.setValidUntil(test.getValidUntil());
        return testMapper.toDto(testRepository.save(t));
    }

    @Override
    public StudentItemDTO getNextQuestion(UUID studentId, Integer currentItemId) throws KSpaceException {
        TakenTest takenTest = takenTestRepository.getUnfinishedTest(studentId);

        if (takenTest == null)
            throw new KSpaceException(HttpStatus.NOT_FOUND, "There is no taken test at the moment.");

        List<Item> items = itemRepository.findAllTestItems(takenTest.getTest().getId());
        if(currentItemId == null) {
            if(items.size() == 0) {
                return null;
            }
            StudentItemDTO item = itemMapper.toDto(items.get(0));
            item.getAnswers().forEach(answer -> answer.setSelected(
                    takenTest.containsAnswer(answer.getId()))
            );
            return item;
        } else {
            try {
                for(Item i : items) {
                    if(i.getId().equals(currentItemId)) {
                        int index = items.indexOf(i);
                        StudentItemDTO item = itemMapper.toDto(items.get(index + 1));
                        item.getAnswers().forEach(answer -> answer.setSelected(
                                takenTest.containsAnswer(answer.getId()))
                        );
                        return item;
                    }
                }
                throw new NotFoundException("Item not in current test");
            } catch (IndexOutOfBoundsException | NotFoundException ex) {
                return null;
            }
        }
    }

    @Override
    public PagedEntity<TakenTestDTO> searchTakenTests(
            String searchKeyword,
            UUID studentId,
            Pageable pageable
    ) {
        Page<TakenTest> page = takenTestRepository.searchTakenTestsByStudent(
                searchKeyword, studentId, pageable);
        return new PagedEntity<>(
                takenTestMapper.toDtoList(page.getContent()),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }

    @Override
    public WorkingTestDTO getCurrentWorkingTest(UUID studentId) {
        TakenTest takenTest = takenTestRepository.getUnfinishedTest(studentId);

        if (takenTest != null) {
            return workingTestMapper.toDto(takenTest);
        }

        return null;
    }

    @Override
    public void answerOnItem(UUID studentId, ItemAnswersDTO itemAnswersDTO) throws NotFoundException {
        TakenTest takenTest = takenTestRepository.getUnfinishedTest(studentId);
        if (itemRepository.findAllTestItems(takenTest.getTest().getId()).stream().noneMatch(
                item -> item.getId().equals(itemAnswersDTO.getItemId()))
        ) {
            throw new NotFoundException("Item not in current test");
        }

        takenTest.clearAnswersOfItem(itemAnswersDTO.getItemId());
        takenTestRepository.deleteAnswersOfItem(itemAnswersDTO.getItemId());
        for(Integer answerId : itemAnswersDTO.getSelectedAnswers()) {
            takenTest.addAnswer(answerRepository.getById(answerId));
        }

        takenTestRepository.save(takenTest);
    }

    @Override
    public List<TestDTO> getTests() {
        return testMapper.toDtoList(testRepository.findAll());
    }

    @Override
    public PagedEntity<TestDTO> searchTests(String searchKeyword, UUID studentId, Pageable pageable) {
        Page<Test> page = testRepository.searchTests(searchKeyword, studentId, pageable);
        return new PagedEntity<>(
                testMapper.toDtoList(page.getContent()),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }

    @Override
    public TestDTO getTest(Integer id) {
        return testMapper.toDto(testRepository.findById(id).orElse(null));
    }

    @Override
    public boolean deleteTest(Integer id) {
        testRepository.deleteById(id);
        return true;
    }
}
