package com.sashaprylutskyy.squidgamems.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Lobbies")
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long lobbyId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private Long assignedAt;

    public Lobby() {

    }

    public Lobby(Long lobbyId, User user, Long assignedAt) {
        this.lobbyId = lobbyId;
        this.user = user;
        this.assignedAt = assignedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Long assignedAt) {
        this.assignedAt = assignedAt;
    }
}
