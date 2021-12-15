package com.platform.kspace.dto;

import java.util.List;

public class DomainDTO {
    private Integer id;
    private String text;
    private List<DomainProblemDTO> domainProblems;

    public DomainDTO() {
    }

    public DomainDTO(Integer id, String text, List<DomainProblemDTO> domainProblems) {
        this.id = id;
        this.text = text;
        this.domainProblems = domainProblems;
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

    public List<DomainProblemDTO> getDomainProblems() {
        return this.domainProblems;
    }

    public void setDomainProblems(List<DomainProblemDTO> domainProblems) {
        this.domainProblems = domainProblems;
    }    
}
