package com.kwetter.dto;

import java.util.List;
import java.util.UUID;

public class LoginResult {
    private List<String> roles;
    private UUID userId;

    public LoginResult(UUID userId, List<String> roles) {
        this.userId = userId;
        this.roles = roles;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
