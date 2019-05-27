package com.kwetter.service;

import com.kwetter.dto.LoginResult;

import javax.ejb.Stateless;

@Stateless
public class AuthService {

    public LoginResult login(String username, String password) {
        //TODO connect with user service to login
        return null;
    }
}