package com.platform.kspace.dto;

import java.util.List;

public class StudentItemDTO {
    private Integer id;
    private String text;
    private String imgName;
    private List<PossibleAnswerDTO> answers;
    private Integer sectionId;

    public StudentItemDTO() {}

    public StudentItemDTO(Integer id, String text, String imgName, List<PossibleAnswerDTO> answers, Integer sectionId) {
        this.id = id;
        this.text = text;
        this.imgName = imgName;
        this.answers = answers;
        this.sectionId = sectionId;
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

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public List<PossibleAnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<PossibleAnswerDTO> answers) {
        this.answers = answers;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
}
