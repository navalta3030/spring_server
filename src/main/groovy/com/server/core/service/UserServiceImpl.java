package com.server.core.service;

import com.server.core.dao.UserDAO;
import com.server.core.model.User;
import com.server.core.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public User saveUser(User user) {
        user.setPassword(user.getPassword());

        return userDAO.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userDAO.save(user);
    }

    @Override
    public User deleteUser(Long userId) {
        userDAO.deleteById(userId);
        return null;
    }

    @Override
    public User findByUsername(String username) {

        return userDAO.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public Long numberOfUsers() {
        return userDAO.count();
    }


}
