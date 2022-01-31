package com.platform.kspace.dto;

import java.util.Date;
import java.util.UUID;

public class TestDTO {
    private Integer id;
    private String name;
    private Double timer;
    private Date validFrom;
    private Date validUntil;
    private UUID createdById;
    private DomainDTO domain;

    public TestDTO() {
    }

    public TestDTO(Integer id, String name, Double timer, Date validFrom, Date validUntil, UUID createdById, DomainDTO domain) {
        this.id = id;
        this.name = name;
        this.timer = timer;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.createdById = createdById;
        this.domain = domain;
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

    public Double getTimer() {
        return this.timer;
    }

    public void setTimer(Double timer) {
        this.timer = timer;
    }

    public Date getValidFrom() {
        return this.validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return this.validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public UUID getCreatedById() {
        return this.createdById;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public DomainDTO getDomain() {
        return domain;
    }

    public void setDomain(DomainDTO domain) {
        this.domain = domain;
    }
}
