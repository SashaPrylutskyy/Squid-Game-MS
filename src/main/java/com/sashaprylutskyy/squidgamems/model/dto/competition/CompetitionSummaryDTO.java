package com.sashaprylutskyy.squidgamems.model.dto.competition;

import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;

public class CompetitionSummaryDTO {

    private Long id;
    private String title;
    private CompetitionRoundStatus status;

    public CompetitionSummaryDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CompetitionRoundStatus getStatus() {
        return status;
    }

    public void setStatus(CompetitionRoundStatus status) {
        this.status = status;
    }
}
