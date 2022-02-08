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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import org.springframework.web.client.RestTemplate;
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

    @Autowired
    private KnowledgeSpaceRepository knowledgeSpaceRepository;

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
        Integer domainId = test.getTest().getDomain().getId();
        List<Item> items = itemRepository.findAllTestItems(test.getTest().getId());
        if (takenTestRepository.countTakenTestsForDomain(domainId) >= items.size()) {
            Optional<KnowledgeSpace> realKs = knowledgeSpaceRepository
                    .findByIsRealAndDomain(true, test.getTest().getDomain());
            if (realKs.isPresent()) {
                return;
            }
            try {
                exportResultsToITA(domainId);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void generateKnowledgeStates(KnowledgeSpace realKs) {
        DomainProblem root = domainProblemRepository
                .findRootProblem(realKs.getId());

        String states = "";
        recursiveStateChecks(root, realKs, states);
        knowledgeSpaceRepository.save(realKs);
    }

    private void recursiveStateChecks(DomainProblem node, KnowledgeSpace realKs, String states) {
        List<Edge> edges = realKs.getEdges();
        List<Edge> nodeEdges = edges.stream().filter(x -> x.getFrom()
                        .equals(node))
                .collect(Collectors.toList());
        states += node.getId();
        if (nodeEdges.isEmpty()) {
            realKs.addKnowledgeStates(states);
        } else {
            states += ",";
            for (Edge edge : nodeEdges) {
                recursiveStateChecks(edge.getTo(), realKs, states);
            }
        }
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
    public void exportResultsToITA(Integer domainId) throws Exception {
        List<TakenTest> takenTests = takenTestRepository.findAllByDomainId(domainId);
        ResultsDTO resultsDTO = new ResultsDTO();
        Integer ksExpId = -1;
        for(TakenTest takenTest : takenTests) {
            List<Item> items = itemRepository.findAllByTestAndDomain(takenTest.getTest().getId(), domainId);
            List<Byte> answers = new ArrayList<>();
            Map<Integer, List<Answer>> groupedAnswers = items.stream()
                    .collect(Collectors.toMap(Item::getId, x -> new ArrayList<>()));
            takenTest.getAnswers()
                    .forEach(x -> groupedAnswers.get(x.getItem().getId()).add(x));

            for (Item item : items) {
                double positive = item.getAnswers()
                        .stream()
                        .filter(x -> x.getPoints() >= 0).mapToDouble(Answer::getPoints).sum();
                double summed = groupedAnswers.get(item.getId())
                        .stream()
                        .mapToDouble(Answer::getPoints).sum();

                if (summed >= (3 * positive) / 4 ) {
                    answers.add((byte) 1);
                } else {
                    answers.add((byte) 0);
                }
            }
            Byte[] bytes = answers.toArray(new Byte[0]);
            resultsDTO.getResults().put(takenTest.getTakenBy().getId(), bytes);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ResultsDTO> request =
                new HttpEntity<>(resultsDTO, headers);
        int[][] diff = restTemplate.postForObject("http://localhost:5000/ita", request, int[][].class);

        Domain d = domainRepository.getById(domainId);
        KnowledgeSpace realKs = new KnowledgeSpace();
        realKs.setIsReal(true);
        realKs.setName("Real1");
        realKs.setDomain(d);
        Optional<KnowledgeSpace> expKs = knowledgeSpaceRepository.findByIsRealAndDomain(false, d);

        if (expKs.isEmpty())
            throw new Exception("Something went wrong...");

        List<DomainProblem> domainProblems = domainProblemRepository.findKnowledgeSpaceDomainProblems(expKs.get().getId());
        for(int[] newEdge : diff) {
            Edge edge = new Edge(domainProblems.get(newEdge[0]), domainProblems.get(newEdge[1]));
            edge.setKnowledgeSpace(realKs);
            realKs.addEdge(edge);
        }

        realKs = knowledgeSpaceRepository.save(realKs);
        generateKnowledgeStates(realKs);
    }

    @Override
    public String testToXml(Integer id) throws KSpaceException {
        Optional<Test> test = testRepository.findById(id);

        if (test.isEmpty()) {
            throw new KSpaceException(HttpStatus.NOT_FOUND, "Knowledge space not found with given id.");
        }

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                "<qti-assessment-test\r\n" +
                "	xmlns=\"http://www.imsglobal.org/xsd/imsqti_v3p0\" \r\n" +
                "	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" +
                "	identifier=\"" + test.get().getId() + "\"\r\n" +
                "	title=\"" + test.get().getName() +"\"\r\n" +
                "	xsi:schemaLocation=\"http://www.imsglobal.org/xsd/imsqti_v3p0 https://purl.imsglobal.org/spec/qti/v3p0/xsd/imsqti_asiv3p0_v1p0.xsd\"\r\n" +
                "	xml:lang=\"en-US\" >";
//                domainProblemsParts(test.getProblems(), filePath) +
        for(Section s : test.get().getSections()) {
            xml += "<qti-assessment-section identifier=\"" + s.getId() + "\" title=\"" + s.getName() +"\" visible=\"true\">";
            for(Item i : s.getItems()) {
                xml += "<qti-assessment-item identifier=\"" + i.getId() + "\" item-dependent=\"false\">";
                    xml += "<qti-response-declaration base-type=\"identifier\" cardinality=\"single\" identifier=\"RESPONSE\">\r\n" +
                            "    <qti-correct-response>\r\n";
                    for(Answer a : i.getAnswers()) {
                        if(a.getPoints() > 0)
                            xml += "<qti-value>" + a.getId() + "</qti-value>\r\n";
                    }
                        xml += "</qti-correct-response>\r\n";
                    xml += "</qti-response-declaration>\r\n";
                xml += "<qti-item-body>\r\n";
                xml += "<qti-choice-interaction max-choices=\"" + i.getAnswers().size() + "\" shuffle=\"false\" response-identifier=\"RESPONSE\">\r\n";
                xml += "<qti-prompt>" + i.getText() + "</qti-prompt>";
                for(Answer a : i.getAnswers()) {
                    xml += "<qti-simple-choice identifier=\"" + a.getId() + "\">" + a.getText() + "</qti-simple-choice>\r\n";
                }
                xml += "</qti-choice-interaction>";
                xml += "</qti-item-body>";
                xml += "<qti-response-processing template=\"https://purl.imsglobal.org/spec/qti/v3p0/rptemplates/match_correct\"/>\r\n";
                xml += "</qti-assessment-item>";
            }
            xml += "</qti-assessment-section>";
        }
        xml += "\r\n" +
        "</qti-assessment-test>";

        return xml;
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
