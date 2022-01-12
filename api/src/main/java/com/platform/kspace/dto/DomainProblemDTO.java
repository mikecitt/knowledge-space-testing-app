package com.platform.kspace.dto;

import java.util.UUID;

public class DomainProblemDTO {
    private UUID id;
    private String text;

    public DomainProblemDTO() {}

    public DomainProblemDTO(UUID id, String text) {
        this.id = id;
        this.text = text;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
