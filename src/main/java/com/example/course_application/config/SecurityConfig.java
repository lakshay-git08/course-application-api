package com.example.course_application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.course_application.serviceImpl.AuthServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    AuthFilter authFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Control inside SecurityConfig.filterChain()");
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/**").permitAll().anyRequest().authenticated())
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    // Authentication Manager Bean
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.info("Control inside SecurityConfig.authenticationManager()");
        return config.getAuthenticationManager();
    }

    // Authentication Provider Bean
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        log.info("Control inside SecurityConfig.daoAuthenticationProvider()");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }
}
