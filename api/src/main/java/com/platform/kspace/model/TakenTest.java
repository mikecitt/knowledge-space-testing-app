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
    
    @Column(nullable = false)
    private Date start;

    @Column(nullable = true)
    private Date end;

    @ManyToOne
    private Student takenBy;

    @OneToOne
    private Test test;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Answer> answers;

    public TakenTest() {}

    public TakenTest(Date start, Student takenBy, Test test) {
        this.start = start;
        this.takenBy = takenBy;
        this.test = test;
    }

    public Integer getId() {
        return id;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Student getTakenBy() {
        return takenBy;
    }

    public Test getTest() {
        return test;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
