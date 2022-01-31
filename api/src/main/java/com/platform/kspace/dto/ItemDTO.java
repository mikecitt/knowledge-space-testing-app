package com.platform.kspace.dto;

import java.util.List;

public class ItemDTO {
    private Integer id;
    private String text;
    private String imgName;
    private List<AnswerDTO> answers;
    private Integer sectionId;

    public ItemDTO() {}

    public ItemDTO(Integer id, String text, String imgName, List<AnswerDTO> answers, Integer sectionId) {
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

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
}
