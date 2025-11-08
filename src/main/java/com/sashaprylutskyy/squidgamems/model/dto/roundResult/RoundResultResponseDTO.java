package com.sashaprylutskyy.squidgamems.model.dto.roundResult;

import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;

public class RoundResultResponseDTO {

    private Long roundId;
    private Long userId;
    private UserStatus status;
    private Long time;

    public RoundResultResponseDTO() {

    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
