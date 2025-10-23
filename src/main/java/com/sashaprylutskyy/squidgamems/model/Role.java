package com.sashaprylutskyy.squidgamems.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleTitle;

    public Role() {

    }

    @Override
    public String toString() {
        return roleTitle;
    }

    public Role(Long id) {
        this.id = id;
    }
}
