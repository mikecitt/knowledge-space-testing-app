package com.platform.kspace.component;

import java.util.Calendar;
import java.util.HashSet;

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
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private UserRepository userRepository;

    private DomainRepository  domainRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, DomainRepository domainRepository) {
        this.userRepository = userRepository;
        this.domainRepository = domainRepository;
        LoadData();
    }

    private void LoadData() {
        Professor p = new Professor("Petar", "Petrovic", "petar@example.com", "$2a$04$P2R/ohGi2eYUJw02EEZaveX37jwcXb4E.RnwQo8MgP8EgNP0vjfN.");
        p.setPassword("qwerty", true);
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

        Item item1 = new Item("Dummy question1");
        item1.addAnswer(new Answer("Dummy answer1", 5.0));
        item1.addAnswer(new Answer("Dummy answer2", 0.0));
        item1.addAnswer(new Answer("Dummy answer3", -5.0));
        
        Item item2 = new Item("Dummy question2");
        item2.addAnswer(new Answer("Dummy answer1", 5.0));
        item2.addAnswer(new Answer("Dummy answer2", 0.0));
        item2.addAnswer(new Answer("Dummy answer3", -5.0));
        
        Item item3 = new Item("Dummy question3");
        item3.addAnswer(new Answer("Dummy answer1", 5.0));
        item3.addAnswer(new Answer("Dummy answer2", 0.0));
        item3.addAnswer(new Answer("Dummy answer3", -5.0));

        Item item4 = new Item("Dummy question4");
        item4.addAnswer(new Answer("Dummy answer1", 5.0));
        item4.addAnswer(new Answer("Dummy answer2", 0.0));
        item4.addAnswer(new Answer("Dummy answer3", -5.0));

        Item item5 = new Item("Dummy question5");
        item5.addAnswer(new Answer("Dummy answer1", 5.0));
        item5.addAnswer(new Answer("Dummy answer2", 0.0));
        item5.addAnswer(new Answer("Dummy answer3", -5.0));

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

        KnowledgeSpace knowledgeSpace = new KnowledgeSpace("ocekivani1", false);
        knowledgeSpace.addEdge(new Edge(http, rest));
        knowledgeSpace.addEdge(new Edge(rest, soa));
        knowledgeSpace.addEdge(new Edge(rest, microServices));
        knowledgeSpace.addEdge(new Edge(soa, databases));
        knowledgeSpace.addEdge(new Edge(microServices, databases));

        Domain domain = new Domain("Web programiranje");
        domain.addKnowledgeSpace(knowledgeSpace);
        
        this.domainRepository.save(domain);
    }
}
