package com.server.core.rest;


import com.server.core.model.User;
import com.server.core.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserDetailsController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {

        return new ResponseEntity<List<User>>(userService.findAllUsers(), HttpStatus.OK);
    }

}
