package com.cmed.health.service;

import com.cmed.health.exception.AlreadyExistException;
import com.cmed.health.exception.UserNotFoundException;
import com.cmed.health.model.dtos.LoginDto;
import com.cmed.health.model.dtos.RegisterDto;
import com.cmed.health.model.entity.Users;
import com.cmed.health.model.mapper.UsersMapper;
import com.cmed.health.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServices {


    private final AuthenticationManager authManager;
    private final UserRepository repository;
    private final UsersMapper mapper;
    private final JWTService jwtService;


    public void register(RegisterDto dto) {
        Users user = mapper.map(dto);
        if (repository.findByUsername(user.getUsername()).isPresent() || repository.findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyExistException("User Already Exists With This Email or Username");
        }
        repository.save(user);
    }

    public String verify(LoginDto dto) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(dto.getUsername());
        } else {
            throw new UserNotFoundException("Invalid Username or Password");
        }
    }

    ;

}
