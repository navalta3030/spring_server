package com.server.core.rest;

import com.server.core.model.request.AuthRequest;
import com.server.core.model.Role;
import com.server.core.model.User;
import com.server.core.service.Interface.UserService;
import com.server.core.service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    /**
     * @param authRequest - username and password are required.
     * @return JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> getUser(@RequestBody AuthRequest authRequest) throws Exception {
        User user = userService.findByUsername(authRequest.getUsername());
        try {
            // Verify if password is match
            if( passwordEncoder.matches(authRequest.getPassword(), user.getPassword()) ) {
                String jwt_token = jwtUtil.generateToken(user);
                user.setToken(jwt_token);

                return new ResponseEntity<String>(jwt_token, HttpStatus.OK);

            } else {
                return new ResponseEntity<String>("Invalid account",HttpStatus.CONFLICT);
            }

        // user.getPassword would be null if user didn't exist
        } catch (NullPointerException e) {
            return new ResponseEntity<String>("Invalid account",HttpStatus.CONFLICT);
        }

    }
}
