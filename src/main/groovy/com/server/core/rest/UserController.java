package com.server.core.rest;

import com.server.core.model.AuthRequest;
import com.server.core.model.Role;
import com.server.core.model.User;
import com.server.core.service.Interface.UserService;
import com.server.core.service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody User user) {

        // verify if we have user in database
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<Object>(HttpStatus.CONFLICT);
        }


        // default role
        user.setRole(Role.USER);
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> getUser(@RequestBody AuthRequest authRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            return new ResponseEntity<String>("Invalid account",HttpStatus.CONFLICT);
        }
        User user = userService.findByUsername(authRequest.getUsername());

        String jwt_token = jwtUtil.generateToken(user);
        user.setToken(jwt_token);

        return new ResponseEntity<String>(jwt_token, HttpStatus.OK);
    }


}
