package com.platform.kspace.dto;

public class SectionDTO {
    private Integer id;
    private String name;
    private Integer testId;

    public SectionDTO() {
    }

    public SectionDTO(Integer id, String name, Integer testId) {
        this.id = id;
        this.name = name;
        this.testId = testId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTestId() {
        return this.testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }
}
