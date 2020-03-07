package com.server.core.service;

import com.server.core.model.User;
import com.server.core.repository.UserDAO;
import com.server.core.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User saveUser(User user) {
        // Encrypt the password user bcrypt
        String p = encoder.encode(user.getPassword());
        user.setPassword(p);

        return userDAO.save(user);
    }

    @Override
    public User updateUser(User user) {return userDAO.save(user); }

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
