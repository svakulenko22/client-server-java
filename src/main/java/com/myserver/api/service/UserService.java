package com.myserver.api.service;

import com.myserver.api.model.User;

public interface UserService {

    User findOne(Integer id);

    String login(String login, String password);
}
