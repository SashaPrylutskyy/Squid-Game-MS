package com.sashaprylutskyy.squidgamems.model.dto.assignment;

import jakarta.validation.constraints.NotNull;

public class AssignmentRequestDTO {

    @NotNull
    private Long envId;

    @NotNull
    private Long userId;

    public AssignmentRequestDTO() {

    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
