package com.platform.kspace.dto;

import java.util.Date;

public class TakenTestDTO {
    private Integer id;
    private String testName;
    private Double testDuration;
    private Date start;
    private Date end;
    private double score;

    public TakenTestDTO() {}

    public TakenTestDTO(Integer id, String testName, Double testDuration, Date start, Date end, double score) {
        this.id = id;
        this.testName = testName;
        this.testDuration = testDuration;
        this.start = start;
        this.end = end;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Double getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(Double testDuration) {
        this.testDuration = testDuration;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
