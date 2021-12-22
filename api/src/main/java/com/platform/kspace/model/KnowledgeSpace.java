package com.platform.kspace.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "knowledgeSpace")
    private Set<Edge> edges;

    public KnowledgeSpace(String name, Boolean isReal) {
        this.name = name;
        this.isReal = isReal;
        edges = new HashSet<>();
    }

    public void addEdge(Edge edge) {
        edge.setKnowledgeSpace(this);
        this.edges.add(edge);
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
}
