package com.example.course_application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.User;
import com.example.course_application.input.UserInput;
import com.example.course_application.service.AuthService;
import com.example.course_application.service.UserService;
import com.example.course_application.serviceImpl.AuthServiceImpl;
import com.example.course_application.utils.JwtUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    AuthServiceImpl authServiceImpl;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody UserInput userInput) {
        log.info("Control inside AuthContrxoller.register()");
        return ApiResponse.buildResponse(authService.register(userInput), HttpStatus.CREATED);
    }

    @PostMapping("login")
    // public ResponseEntity<ApiResponse<Optional<User>>> login(@RequestBody
    // Map<String, String> requestBody) {
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody Map<String, String> requestBody,
            HttpServletResponse response) {

        log.info("Control inside AuthController.login()");

        String username = requestBody.get("username");
        String password = requestBody.get("password");

        // 1. Authenticate User
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // 2. Get User Details
        User user = userService.getUserByUsername(username);

        // 3. Generate JWT Token
        String token = jwtUtils.generateToken(user);

        // 4. Add token to Cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // cookie.setMaxAge(30000);
        response.addCookie(cookie);

        // 5. Create Response
        Map<String, String> result = new HashMap<>();
        result.put("token", token);

        return ApiResponse.buildResponse(result, HttpStatus.OK);

    }
}
