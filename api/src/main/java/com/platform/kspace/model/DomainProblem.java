package com.platform.kspace.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DomainProblem {
    @Id
    protected UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "domainProblem")
    private Set<Item> items;

    public DomainProblem() {
    }

    public DomainProblem(String name) {
        this.name = name;
        items = new HashSet<>();
    }

    public DomainProblem(UUID id, String name) {
        this.id = id;
        this.name = name;
        items = new HashSet<>();
    }

    public DomainProblem(String name, Set<Item> items) {
        this.name = name;
        this.items = items;
        items = new HashSet<>();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainProblem that = (DomainProblem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
