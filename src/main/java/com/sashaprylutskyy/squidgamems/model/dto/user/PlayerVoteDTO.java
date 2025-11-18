package com.sashaprylutskyy.squidgamems.model.dto.user;

public class PlayerVoteDTO {
    private boolean canVote;
    private boolean hasVoted;

    public PlayerVoteDTO(boolean canVote, boolean hasVoted) {
        this.canVote = canVote;
        this.hasVoted = hasVoted;
    }

    public boolean isCanVote() {
        return canVote;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }
}