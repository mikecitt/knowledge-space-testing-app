package com.platform.kspace.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class KnowledgeSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean isReal;

    @ManyToOne
    private Domain domain;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "knowledgeSpace")
    private List<Edge> edges;

    public KnowledgeSpace() {
        
    }

    public KnowledgeSpace(String name, Boolean isReal) {
        this.name = name;
        this.isReal = isReal;
        this.edges = new ArrayList<>();
    }

    public KnowledgeSpace(String name, Boolean isReal, List<Edge> edges) {
        this.name = name;
        this.isReal = isReal;
        this.edges = edges;
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
}
