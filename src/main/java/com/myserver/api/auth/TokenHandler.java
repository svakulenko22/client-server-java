package com.myserver.api.auth;

import com.myserver.api.model.User;

public interface TokenHandler {

    void parseUserFromToken(String token);

    String createTokenForUser(User user);
}
