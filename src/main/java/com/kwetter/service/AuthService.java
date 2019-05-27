package com.kwetter.service;

import com.google.gson.Gson;
import com.kwetter.dto.LoginResult;
import com.kwetter.dto.UserDTO;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;

import javax.ejb.Stateless;
import java.util.UUID;

@Stateless
public class AuthService {
    private final String URL = "http://localhost:8080/KwetterWar/api/users/login/";

    public LoginResult login(String username, String password) {
        GetRequest res = Unirest.get(URL + "/" + username + "/" + password);
        Gson gson = new Gson();
        UserDTO resUser = gson.fromJson(res.getBody().toString(), UserDTO.class);
        return new LoginResult(UUID.fromString(resUser.getId()), resUser.getRoles());
    }
}