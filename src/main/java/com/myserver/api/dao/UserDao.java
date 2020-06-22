package com.myserver.api.dao;

import com.myserver.api.model.User;

public interface UserDao {

    User findOne(Integer id);

    User findByLogin(String login);
}
