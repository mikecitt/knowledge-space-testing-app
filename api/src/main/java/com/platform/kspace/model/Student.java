package com.platform.kspace.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue("U")
public class Student extends User {
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "takenBy")
    private List<TakenTest> takenTests;

    @Column(nullable = false)
    private Date dateOfBirth;

    public Student() {
        super();
    }

    public Student(String firstName, String lastName, String email, String password, Date dateOfBirth) {
        super(firstName, lastName, email, password);
        this.dateOfBirth = dateOfBirth;
    }
}
