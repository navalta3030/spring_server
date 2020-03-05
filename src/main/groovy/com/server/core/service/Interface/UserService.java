package com.server.core.service.Interface;

import com.server.core.model.User;

import java.util.List;

public interface UserService {
    public abstract User saveUser(User user);

    public abstract User updateUser(User user);

    public abstract User deleteUser(Long userId);

    public abstract User findByUsername(String username);

    public abstract List<User> findAllUsers();

    public abstract Long numberOfUsers();
}
