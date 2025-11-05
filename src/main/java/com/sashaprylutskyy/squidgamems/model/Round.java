package com.sashaprylutskyy.squidgamems.model;

import com.sashaprylutskyy.squidgamems.model.enums.CompetitionRoundStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "rounds")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long competitionId;

    @Column(nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private Byte roundNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompetitionRoundStatus status;

    private Long startedAt;
    private Long endedAt;

    public Round() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public Byte getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Byte roundNumber) {
        this.roundNumber = roundNumber;
    }

    public CompetitionRoundStatus getStatus() {
        return status;
    }

    public void setStatus(CompetitionRoundStatus status) {
        this.status = status;
    }

    public Long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }

    public Long getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Long endedAt) {
        this.endedAt = endedAt;
    }
}
