package com.easylocate.controller;

import com.easylocate.model.User;
import com.easylocate.service.JWTService;
import com.easylocate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@SuppressWarnings("unused")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    @Lazy
    private AuthenticationManager auth;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findUserByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication;
        try {
            authentication = auth.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Wrong username / password !");
        }

        String jwt = null;
        if (authentication.isAuthenticated()) {
            final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
            jwt = jwtService.generateJWToken(userDetails.getUsername());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }
}
