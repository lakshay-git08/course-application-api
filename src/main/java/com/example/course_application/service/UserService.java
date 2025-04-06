package com.example.course_application.service;

import java.util.List;
import java.util.Optional;

import com.example.course_application.entity.User;
import com.example.course_application.input.UserInput;

public interface UserService {

    public List<User> getAllUsers(int page, int limit, String sortBy, int sortDirection);

    public Optional<User> getUserById(String id);

    public User updateUser(UserInput userInput, String id, Optional<User> userFromDB);

    public void deleteUser(String id);
}
