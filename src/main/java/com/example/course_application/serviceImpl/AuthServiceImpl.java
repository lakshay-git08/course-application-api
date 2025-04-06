package com.example.course_application.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.User;
import com.example.course_application.enums.UserRoleType;
import com.example.course_application.enums.UserType;
import com.example.course_application.input.UserInput;
import com.example.course_application.repository.UserRepository;
import com.example.course_application.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public User register(UserInput userInput) {
        User user = modelMapper.map(userInput, User.class);
        if (userInput.getType().equals(UserType.CREATOR)) {
            user.setRoleType(UserRoleType.CREATOR.roleType);
        } else {
            user.setRoleType(UserRoleType.STUDENT.roleType);
        }
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());
        User newUser = userRepository.save(user);
        return newUser;
    };

    public Optional<User> login(String username, String password) {
        Optional<User> userFromDB = userRepository.findUserByUsernameAndPassword(username, password);
        return userFromDB;
    }

}
