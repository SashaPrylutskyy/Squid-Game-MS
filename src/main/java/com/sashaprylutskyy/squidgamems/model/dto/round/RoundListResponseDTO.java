package com.sashaprylutskyy.squidgamems.model.dto.round;

import java.util.List;

public class RoundListResponseDTO {

    private Long competitionId;
    private List<RoundSummaryDTO> roundSummaryDTOs;

    public RoundListResponseDTO() {

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
