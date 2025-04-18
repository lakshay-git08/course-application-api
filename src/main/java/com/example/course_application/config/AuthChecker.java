package com.example.course_application.config;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.course_application.enums.UserType;

@Component
public class AuthChecker {

    public boolean isLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }

        // Get all valid roles from the UserRole enum
        Set<String> validRoles = Arrays.stream(UserType.values())
                .map(Enum::name)
                .collect(Collectors.toSet());

        // Check if the user has any of those roles
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(validRoles::contains);
    }
}
