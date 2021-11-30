package com.platform.kspace.dto;

public class PossibleAnswerDTO {
    private Integer id;
    private boolean selected;
    private String text;

    public PossibleAnswerDTO() {}

    public PossibleAnswerDTO(Integer id, String text) {
        this.id = id;
        this.selected = false;
        this.text = text;
    }

    public PossibleAnswerDTO(Integer id, boolean selected, String text) {
        this.id = id;
        this.selected = selected;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
