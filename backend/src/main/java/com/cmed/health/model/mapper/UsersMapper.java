package com.cmed.health.model.mapper;

import com.cmed.health.model.dtos.RegisterDto;
import com.cmed.health.model.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UsersMapper {

    private final PasswordEncoder passwordEncoder;

    public Users map(RegisterDto dto) {
        Users entity = new Users();
        entity.setCreatedAt(new Date());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        return entity;
    }

}
