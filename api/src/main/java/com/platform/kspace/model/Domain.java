package com.platform.kspace.model;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "domain")
    private List<DomainProblem> domainProblems;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "domain")
    private Set<KnowledgeSpace> knowledgeSpaces;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "domain")
    private Set<Test> tests;


    public Domain() {
    }

    public Domain(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Domain(Integer id, String name, List<DomainProblem> domainProblems) {
        this.id = id;
        this.name = name;
        this.domainProblems = domainProblems;
    }

    public Domain(Integer id, String name, List<DomainProblem> domainProblems, Set<KnowledgeSpace> knowledgeSpaces, Set<Test> tests) {
        this.id = id;
        this.name = name;
        this.domainProblems = domainProblems;
        this.knowledgeSpaces = knowledgeSpaces;
        this.tests = tests;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DomainProblem> getDomainProblems() {
        return this.domainProblems;
    }

    public void setDomainProblems(List<DomainProblem> domainProblems) {
        this.domainProblems = domainProblems;
    }

    public Set<KnowledgeSpace> getKnowledgeSpaces() {
        return this.knowledgeSpaces;
    }

    public void setKnowledgeSpaces(Set<KnowledgeSpace> knowledgeSpaces) {
        this.knowledgeSpaces = knowledgeSpaces;
    }

    public Set<Test> getTests() {
        return this.tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }
}
