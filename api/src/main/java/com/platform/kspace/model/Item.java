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
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String text;

    @Column
    private byte[] picture;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
    private List<Answer> answers;

    @ManyToOne
    private Section section;

    public Item() {
        this.answers = new ArrayList<>();
    }

    public Item(String text) {
        this.text = text;
        this.answers = new ArrayList<>();
    }

    public Item(String text, byte[] picture) {
        this.text = text;
        this.picture = picture;
        this.answers = new ArrayList<>();
    }

    public Item(String text, byte[] picture, List<Answer> answers) {
        this.text = text;
        this.picture = picture;
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

    public byte[] getPicture() {
        return this.picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
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

}
