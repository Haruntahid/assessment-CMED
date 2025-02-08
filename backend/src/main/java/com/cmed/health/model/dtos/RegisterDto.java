package com.cmed.health.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    @NotNull(message = "Username cannot be null")
    @Size(min = 3,max = 20, message = "Username must be between 3 to 20 characters")
    private String username;

    @Email(message = "Provide a valid Email address")
    @NotNull(message = "Email cannot be null")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
