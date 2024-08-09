package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.RegistrationRequestDto;
import com.example.effectivemobiletest.dto.TokenResponseDto;
import com.example.effectivemobiletest.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void registerUser(RegistrationRequestDto registrationRequestDto);
    TokenResponseDto authenticateUser(String email, String password);

    List<User> findAll();
}
