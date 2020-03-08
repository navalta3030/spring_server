package com.server.core.service;

import com.server.core.model.User;
import com.server.core.repository.UserRepository;
import com.server.core.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        // Encrypt the password user bcrypt
        String p = encoder.encode(user.getPassword());
        user.setPassword(p);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {return userRepository.save(user); }

    @Override
    public User deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return null;
    }

    @Override
    public Long numberOfUsers() {
        return userRepository.count();
    }

    @Override
    public List<User> saveAll(ArrayList<User> users) {
        return userRepository.saveAll(users);
    }


}
