package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.Utils.JwtUtils;
import com.example.effectivemobiletest.dto.RegistrationRequestDto;
import com.example.effectivemobiletest.dto.TokenResponseDto;
import com.example.effectivemobiletest.model.User;
import com.example.effectivemobiletest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<?> login(@RequestParam String email,
                                   @RequestParam String password) {

        TokenResponseDto token = userService.authenticateUser(email, password);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }
}
