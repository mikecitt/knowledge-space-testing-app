package com.platform.kspace.dto;

public class AnswerDTO {
    private Integer id;
    private String text;
    private Double points;

    public AnswerDTO() {}

    public AnswerDTO(Integer id, String text, Double points) {
        this.id = id;
        this.text = text;
        this.points = points;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getPoints() {
        return this.points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

}
