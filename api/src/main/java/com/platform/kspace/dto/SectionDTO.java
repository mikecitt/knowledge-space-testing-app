package com.platform.kspace.dto;

import java.util.List;

public class SectionDTO {
    private Integer id;
    private String name;
    private Integer testId;
    private List<ItemDTO> items;

    public SectionDTO() {
    }

    public SectionDTO(Integer id, String name, Integer testId, List<ItemDTO> items) {
        this.id = id;
        this.name = name;
        this.testId = testId;
        this.items = items;
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

    public List<ItemDTO> getItems() {
        return this.items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
