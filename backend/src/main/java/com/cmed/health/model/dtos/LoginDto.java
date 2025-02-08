package com.cmed.health.model.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String username;
    private String password;
}
