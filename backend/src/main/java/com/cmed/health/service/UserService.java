package com.cmed.health.service;

import com.cmed.health.exception.ResourceNotFoundException;
import com.cmed.health.model.entity.UserAuth;
import com.cmed.health.model.entity.Users;
import com.cmed.health.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = repository.findByUsername(username);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User Not Found with username: " + username);
        } else return new UserAuth(user.get());
    }
}
