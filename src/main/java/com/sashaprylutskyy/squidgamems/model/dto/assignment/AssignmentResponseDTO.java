package com.sashaprylutskyy.squidgamems.model.dto.assignment;

import com.sashaprylutskyy.squidgamems.model.dto.user.UserSummaryDTO;
import com.sashaprylutskyy.squidgamems.model.enums.EnvType;

public class AssignmentResponseDTO {

    private EnvType envType;
    private Long envId;
    private UserSummaryDTO user;
    private UserSummaryDTO assignedBy;

    public AssignmentResponseDTO() {

    }

    public EnvType getEnvType() {
        return envType;
    }

    public void setEnvType(EnvType envType) {
        this.envType = envType;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public UserSummaryDTO getUser() {
        return user;
    }

    public void setUser(UserSummaryDTO user) {
        this.user = user;
    }

    public UserSummaryDTO getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(UserSummaryDTO assignedBy) {
        this.assignedBy = assignedBy;
    }
}
