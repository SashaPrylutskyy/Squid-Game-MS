package com.sashaprylutskyy.squidgamems.model.dto.round;

public class CurrentRoundDTO {
    private Long id;
    private Long competitionId;
    private String gameTitle;

    public CurrentRoundDTO(Long id, Long competitionId, String gameTitle) {
        this.id = id;
        this.competitionId = competitionId;
        this.gameTitle = gameTitle;
    }

    public CurrentRoundDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }
}
