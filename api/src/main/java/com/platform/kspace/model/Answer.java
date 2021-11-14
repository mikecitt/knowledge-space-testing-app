package com.platform.kspace.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    private String text;

    @Column
    private Double points;

    @ManyToOne
    private Item item;

    public Answer() {}

    public Answer(String text, Double points, Item item) {
        this.text = text;
        this.points = points;
        this.item = item;
    }

    public Answer(String text, Double points) {
        this.text = text;
        this.points = points;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Double getPoints() {
        return points;
    }

    public Item getItem() {
        return item;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
