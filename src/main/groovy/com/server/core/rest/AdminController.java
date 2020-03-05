package com.server.core.rest;

import com.server.core.model.User;
import com.server.core.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/admin")
public class AdminController {
    @PutMapping("/user-update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User existUser = userService.findByUsername(user.getUsername());

        // if user not in database
        if (existUser != null && !existUser.getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }


        return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/user-delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getId());

        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping("/user-all")
    public ResponseEntity<?> findAllUsers() {
        return new ResponseEntity<List<User>>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user-count")
    public ResponseEntity<?> findUsersCount() {
        return new ResponseEntity<Long>(userService.numberOfUsers(), HttpStatus.OK);
    }

    @Autowired
    private UserService userService;
}
