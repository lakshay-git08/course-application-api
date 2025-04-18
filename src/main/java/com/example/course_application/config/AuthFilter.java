package com.example.course_application.config;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.course_application.entity.ApiResponse;
import com.example.course_application.serviceImpl.AuthServiceImpl;
import com.example.course_application.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthServiceImpl authServiceImpl;

    @Autowired
    AuthChecker authChecker;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("Control inside AuthFilter.doFilterInternal()");
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = jwtUtils.extractToken(token).getSubject();
            UserDetails userDetails = authServiceImpl.loadUserByUsername(username);
            if (jwtUtils.isTokenValid(token, userDetails)) {
                var validToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(validToken);
            }
        }

        String path = request.getRequestURI();
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!authChecker.isLoggedIn()) {
            ApiResponse<?> failResponse = ApiResponse.builder().statusCode(401)
                    .message("You are not logged in. Please login first").build();
            String json = objectMapper.writeValueAsString(failResponse);
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write(json);
            return;
        }
        filterChain.doFilter(request, response);

    }
}