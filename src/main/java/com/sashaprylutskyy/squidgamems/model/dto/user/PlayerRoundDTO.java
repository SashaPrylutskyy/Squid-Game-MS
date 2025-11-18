package com.sashaprylutskyy.squidgamems.model.dto.user;

import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;

public class PlayerRoundDTO {
    private Long id;
    private String gameTitle;
    private CompetitionRoundStatus status;

    public PlayerRoundDTO(Long id, String gameTitle, CompetitionRoundStatus status) {
        this.id = id;
        this.gameTitle = gameTitle;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public CompetitionRoundStatus getStatus() {
        return status;
    }
}