package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.dto.LoginRequestDto;
import com.example.effectivemobiletest.dto.RegistrationRequestDto;
import com.example.effectivemobiletest.dto.TokenResponseDto;
import com.example.effectivemobiletest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDto request) {
        userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        TokenResponseDto token = userService.authenticateUser(email, password);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }
}
