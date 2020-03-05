package com.server.core.rest;

import com.server.core.model.AuthRequest;
import com.server.core.model.Role;
import com.server.core.model.User;
import com.server.core.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/registration")
    public ResponseEntity<?> register(@RequestBody User user) {

        // verify if we have user in database
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<Object>(HttpStatus.CONFLICT);
        }


        // default role
        user.setRole(Role.USER);
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/user/login")
    public ResponseEntity<?> getUser(@RequestBody AuthRequest authRequest) {

        // check if in database
        userService.findByUsername(authRequest.getUserName());

        // generate jwt token



        return new ResponseEntity<String>("TOken", HttpStatus.OK);
    }


}
