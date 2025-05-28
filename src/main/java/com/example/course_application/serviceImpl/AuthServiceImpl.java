package com.example.course_application.serviceImpl;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.User;
import com.example.course_application.enums.UserRoleType;
import com.example.course_application.enums.UserType;
import com.example.course_application.input.UserInput;
import com.example.course_application.repository.UserRepository;
import com.example.course_application.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User register(UserInput userInput) {
        log.info("Control inside AuthServiceImpl.register()");
        User user = modelMapper.map(userInput, User.class);
        if (userInput.getType().equals(UserType.CREATOR)) {
            user.setRoleType(UserRoleType.CREATOR.value);
        } else if (userInput.getType().equals(UserType.ADMIN)) {
            user.setRoleType(UserRoleType.ADMIN.value);
        } else {
            user.setRoleType(UserRoleType.STUDENT.value);
        }
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());
        user.setDeleted(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Control inside AuthServiceImpl.loadUserByUsername()");

        Optional<User> userFromDB = userRepository.findByUsername(username);
        // System.out.println(userFromDB);
        if (userFromDB.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        String userRoleType = UserRoleType.fromRoleType(userFromDB.get().getRoleType()).name();

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRoleType); // it matches the given string at
                                                                                      // the time of preAuthorize
        return new org.springframework.security.core.userdetails.User(userFromDB.get().getUsername(),
                userFromDB.get().getPassword(), Collections.singleton(grantedAuthority));
    }
}
