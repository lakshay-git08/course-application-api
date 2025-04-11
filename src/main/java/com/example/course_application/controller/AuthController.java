package com.example.course_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.User;
import com.example.course_application.input.UserInput;
import com.example.course_application.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody UserInput userInput) {
        return ApiResponse.buildResponse(authService.register(userInput), HttpStatus.OK);
    }

    // @PostMapping("login")
    // public ResponseEntity<ApiResponse<Optional<User>>> login(@RequestBody
    // Map<String, String> requestBody) {
    // String username = requestBody.get("username");
    // String password = requestBody.get("password");
    // return ApiResponse.buildResponse(authService.login(username, password),
    // HttpStatus.OK);
    // }

}
