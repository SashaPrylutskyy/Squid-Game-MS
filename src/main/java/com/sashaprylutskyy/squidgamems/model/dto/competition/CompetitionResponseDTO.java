package com.sashaprylutskyy.squidgamems.model.dto.competition;

import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.enums.CompetitionStatus;

import java.util.UUID;

public class CompetitionResponseDTO {

    private UUID uuid;
    private String title;
    private Long lobbyId;
    private CompetitionStatus status;
    private UserSummaryDTO createdBy;
    private Long createdAt;
    private Long updatedAt;

    public CompetitionResponseDTO() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public CompetitionStatus getStatus() {
        return status;
    }

    public void setStatus(CompetitionStatus status) {
        this.status = status;
    }

    public UserSummaryDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserSummaryDTO createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createAt) {
        this.createdAt = createAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
