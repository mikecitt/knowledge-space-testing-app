package com.platform.kspace.model;

import java.util.Date;
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
import javax.persistence.OneToOne;

@Entity
public class TakenTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    private Date start;

    @Column
    private Date end;

    @ManyToOne
    private Student takenBy;

    @OneToOne
    private Test test;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Answer> answers;
}
