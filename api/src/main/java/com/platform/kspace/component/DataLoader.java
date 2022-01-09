package com.platform.kspace.component;

import java.util.Calendar;

import com.platform.kspace.model.Answer;
import com.platform.kspace.model.Domain;
import com.platform.kspace.model.DomainProblem;
import com.platform.kspace.model.Edge;
import com.platform.kspace.model.Item;
import com.platform.kspace.model.KnowledgeSpace;
import com.platform.kspace.model.Professor;
import com.platform.kspace.model.Student;
import com.platform.kspace.repository.DomainRepository;
import com.platform.kspace.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private UserRepository userRepository;

    private DomainRepository  domainRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, DomainRepository domainRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.domainRepository = domainRepository;
        this.passwordEncoder = passwordEncoder;
        LoadData();
    }

    private void LoadData() {
        /*Professor p = new Professor("Petar", "Petrovic", "petar@example.com", "$2a$04$P2R/ohGi2eYUJw02EEZaveX37jwcXb4E.RnwQo8MgP8EgNP0vjfN.");
        p.setPassword(passwordEncoder.encode("qwerty"), true);
        p = this.userRepository.save(p);

        Calendar cal = Calendar.getInstance();
        cal.set(2000, 10, 5);
        Student s = new Student(
                "Mika",
                "Mikic",
                "mika@example.com",
                "$2a$04$P2R/ohGi2eYUJw02EEZaveX37jwcXb4E.RnwQo8MgP8EgNP0vjfN.",
                cal.getTime()
        );
        this.userRepository.save(s);

        Item item1 = new Item("HTML is what type of language?");
        item1.addAnswer(new Answer("Scripting Language", 0.0));
        item1.addAnswer(new Answer("Markup Language", 5.0));
        item1.addAnswer(new Answer("Programming Language", 0.0));
        item1.addAnswer(new Answer("Network Protocol", 0.0));
        
        Item item2 = new Item("Which HTTP methods exists?");
        item2.addAnswer(new Answer("GET", 5.0));
        item2.addAnswer(new Answer("FIND", -5.0));
        item2.addAnswer(new Answer("POST", 5.0));
        item2.addAnswer(new Answer("ADD", -5.0));
        item2.addAnswer(new Answer("DELETE", 5.0));
        item2.addAnswer(new Answer("REMOVE", -5.0));
        
        Item item3 = new Item("Which of the following is the base namespace for a SOAP message?");
        item3.addAnswer(new Answer("http://www.w3.org/2001/06/soap-message", 0.0));
        item3.addAnswer(new Answer("http://www.w3.org/2001/06/soap-envelope", 5.0));
        item3.addAnswer(new Answer("http://www.w3.org/2001/06/soap/message", 0.0));
        item3.addAnswer(new Answer("http://www.w3.org/2001/06/soap/soapEnvelope", 0.0));

        Item item4 = new Item("What's the difference between a microservices-oriented architecture (MOA) and a service-oriented architecture (SOA)?");
        item4.addAnswer(new Answer("An SOA uses intermediation technology to facilitate communication between services", 5.0));
        item4.addAnswer(new Answer("An MOA shares as little data as possible while an SOA shares as much data as possible", 5.0));
        item4.addAnswer(new Answer("A developer can run a monolithic application with SOA principles", 5.0));

        Item item5 = new Item("What do you mean by one to many relationships?");
        item5.addAnswer(new Answer("One class may have many teachers", -5.0));
        item5.addAnswer(new Answer("One teacher can have many classes", 5.0));
        item5.addAnswer(new Answer("Many classes may have many teachers", -5.0));
        item5.addAnswer(new Answer("Many teachers may have many classes", -5.0));

        DomainProblem http = new DomainProblem("HTTP");
        http.addItem(item1);
        DomainProblem rest = new DomainProblem("REST");
        rest.addItem(item2);
        DomainProblem soa = new DomainProblem("SOA");
        soa.addItem(item3);
        DomainProblem microServices = new DomainProblem("Microservices");
        microServices.addItem(item4);
        DomainProblem databases = new DomainProblem("Databases");
        databases.addItem(item5);

        KnowledgeSpace knowledgeSpace = new KnowledgeSpace("expected1", false);
        knowledgeSpace.addEdge(new Edge(http, rest));
        knowledgeSpace.addEdge(new Edge(rest, soa));
        knowledgeSpace.addEdge(new Edge(rest, microServices));
        knowledgeSpace.addEdge(new Edge(soa, databases));
        knowledgeSpace.addEdge(new Edge(microServices, databases));

        Domain domain = new Domain("Web programming");
        domain.addKnowledgeSpace(knowledgeSpace);
        
        this.domainRepository.save(domain);

        item1 = new Item("Identify the device through which data and instructions are entered into a computer?");
        item1.addAnswer(new Answer("Memory", 0.0));
        item1.addAnswer(new Answer("Keyboard", 5.0));
        item1.addAnswer(new Answer("Software", 0.0));
        item1.addAnswer(new Answer("Webcam", 5.0));
        
        item2 = new Item("Which one of these is working as temporary memory?");
        item2.addAnswer(new Answer("ROM", -5.0));
        item2.addAnswer(new Answer("RAM", 5.0));
        item2.addAnswer(new Answer("Cache", 5.0));
        item2.addAnswer(new Answer("Hard Drive", -5.0));
        
        item3 = new Item("Which software is a file compression utility?");
        item3.addAnswer(new Answer("7-zip", 5.0));
        item3.addAnswer(new Answer("WinRAR", 5.0));
        item3.addAnswer(new Answer("File Explorer", -5.0));

        item4 = new Item("In which situation running applications of an user account remains active?");
        item4.addAnswer(new Answer("When we Log Off", -5.0));
        item4.addAnswer(new Answer("When we Shut Down", -5.0));
        item4.addAnswer(new Answer("When we Switch User", 5.0));

        item5 = new Item("Which of the following is a correct format of Email address?");
        item5.addAnswer(new Answer("teacher.school.com", -5.0));
        item5.addAnswer(new Answer("teacher.mike@school.com", 5.0));
        item5.addAnswer(new Answer("teacher-mike@school.com", -5.0));
        item5.addAnswer(new Answer("teacher@mikec.school.com", 5.0));

        Item item6 = new Item("Which one of the following is a search engine?");
        item6.addAnswer(new Answer("Google", 5.0));
        item6.addAnswer(new Answer("Microsoft", -5.0));
        item6.addAnswer(new Answer("Windows", -5.0));
        item6.addAnswer(new Answer("Yahoo", 5.0));

        DomainProblem basic = new DomainProblem("Basic Computer Knowledge");
        basic.addItem(item1);
        basic.addItem(item2);

        DomainProblem operations = new DomainProblem("Computer Operation Proficiency");
        operations.addItem(item3);
        operations.addItem(item4);

        DomainProblem web = new DomainProblem("Basic Web Knowledge");
        web.addItem(item5);
        web.addItem(item6);

        knowledgeSpace = new KnowledgeSpace("expected1", false);
        knowledgeSpace.addEdge(new Edge(basic, web));
        knowledgeSpace.addEdge(new Edge(web, operations));

        Domain general = new Domain("General Computer Usage");
        general.addKnowledgeSpace(knowledgeSpace);

        domainRepository.save(general);*/
    }
}
