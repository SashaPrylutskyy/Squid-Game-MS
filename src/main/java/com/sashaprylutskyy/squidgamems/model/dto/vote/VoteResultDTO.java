package com.sashaprylutskyy.squidgamems.model.dto.vote;

public class VoteResultDTO {

    private int continueGame;
    private int quitGame;
    private int remaining;

    public VoteResultDTO() {

    }

    public VoteResultDTO(int continueGame, int quitGame, int remaining) {
        this.continueGame = continueGame;
        this.quitGame = quitGame;
        this.remaining = remaining;
    }

    public int getContinueGame() {
        return continueGame;
    }

    public int getQuitGame() {
        return quitGame;
    }

    public int getRemaining() {
        return remaining;
    }
}
