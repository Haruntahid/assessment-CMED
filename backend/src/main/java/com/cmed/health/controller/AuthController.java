package com.cmed.health.controller;

import com.cmed.health.model.dtos.LoginDto;
import com.cmed.health.model.dtos.RegisterDto;
import com.cmed.health.response.ApiResponse;
import com.cmed.health.service.AuthServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServices service;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDto dto) {
       service.register(dto);
       return ApiResponse.success(HttpStatus.CREATED,"Registration successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDto dto) {
        service.verify(dto);
        return ApiResponse.successWithToken(HttpStatus.OK, "Login successful", service.verify(dto));
    }

}
