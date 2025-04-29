package com.example.course_application.service;

import java.util.List;

import com.example.course_application.entity.BaseFilter;
import com.example.course_application.entity.User;
import com.example.course_application.input.UserInput;

public interface UserService {

    public List<User> getAllUsers(BaseFilter combinedFilter);

    public User getUserById(String id);

    public User getUserByUsername(String username);

    public User updateUser(UserInput userInput, String id, User userFromDB);

    public void deleteUser(String id);
}
