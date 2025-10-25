package com.sashaprylutskyy.squidgamems.model;

import jakarta.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long competitionId;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private User player;

    @ManyToOne
    @JoinColumn(name = "assigned_by", nullable = false)
    private User assignedBy;

    @Column(nullable = false)
    private Long assignedAt;

    public Assignment() {

    }

    public Assignment(Long competitionId, User player, User assignedBy, Long assignedAt) {
        this.competitionId = competitionId;
        this.player = player;
        this.assignedBy = assignedBy;
        this.assignedAt = assignedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
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
