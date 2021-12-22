package com.platform.kspace.dto;


public class DomainDTO {
    private Integer id;
    private String text;

    public DomainDTO() {
    }

    public DomainDTO(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
