package com.sashaprylutskyy.squidgamems.model.dto.roundResult;

import java.util.List;

public class RoundResultRequestDTO {

    private boolean isValid;
    private Long roundId;
    private List<Long> playerIds;

    public RoundResultRequestDTO() {

    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public List<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }
}
