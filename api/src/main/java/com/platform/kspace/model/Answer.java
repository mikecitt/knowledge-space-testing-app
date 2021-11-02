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
}
