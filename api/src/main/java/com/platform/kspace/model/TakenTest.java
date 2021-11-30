package com.platform.kspace.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public void clearAnswersOfItem(Integer itemId) {
        answers = answers.stream()
                .filter(answer -> !answer.getItem().getId().equals(itemId))
                .collect(Collectors.toList());
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public boolean containsAnswer(Integer id) {
        boolean a = answers.stream().anyMatch(answer -> answer.getId().equals(id));
        return answers.stream().anyMatch(answer -> answer.getId().equals(id));
    }
}
