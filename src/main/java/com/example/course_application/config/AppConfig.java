package com.example.course_application.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AppConfig {

    @Bean
    ModelMapper modelMapper() {
        log.info("Control inside AppConfig.modelMapper()");
        return new ModelMapper();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.info("Control inside AppConfig.bCryptPasswordEncoder()");
        return new BCryptPasswordEncoder();
    }

}
