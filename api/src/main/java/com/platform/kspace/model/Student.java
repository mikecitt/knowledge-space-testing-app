package com.platform.kspace.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("U")
public class Student extends User {
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "takenBy")
    private List<TakenTest> takenTests;

    public Student() {
        super();
    }

    public Student(String name, String email, String password) {
        super(name, email, password);
    }
}
