package com.example.event.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.cors(cors -> {});

        http.authorizeHttpRequests(auth -> auth
                // PUBLIC ENDPOINTS
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/events").permitAll()              // <-- VERY IMPORTANT
                .requestMatchers("/events/**").permitAll()          // <-- LET USERS VIEW EVENTS

                // ADMIN + USER protected endpoints
                .requestMatchers("/admin/**").authenticated()
                .anyRequest().authenticated()
        );

        http.sessionManagement(sess -> sess
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
