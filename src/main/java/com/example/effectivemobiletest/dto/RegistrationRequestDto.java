package com.example.effectivemobiletest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegistrationRequestDto {
    private String username;
    private String password;
    private String email;
}
