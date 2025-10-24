package com.sashaprylutskyy.squidgamems.model;

import com.sashaprylutskyy.squidgamems.model.enums.InvitationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.UUID;

@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lobbyId;

    @ManyToOne
    @JoinColumn(name = "invited_by_id")
    private User invitedBy;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private UUID token;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationStatus inviteStatus;

    @Column(nullable = false)
    private Long createdAt;

    public Invitation() {
    }

    public Invitation(Long lobbyId, User invitedBy, Role role, String email,
                      UUID token, InvitationStatus inviteStatus, Long createdAt) {
        this.lobbyId = lobbyId;
        this.invitedBy = invitedBy;
        this.role = role;
        this.email = email;
        this.token = token;
        this.inviteStatus = inviteStatus;
        this.createdAt = createdAt;
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

    public User getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(User invitedBy) {
        this.invitedBy = invitedBy;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public InvitationStatus getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(InvitationStatus inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
