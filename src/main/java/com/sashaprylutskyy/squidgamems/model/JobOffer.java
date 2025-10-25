package com.sashaprylutskyy.squidgamems.model;

import com.sashaprylutskyy.squidgamems.model.enums.JobOfferStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.UUID;

@Entity
@Table(name = "job_offers")
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lobbyId;

    @ManyToOne
    @JoinColumn(name = "offered_by_id")
    private User offeredBy;

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
    private JobOfferStatus offerStatus;

    @Column(nullable = false)
    private Long createdAt;

    public JobOffer() {
    }

    public JobOffer(Long lobbyId, User offeredBy, Role role, String email,
                    UUID token, JobOfferStatus offerStatus, Long createdAt) {
        this.lobbyId = lobbyId;
        this.offeredBy = offeredBy;
        this.role = role;
        this.email = email;
        this.token = token;
        this.offerStatus = offerStatus;
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

    public User getOfferedBy() {
        return offeredBy;
    }

    public void setOfferedBy(User offeredBy) {
        this.offeredBy = offeredBy;
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

    public JobOfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(JobOfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
