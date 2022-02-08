package com.platform.kspace.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class KnowledgeSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isReal;

    @ManyToOne
    private Domain domain;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "knowledgeSpace", orphanRemoval = true)
    private List<Edge> edges;

    @ElementCollection
    @CollectionTable(name="knowledgeStates")
    private List<String> knowledgeStates;

    public KnowledgeSpace() {
        this.edges = new ArrayList<>();
        this.knowledgeStates = new ArrayList<>();
    }

    public KnowledgeSpace(String name, Boolean isReal) {
        this.name = name;
        this.isReal = isReal;
        this.edges = new ArrayList<>();
        this.knowledgeStates = new ArrayList<>();
    }

    public KnowledgeSpace(String name, Boolean isReal, List<Edge> edges) {
        this.name = name;
        this.isReal = isReal;
        this.edges = edges;
        this.knowledgeStates = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        edge.setKnowledgeSpace(this);
        this.edges.add(edge);
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsReal() {
        return this.isReal;
    }

    public Boolean getIsReal() {
        return this.isReal;
    }

    public void setIsReal(Boolean isReal) {
        this.isReal = isReal;
    }

    public Domain getDomain() {
        return this.domain;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void addKnowledgeStates(String stateStr) {
        this.knowledgeStates.add(stateStr);
    }

    public List<String[]> getKnowledgeStates() {
        List<String[]> states = new ArrayList<>();
        for (String stateStr : this.knowledgeStates) {
            states.add(stateStr.split(","));
        }

        return states;
    }
}
