package com.sashaprylutskyy.squidgamems.model.dto.round;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RoundRequestDTO {

    @NotNull
    private Long competitionId;

    @NotNull
    @Size(min = 1, message = "At least one round must be provided")
    private List<RoundSummaryDTO> roundSummaryDTOs;

    public RoundRequestDTO() {

    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public List<RoundSummaryDTO> getRoundSummaryDTOs() {
        return roundSummaryDTOs;
    }

    public void setRoundSummaryDTOs(List<RoundSummaryDTO> roundSummaryDTOs) {
        this.roundSummaryDTOs = roundSummaryDTOs;
    }
}
