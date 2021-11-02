package com.platform.kspace.dto;

public class PossibleAnswerDTO {
    private Integer id;
    private String text;

    public PossibleAnswerDTO() {}

    public PossibleAnswerDTO(Integer id, String text) {
        this.id = id;
        this.text = text;
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
}
