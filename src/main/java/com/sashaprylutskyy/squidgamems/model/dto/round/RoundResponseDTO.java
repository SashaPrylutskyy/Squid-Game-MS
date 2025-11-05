package com.sashaprylutskyy.squidgamems.model.dto.round;

import java.util.List;

public class RoundResponseDTO {

    private Long competitionId;
    private List<RoundSummaryDTO> roundSummaryDTOs;

    public RoundResponseDTO() {

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
