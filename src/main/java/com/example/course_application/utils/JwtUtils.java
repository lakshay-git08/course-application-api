package com.example.course_application.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.course_application.entity.User;
import com.example.course_application.enums.UserRoleType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

        private final String secret = "jnbhr-cbbhr-cbh-bcr-hbrb-ubc-rbbrc-brbc-rbkhh-hhhh-gtt-btgb-htyy-rgfr-frerfr-yt-thyt-tyt-rtr-ff";
        private final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        public String generateToken(User user) {
                return Jwts.builder()
                                .subject(user.getUsername())
                                .claim("role", UserRoleType.fromRoleType(user.getRoleType()))
                                .issuedAt(new Date())
                                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                                .signWith(secretKey).compact();
        }

        public Claims extractToken(String token) {
                return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        }

        public Boolean isTokenValid(String token, UserDetails userDetails) {
                return extractToken(token).getSubject().equals(userDetails.getUsername()) && !isTokenExpired(token);
        }

        public Boolean isTokenExpired(String token) {
                Date expiration = extractToken(token).getExpiration();
                return expiration.before(new Date());
        }

}
