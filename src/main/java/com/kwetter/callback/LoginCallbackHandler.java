package com.kwetter.callback;

import javax.security.auth.callback.*;

public class LoginCallbackHandler implements CallbackHandler {
    private String username;
    private String password;

    @Override
    public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
        for(Callback c : callbacks) {
            if (c instanceof NameCallback) {
                // prompt the user for a username
                NameCallback nc = (NameCallback)c;
                nc.setName(username);
            } else if (c instanceof PasswordCallback) {
                // prompt the user for sensitive information
                PasswordCallback pc = (PasswordCallback)c;
                pc.setPassword(password.toCharArray());
            } else {
                throw new UnsupportedCallbackException(c, "Unrecognized callback");
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
