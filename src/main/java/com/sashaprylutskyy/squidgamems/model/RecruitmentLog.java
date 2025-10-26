package com.sashaprylutskyy.squidgamems.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recruitment_logs")
public class RecruitmentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User player;

    @ManyToOne
    @JoinColumn(name = "refCode", nullable = false)
    private RefCode refCode;

    private Long createdAt;

    public RecruitmentLog() {

    }

    public RecruitmentLog(User player, RefCode refCode, Long createdAt) {
        this.player = player;
        this.refCode = refCode;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public User getPlayer() {
        return player;
    }

    public RefCode getRefCode() {
        return refCode;
    }

    public Long getCreatedAt() {
        return createdAt;
    }
}
