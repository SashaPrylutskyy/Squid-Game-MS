package com.sashaprylutskyy.squidgamems.model;

import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;
import jakarta.persistence.*;


@Entity
@Table(name = "competitions")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Long lobbyId;
    private Long currentRoundId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetitionRoundStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    private Long createdAt;
    private Long updatedAt;

    public Competition() {

    }

    public Competition(String title, Long lobbyId, User createdBy, Long currentTime) {
        this(title, lobbyId, CompetitionRoundStatus.PENDING, createdBy, currentTime, currentTime);
    }

    public Competition(String title, Long lobbyId, CompetitionRoundStatus status, User createdBy, Long createdAt, Long updatedAt) {
        this.title = title;
        this.lobbyId = lobbyId;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public Long getCurrentRoundId() {
        return currentRoundId;
    }

    public void setCurrentRoundId(Long currentRoundId) {
        this.currentRoundId = currentRoundId;
    }

    public CompetitionRoundStatus getStatus() {
        return status;
    }

    public void setStatus(CompetitionRoundStatus status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
