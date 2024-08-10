package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.Utils.JwtUtils;
import com.example.effectivemobiletest.dto.RegistrationRequestDto;
import com.example.effectivemobiletest.dto.TokenResponseDto;
import com.example.effectivemobiletest.exception.InvalidCredentialsException;
import com.example.effectivemobiletest.exception.UserNotFoundException;
import com.example.effectivemobiletest.model.User;
import com.example.effectivemobiletest.model.UserRole;
import com.example.effectivemobiletest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegistrationRequestDto registrationRequestDto) {
        User user = User.builder()
                .username(registrationRequestDto.getUsername())
                .email(registrationRequestDto.getEmail())
                .password(passwordEncoder.encode(registrationRequestDto.getPassword()))
                .roles(Set.of(UserRole.ROLE_USER))
                .build();
        userRepository.save(user);
    }

    public TokenResponseDto authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
        String accessToken = jwtUtils.generateToken(user.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());
        return new TokenResponseDto(accessToken,refreshToken);
    }

    @Override
    public TokenResponseDto generateToken(String token) {
        String tokenWithoutBearerStr = token.substring(7);
        String username = jwtUtils.extractUsername(tokenWithoutBearerStr);
        User user = userRepository.findUserByUsername(username).
                orElseThrow(() -> new UserNotFoundException("User with id " + username + " not found"));

        String accessToken = jwtUtils.generateToken(user.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());
        return new TokenResponseDto(accessToken,refreshToken);
    }
}