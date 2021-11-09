package com.platform.kspace.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name="authority")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    public Authority() {
        super();
    }

    public Authority(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
