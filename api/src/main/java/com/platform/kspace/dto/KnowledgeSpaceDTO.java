package com.platform.kspace.dto;

import java.util.List;

public class KnowledgeSpaceDTO {
    private Integer id;
    private String name;
    private Boolean isReal;
    private List<EdgeDTO> edges;


    public KnowledgeSpaceDTO() {
    }

    public KnowledgeSpaceDTO(Integer id, String name, Boolean isReal, List<EdgeDTO> edges) {
        this.id = id;
        this.name = name;
        this.isReal = isReal;
        this.edges = edges;
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

    public Boolean isIsReal() {
        return this.isReal;
    }

    public Boolean getIsReal() {
        return this.isReal;
    }

    public void setIsReal(Boolean isReal) {
        this.isReal = isReal;
    }

    public List<EdgeDTO> getEdges() {
        return this.edges;
    }

    public void setEdges(List<EdgeDTO> edges) {
        this.edges = edges;
    }
}
