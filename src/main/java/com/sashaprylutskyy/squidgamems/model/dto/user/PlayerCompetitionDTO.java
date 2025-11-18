package com.sashaprylutskyy.squidgamems.model.dto.user;

public class PlayerCompetitionDTO {
    private Long id;
    private String title;

    public PlayerCompetitionDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}