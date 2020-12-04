package com.example.springbootsecurityjpa.services;

import com.example.springbootsecurityjpa.requests.RegisterRequest;
import com.example.springbootsecurityjpa.requests.SigninRequest;
import org.springframework.http.ResponseEntity;

/**
 * @DATA: 2020/12/3
 */
public interface UserService {
    ResponseEntity<?> register(RegisterRequest registerRequest);

    ResponseEntity<?> login(SigninRequest signinRequest);
}
