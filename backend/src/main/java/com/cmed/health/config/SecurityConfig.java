package com.cmed.health.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;


    final String[] PERMIT_ALL = {
            "/api/v1/auth/**",
            "/api/v1/users/welcome",
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/api/v1/privileges/**",
            "/api/v1/permission/**",
            "/api/v1/permission",
            "/api/v1/roles/**",
            "/api/v1/roles",
            "api/v1/**"
    };


    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            return config;
        };
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                .csrf(AbstractHttpConfigurer::disable)
                        .cors(AbstractHttpConfigurer::disable)
                        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests.requestMatchers(PERMIT_ALL).permitAll())
                        .authenticationProvider(authenticationProvider())
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
