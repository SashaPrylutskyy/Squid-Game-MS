package com.sashaprylutskyy.squidgamems.model;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String gameTitle;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Byte gameDuration;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;

    public Game() {

    }

    public Game(String gameTitle, String description, Byte gameDuration, User createdBy, Long currentTime) {
        this.gameTitle = gameTitle;
        this.description = description;
        this.gameDuration = gameDuration;
        this.createdBy = createdBy;
        createdAt = currentTime;
        updatedAt = currentTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Byte gameDuration) {
        this.gameDuration = gameDuration;
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
        updatedAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
