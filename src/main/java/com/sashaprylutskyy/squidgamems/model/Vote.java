package com.sashaprylutskyy.squidgamems.model;

import jakarta.persistence.*;

@Entity
@Table(name = "votes",
       uniqueConstraints = {
           @UniqueConstraint(
               name = "uk_round_player",
               columnNames = {"round_id", "player_id"}
           )
       }
)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private User player;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private Round round;

    private Boolean isQuit;

    public Vote() {

    }

    public Vote(User player, Round round, Boolean isQuit) {
        this.player = player;
        this.round = round;
        this.isQuit = isQuit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Boolean getQuit() {
        return isQuit;
    }

    public void setQuit(Boolean quit) {
        isQuit = quit;
    }
}
