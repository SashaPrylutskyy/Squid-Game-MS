package com.sashaprylutskyy.squidgamems.model.dto.roundResult;

import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;

import java.util.List;

public class RoundResultSummaryDTO {

    private Long roundId;
    private List<UserSummaryDTO> players;

    public RoundResultSummaryDTO() {

    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public List<UserSummaryDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<UserSummaryDTO> players) {
        this.players = players;
    }
}
