package com.platform.kspace.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
public class Professor extends User {

    public Professor() {
        super();
    }

    public Professor(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }
}
