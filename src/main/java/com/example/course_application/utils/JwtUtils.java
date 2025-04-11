package com.example.course_application.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.course_application.enums.UserRoleType;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final String secret = "jnbhr-cbbhr-cbh-bcr-hbrb-ubc-rbbrc-brbc-rbkhh-hhhh-gtt-btgb-htyy-rgfr-frerfr-yt-thyt-tyt-rtr-ff";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().subject(userDetails.getUsername()).claim("role", userDetails.getAuthorities().stream()
                .findFirst().map(GrantedAuthority::getAuthority).orElse(UserRoleType.STUDENT.name()))
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey)
                .compact();
    }
}
