package com.kwetter.service;

import com.google.gson.Gson;
import com.kwetter.dto.LoginResult;
import com.kwetter.dto.UserDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.ejb.Stateless;
import java.util.UUID;

@Stateless
public class AuthService {
    private final String URL = "http://localhost:8080/kwetter/api/users/credentials/";

    public LoginResult login(String username, String password) throws UnirestException {
        HttpResponse res = Unirest.get(URL + username + "/" + password).asJson();
        Gson gson = new Gson();
        if (res != null) {
            UserDTO resUser = gson.fromJson(res.getBody().toString(), UserDTO.class);
            return new LoginResult(UUID.fromString(resUser.getId()), resUser.getRoles());
        }
        return null;
    }
}