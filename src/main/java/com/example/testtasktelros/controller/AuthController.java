package com.example.testtasktelros.controller;

import com.example.testtasktelros.dto.JWTAuthResponse;
import com.example.testtasktelros.dto.UserDTO;
import com.example.testtasktelros.exception.APIException;
import com.example.testtasktelros.model.User;
import com.example.testtasktelros.repository.RoleRepository;
import com.example.testtasktelros.repository.UserRepository;
import com.example.testtasktelros.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@ControllerAdvice
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody UserDTO loginDto) throws APIException {
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }
}
