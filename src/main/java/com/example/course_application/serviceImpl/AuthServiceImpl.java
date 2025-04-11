package com.example.course_application.serviceImpl;

import java.util.Collections;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.User;
import com.example.course_application.enums.UserRoleType;
import com.example.course_application.enums.UserType;
import com.example.course_application.input.UserInput;
import com.example.course_application.repository.UserRepository;
import com.example.course_application.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

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
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDB = userRepository.findByUsername(username).orElse(null);
        if (userFromDB == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        String userRoleType = UserRoleType.fromRoleType(userFromDB.getRoleType()).name();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRoleType);
        return new org.springframework.security.core.userdetails.User(userFromDB.getUsername(),
                userFromDB.getPassword(), Collections.singleton(grantedAuthority));
    }

}
