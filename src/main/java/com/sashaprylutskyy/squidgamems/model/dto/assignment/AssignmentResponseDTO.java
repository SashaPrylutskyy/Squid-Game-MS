package com.sashaprylutskyy.squidgamems.model.dto.assignment;

import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;

import java.util.List;

public class AssignmentResponseDTO {

    private Long competitionId;
    private List<UserSummaryDTO> players;
    private UserSummaryDTO processedBy;
    private Long processedAt;

    public AssignmentResponseDTO() {

    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public List<UserSummaryDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<UserSummaryDTO> players) {
        this.players = players;
    }

    public UserSummaryDTO getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(UserSummaryDTO processedBy) {
        this.processedBy = processedBy;
    }

    public Long getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Long processedAt) {
        this.processedAt = processedAt;
    }
}
