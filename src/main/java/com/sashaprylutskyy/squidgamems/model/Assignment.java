package com.sashaprylutskyy.squidgamems.model;

import com.sashaprylutskyy.squidgamems.model.enums.Env;
import jakarta.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Env env;

    @Column(nullable = false)
    private Long envId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private User assignedBy;

    @Column(nullable = false)
    private Long assignedAt;

    public Assignment() {

    }

    public Assignment(Env env, Long envId, User user, User assignedBy, Long assignedAt) {
        this.env = env;
        this.envId = envId;
        this.user = user;
        this.assignedBy = assignedBy;
        this.assignedAt = assignedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Env getEnv() {
        return env;
    }

    public void setEnv(Env env) {
        this.env = env;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Long getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Long assignedAt) {
        this.assignedAt = assignedAt;
    }
}
