package com.platform.kspace.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "domain")
    private Set<Item> items;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "domain")
    private Set<KnowledgeSpace> knowledgeSpaces;
}
