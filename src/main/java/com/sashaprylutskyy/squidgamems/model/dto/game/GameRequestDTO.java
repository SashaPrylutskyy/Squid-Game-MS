package com.sashaprylutskyy.squidgamems.model.dto.game;

import jakarta.validation.constraints.*;

public class GameRequestDTO {

    @NotBlank(message = "Game title is required")
    @Size(min = 3, max = 30, message = "Game title should be within 3 and 30 chars inclusive.")
    private String gameTitle;

    @NotBlank(message = "Game description is required")
    @Size(min = 15, max = 300, message = "Game description should be within 15 and 300 chars inclusive.")
    private String description;

    @NotNull(message = "Game duration is required")
    @Min(value = 5, message = "Game duration should be at least 5 minutes.")
    @Max(value = 127, message = "Game duration should not exceed 127 minutes.")
    private Byte gameDuration;

    public GameRequestDTO() {

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
}
