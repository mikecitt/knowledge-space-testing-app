package com.platform.kspace.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double timer;

    @Column(nullable = false)
    private Date validFrom;

    @Column(nullable = false)
    private Date validUntil;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "test")
    private Set<Section> sections;

    @ManyToOne
    private Domain domain;

    @ManyToOne(fetch = FetchType.LAZY)
    private Professor createdBy;

    public Test() {
        this.sections = new HashSet<>();
    }

    public Test(String name, Double timer, Date validFrom, Date validUntil) {
        this.name = name;
        this.timer = timer;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.sections = new HashSet<>();
    }

    public Integer getId() {
        return this.id;
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

    public Set<Section> getSections() {
        return this.sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        section.setTest(this);
        this.sections.add(section);
    }

    public Professor getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Professor createdBy) {
        this.createdBy = createdBy;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
}
