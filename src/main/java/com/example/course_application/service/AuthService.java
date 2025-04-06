package com.example.course_application.service;

import java.util.Optional;

import com.example.course_application.entity.User;
import com.example.course_application.input.UserInput;

public interface AuthService {

    public User register(UserInput userInput);

    public Optional<User> login(String username, String password);
}
