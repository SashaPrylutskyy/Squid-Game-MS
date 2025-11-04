package com.sashaprylutskyy.squidgamems.model.dto.game;

import com.sashaprylutskyy.squidgamems.model.User;
import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;

public class GameSummaryDTO {

    private Long id;
    private String gameTitle;
    private String description;
    private Byte gameDuration;
    private UserSummaryDTO createdBy;

    public GameSummaryDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Byte gameDuration) {
        this.gameDuration = gameDuration;
    }

    public UserSummaryDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserSummaryDTO createdBy) {
        this.createdBy = createdBy;
    }
}
