package com.sashaprylutskyy.squidgamems.model.dto.assignment;

import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;

import java.util.List;

public class AssignmentResponseDTO {

    private Long competitionId;
    private List<UserSummaryDTO> players;
    private UserSummaryDTO assignedBy;
    private Long assignedAt;

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

    public UserSummaryDTO getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(UserSummaryDTO assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Long getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Long assignedAt) {
        this.assignedAt = assignedAt;
    }
}
