package com.platform.kspace.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "domain")
    private Set<KnowledgeSpace> knowledgeSpaces;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "domain")
    private Set<Test> tests;


    public Domain() {
    }

    public Domain(String name) {
        this.name = name;
        this.knowledgeSpaces = new HashSet<>();
    }

    public Domain(String name, Set<KnowledgeSpace> knowledgeSpaces, Set<Test> tests) {
        this.name = name;
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

    public Set<KnowledgeSpace> getKnowledgeSpaces() {
        return this.knowledgeSpaces;
    }

    public void setKnowledgeSpaces(Set<KnowledgeSpace> knowledgeSpaces) {
        this.knowledgeSpaces = knowledgeSpaces;
    }

    public void addKnowledgeSpace(KnowledgeSpace knowledgeSpace) {
        knowledgeSpace.setDomain(this);
        this.knowledgeSpaces.add(knowledgeSpace);
    }

    public Set<Test> getTests() {
        return this.tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }
}
