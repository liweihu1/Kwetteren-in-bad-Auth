package com.kwetter.dto;

import java.util.UUID;

public class JWTTokenDTO {
    private String token;
    private UUID id;

    public JWTTokenDTO(String token, UUID id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
