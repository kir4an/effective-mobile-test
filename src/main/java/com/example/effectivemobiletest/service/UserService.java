package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.RegistrationRequestDto;
import com.example.effectivemobiletest.dto.TokenResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void registerUser(RegistrationRequestDto registrationRequestDto);

    TokenResponseDto authenticateUser(String email, String password);

    TokenResponseDto generateToken(String token);
}
