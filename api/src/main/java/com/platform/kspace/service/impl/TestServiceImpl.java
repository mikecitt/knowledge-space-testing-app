package com.platform.kspace.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import ch.qos.logback.core.net.QueueFactory;
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
import org.yaml.snakeyaml.util.ArrayUtils;

import javax.transaction.Transactional;

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

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private DomainProblemRepository domainProblemRepository;

    private final WorkingTestMapper workingTestMapper;

    private final TestMapper testMapper;

    private final StudentItemMapper itemMapper;

    private final TakenTestMapper takenTestMapper;

    private boolean isTestStartable(Test test) {
        Calendar now = Calendar.getInstance();
        return test.getValidFrom().before(now.getTime()) &&
                test.getValidUntil().after(now.getTime());
    }

    private List<DomainProblem> getDomainProblemSequence(KnowledgeSpace ks) throws KSpaceException {
        DomainProblem root = domainProblemRepository.findRootProblem(ks.getId());

        LinkedList<DomainProblem> problems = new LinkedList<>();
        Edge startEdge = ks.getEdges().stream().filter(x -> x.getFrom().equals(root)).findAny().orElse(null);
        if (startEdge == null)
            throw new KSpaceException(HttpStatus.BAD_REQUEST, "Something went wrong.");

        Queue<Edge> edgeQueue = new LinkedList<>();
        edgeQueue.add(startEdge);

        while(!edgeQueue.isEmpty()) {
            Edge e = edgeQueue.poll();
            if (!problems.contains(e.getFrom()))
                problems.add(e.getFrom());
            if (!problems.contains(e.getTo()))
                problems.add(e.getTo());
            ks.getEdges()
                    .stream()
                    .filter(x -> x.getFrom().equals(e.getTo()))
                    .forEach(edgeQueue::add);
        }
        return problems;
    }

    private void finishTest(TakenTest test) {
        test.setEnd(new Date());
        takenTestRepository.save(test);
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
        Item retVal;
        if (takenTest == null)
            throw new KSpaceException(HttpStatus.NOT_FOUND, "There is no taken test at the moment.");

        Optional<KnowledgeSpace> real = takenTest.getTest()
                .getDomain().getKnowledgeSpaces().stream().filter(KnowledgeSpace::isIsReal).findAny();

        if (real.isEmpty()) {
            Optional<KnowledgeSpace> ks = takenTest.getTest()
                    .getDomain().getKnowledgeSpaces().stream().findFirst();

            if (ks.isEmpty())
                throw new KSpaceException(HttpStatus.BAD_REQUEST, "Test does not contain valid knowledge space.");

            List<DomainProblem> problemSequence = getDomainProblemSequence(ks.get());

            if(currentItemId == null) {
                DomainProblem root = domainProblemRepository.findRootProblem(ks.get().getId());
                retVal = root.getItems().stream()
                        .filter(x -> x.getSection()
                                .getTest()
                                .getId()
                                .equals(takenTest.getTest().getId())).findAny().get();
                StudentItemDTO itemDto = itemMapper.toDto(retVal);
                itemDto.getAnswers().forEach(answer -> answer.setSelected(
                        takenTest.containsAnswer(answer.getId()))
                );
                return itemDto;
            }
            Optional<Item> item = itemRepository.findById(currentItemId);
            if (item.isEmpty())
                throw new KSpaceException(HttpStatus.BAD_REQUEST, "Test does not contain that item.");

            int index = problemSequence.indexOf(item.get().getDomainProblem());
            if (index + 1 == problemSequence.size()) {
                finishTest(takenTest);
                return null;
            } else {
                retVal = problemSequence.get(index + 1).getItems().stream()
                        .filter(x -> x.getSection()
                                .getTest()
                                .getId()
                                .equals(takenTest.getTest().getId())).findAny().get();
                StudentItemDTO itemDto = itemMapper.toDto(retVal);
                itemDto.getAnswers().forEach(answer -> answer.setSelected(
                        takenTest.containsAnswer(answer.getId()))
                );
                return itemDto;
            }
        } else {
            // TODO: add real space
            return null;
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
        takenTestRepository.deleteAnswersOfItem(itemAnswersDTO.getItemId(), takenTest.getId());
        for(Integer answerId : itemAnswersDTO.getSelectedAnswers()) {
            takenTest.addAnswer(answerRepository.getById(answerId));
        }

        takenTestRepository.save(takenTest);
    }

    @Override
    public void updateTestDomain(Integer testId, Integer domainId) throws NotFoundException {
        Optional<Test> test = testRepository.findById(testId);
        Optional<Domain> domain = domainRepository.findById(domainId);

        if (test.isEmpty() || domain.isEmpty()) {
            throw new NotFoundException("Test or domain were not found.");
        }

        test.get().setDomain(domain.get());
        test.get().getSections()
                .forEach(section -> section.getItems()
                        .forEach(Item::removeDomainProblem));
        testRepository.save(test.get());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void assignProblemsToItems(List<ItemProblemDTO> itemProblems) throws NotFoundException {
        for (ItemProblemDTO itemProblem : itemProblems) {
            Optional<DomainProblem> domainProblem = domainProblemRepository
                    .findById(itemProblem.getDomainProblemId());
            Optional<Item> item = itemRepository.findById(itemProblem.getItemId());

            if (domainProblem.isEmpty() || item.isEmpty()) {
                throw new NotFoundException("Domain problem or item were not found.");
            }

            item.get().setDomainProblem(domainProblem.get());
            itemRepository.save(item.get());
        }
    }

    @Override
    public void exportResultsToITA(Integer testId) {
        Test test = testRepository.getById(testId);
        List<Item> items = itemRepository.findAllTestItems(test.getId());
        List<TakenTest> takenTests = takenTestRepository.findAllByTest(test);
        ResultsDTO resultsDTO = new ResultsDTO();

        for(TakenTest takenTest : takenTests) {
            List<Byte> answers = new ArrayList<>();
            TreeMap<Integer, List<Answer>> groupedAnswers = new TreeMap<>();
            for (Item item : items) {
                groupedAnswers.put(item.getId(), new ArrayList<>());
            }

            for(Answer answer : takenTest.getAnswers()) {
                groupedAnswers.get(answer.getId()).add(answer);
            }

            for (Integer key : groupedAnswers.keySet()) {
                double positive = groupedAnswers.get(key).stream().filter(x -> x.getPoints() >= 0).mapToDouble(Answer::getPoints).sum();
                double sumed = groupedAnswers.get(key).stream().mapToDouble(Answer::getPoints).sum();

                if (positive / 2 > sumed) {
                    answers.add((byte) 1);
                } else {
                    answers.add((byte) 0);
                }
            }
            Byte[] bytes = answers.toArray(new Byte[0]);
            resultsDTO.getResults().put(takenTest.getTakenBy().getId(), bytes);
        }

        //TODO : to send to ita
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
