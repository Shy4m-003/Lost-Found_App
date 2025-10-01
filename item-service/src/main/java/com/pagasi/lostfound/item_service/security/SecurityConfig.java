package com.pagasi.lostfound.item_service.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // new lambda style
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/items").hasRole("USER")
                        .requestMatchers("/api/items/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(); // basic auth

        return http.build();
    }

}
