package com.platform.kspace.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToOne
    private Test test;

    @ManyToOne
    private KnowledgeSpace knowledgeSpace;

    // TODO: remove from model
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "section")
    private List<Item> items;

    public Section() {
        this.items = new ArrayList<Item>();
    }

    public Section(String name) {
        this.name = name;
        this.items = new ArrayList<Item>();
    }

    public Test getTest() {
        return this.test;
    }

    public void setTest(Test test) {
        this.test = test;
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

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        item.setSection(this);
        this.items.add(item);
    }

}
