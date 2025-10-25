package com.sashaprylutskyy.squidgamems.model.dto.assignment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AssignmentRequestDTO {

    @NotNull
    private Long competitionId;

    @NotNull
    @NotEmpty
    private List<Long> playerIds;

    public AssignmentRequestDTO() {

    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public List<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }
}
