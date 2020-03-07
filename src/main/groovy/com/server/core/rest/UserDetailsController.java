package com.server.core.rest;


import com.server.core.model.User;
import com.server.core.model.request.UserJwtID;
import com.server.core.service.Interface.UserService;
import com.server.core.service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserDetailsController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     *
     * @return
     *
     *     {
     *         "id": 1,
     *         "first_name": "_",
     *         "last_name": "_",
     *         "username": "_",
     *         "password": "$2a$10$YnZA1XR9b4pO/vx2W5XVTuuDdd.6n.njHRGaWVrYwLZh9LWrU9sO.",
     *         "email": "_",
     *         "role": "USER",
     *         "token": null
     *     }
     *
     */
    @PostMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestBody UserJwtID userJwtID) {

        String username = jwtUtil.extractUsername(userJwtID.getJwt());
        return new ResponseEntity<User>(userService.findByUsername(username), HttpStatus.OK);
    }

}
