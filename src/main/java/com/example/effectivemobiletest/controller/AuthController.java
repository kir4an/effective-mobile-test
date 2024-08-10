package com.example.effectivemobiletest.controller;

import com.example.effectivemobiletest.dto.LoginRequestDto;
import com.example.effectivemobiletest.dto.RegistrationRequestDto;
import com.example.effectivemobiletest.dto.TokenResponseDto;
import com.example.effectivemobiletest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Создает нового пользователя в системе.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно зарегистрирован.",
                            content = @Content(
                                    schema = @Schema
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные регистрации.",
                            content = @Content
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDto request) {
        userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(
            summary = "Вход пользователя",
            description = "Аутентифицирует пользователя и возвращает токены доступа.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная аутентификация. Возвращает токены доступа.",
                            content = @Content(
                                    schema = @Schema(implementation = TokenResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Неверные учетные данные.",
                            content = @Content
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        TokenResponseDto token = userService.authenticateUser(email, password);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }
    @Operation(
            summary = "Обновление токенов доступа",
            description = "Генерирует новый токен доступа и обновляет существующий токен обновления.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Токены успешно обновлены.",
                            content = @Content(
                                    schema = @Schema(implementation = TokenResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Неверный токен обновления.",
                            content = @Content
                    )
            }
    )
    @GetMapping("token/refresh")
    public ResponseEntity<?> refreshTokens(@RequestHeader("Authorization") String token) {
        TokenResponseDto tokens = userService.generateToken(token);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tokens);
    }
}
