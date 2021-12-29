package com.platform.kspace.dto;

public class EdgeDTO {
    private DomainProblemDTO from;
    private DomainProblemDTO to;

    public EdgeDTO() {
    }

    public EdgeDTO(DomainProblemDTO from, DomainProblemDTO to) {
        this.from = from;
        this.to = to;
    }

    public DomainProblemDTO getFrom() {
        return this.from;
    }

    public void setFrom(DomainProblemDTO from) {
        this.from = from;
    }

    public DomainProblemDTO getTo() {
        return this.to;
    }

    public void setTo(DomainProblemDTO to) {
        this.to = to;
    }
}
