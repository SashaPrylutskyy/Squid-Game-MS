package com.sashaprylutskyy.squidgamems.model.dto.user;

import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;

public class PlayerStatusResponseDTO {
    private PlayerCompetitionDTO competition;
    private UserStatus statusInCompetition;
    private PlayerRoundDTO currentRound;
    private PlayerVoteDTO activeVote;

    // Статичний метод для швидкого створення відповіді "не в грі"
    public static PlayerStatusResponseDTO notInGame() {
        PlayerStatusResponseDTO dto = new PlayerStatusResponseDTO();
        dto.setStatusInCompetition(null); // Або UserStatus.NOT_IN_GAME, якщо додасте такий
        return dto;
    }

    public PlayerStatusResponseDTO() {
    }

    // Getters and Setters
    public PlayerCompetitionDTO getCompetition() {
        return competition;
    }

    public void setCompetition(PlayerCompetitionDTO competition) {
        this.competition = competition;
    }

    public UserStatus getStatusInCompetition() {
        return statusInCompetition;
    }

    public void setStatusInCompetition(UserStatus statusInCompetition) {
        this.statusInCompetition = statusInCompetition;
    }

    public PlayerRoundDTO getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(PlayerRoundDTO currentRound) {
        this.currentRound = currentRound;
    }

    public PlayerVoteDTO getActiveVote() {
        return activeVote;
    }

    public void setActiveVote(PlayerVoteDTO activeVote) {
        this.activeVote = activeVote;
    }
}