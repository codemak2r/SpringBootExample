package com.example.springbootsecurityjpa.controller;

import com.example.springbootsecurityjpa.requests.RegisterRequest;
import com.example.springbootsecurityjpa.requests.SigninRequest;
import com.example.springbootsecurityjpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @DATA: 2020/12/3
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SigninRequest signinRequest){
        return userService.login(signinRequest);
    }
}
