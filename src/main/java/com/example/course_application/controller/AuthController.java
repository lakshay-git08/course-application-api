package com.example.course_application.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.course_application.config.AppConfig;
import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.User;
import com.example.course_application.input.UserInput;
import com.example.course_application.service.AuthService;
import com.example.course_application.serviceImpl.AuthServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AppConfig appConfig;

    @Autowired
    AuthService authService;

    @Autowired
    AuthServiceImpl authServiceImpl;

    @Autowired
    AuthenticationManager authenticationManager;

    AuthController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody UserInput userInput) {
        return ApiResponse.buildResponse(authService.register(userInput), HttpStatus.OK);
    }

    @PostMapping("login")
    // public ResponseEntity<ApiResponse<Optional<User>>> login(@RequestBody
    // Map<String, String> requestBody) {
    public void login(@RequestBody Map<String, String> requestBody) {
        log.info("Control inside AuthController.login()");

        String username = requestBody.get("username");
        String password = requestBody.get("password");

        // 1. Authenticate User
        var result = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        System.out.println(result);

        // 2. Get User Details
        UserDetails userDetails = authServiceImpl.loadUserByUsername(username);
        System.out.println(userDetails);

        // return ApiResponse.buildResponse(authService.login(username, password),
        // HttpStatus.OK);
    }

}
