package com.sashaprylutskyy.squidgamems.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ref_codes")
public class RefCode {

    @Id
    @Column(nullable = false, unique = true)
    private String refCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public RefCode() {

    }

    public RefCode(String refCode, User user) {
        this.refCode = refCode;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }
}
