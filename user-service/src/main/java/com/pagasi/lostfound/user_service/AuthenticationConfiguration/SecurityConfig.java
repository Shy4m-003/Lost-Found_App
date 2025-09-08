package com.pagasi.lostfound.user_service.AuthenticationConfiguration;

import com.pagasi.lostfound.user_service.customization.CustomUserDetailsServiceImpl;
import com.pagasi.lostfound.user_service.service.Implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @Autowired
    private UserServiceImpl userLogic;
    @Autowired
    private PasswordEncrypt passwordEncrypt;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return (SecurityFilterChain)((HttpSecurity)httpSecurity.csrf().disable()).headers((headers) -> headers.frameOptions().disable()).authorizeHttpRequests((registry) -> {
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)registry.requestMatchers(new String[]{"/api/user-service/v1/sign-in/**"})).permitAll();
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)registry.requestMatchers(new String[]{"/api/user-service/v1/sign-up/**"})).permitAll();
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)registry.requestMatchers(new String[]{"/h2-console/**"})).permitAll();
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)registry.anyRequest()).authenticated();
        }).httpBasic(Customizer.withDefaults()).build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return this.customUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService());
        provider.setPasswordEncoder(this.passwordEncrypt.passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(new AuthenticationProvider[]{this.authenticationProvider()});
    }
}