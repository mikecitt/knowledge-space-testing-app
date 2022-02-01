package com.platform.kspace.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String text;

    @Column
    private String imgName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
    private List<Answer> answers;

    @ManyToOne
    private DomainProblem domainProblem;

    @ManyToOne
    private Section section;

    public Item() {
        this.answers = new ArrayList<>();
    }

    public Item(String text) {
        this.text = text;
        this.answers = new ArrayList<>();
    }

    public Item(String text, String imgName) {
        this.text = text;
        this.imgName = imgName;
        this.answers = new ArrayList<>();
    }

    public Item(String text, String imgName, List<Answer> answers) {
        this.text = text;
        this.imgName = imgName;
        this.answers = answers;
        this.answers.forEach(a -> a.setItem(this)); // testing
    }

    public Integer getId() {
        return this.id;
    }

    public Section getSection() {
        return this.section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgName() {
        return this.imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public void fixAnswers() {
        this.answers.forEach(a -> a.setItem(this));
    }

    public DomainProblem getDomainProblem() {
        return domainProblem;
    }

    public void setDomainProblem(DomainProblem domainProblem) {
        this.domainProblem = domainProblem;
    }

    public void removeDomainProblem() {
        this.domainProblem = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
