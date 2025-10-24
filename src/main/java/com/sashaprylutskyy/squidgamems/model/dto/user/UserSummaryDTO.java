package com.sashaprylutskyy.squidgamems.model.dto.user;

import com.sashaprylutskyy.squidgamems.model.enums.UserStatus;

public class UserSummaryDTO {

    private Long id;
    private String email;
    private String roleTitle;
    private UserStatus status;

    public UserSummaryDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
