package com.example.course_application.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import com.example.course_application.enums.UserType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    private String loginUser(String username, String password) throws Exception {

        // String studentUsername = "student5";
        // String adminUsername = "admin";
        // String password = "12345";

        JSONObject payload = new JSONObject();

        payload.put("username", username);
        payload.put("password", password);

        MvcResult result = mockMvc.perform(
                post("/api/auth/login")
                        .contentType("application/json")
                        .content(payload.toString()))
                .andExpect(status().isOk()).andReturn();

        String responseStr = result.getResponse().getContentAsString();
        JSONObject jsonResponse = new JSONObject(responseStr);

        assertEquals(true, jsonResponse.getBoolean("success"));
        String token = jsonResponse.getJSONObject("content").getString("token");
        assertNotNull(token);

        return token;
    }

    private String getTokenForUserType(UserType userType) throws Exception {
        switch (userType) {
            case ADMIN:
                return loginUser("admin", "12345");
            case CREATOR:
                return loginUser("creator1", "12345");
            case STUDENT:
                return loginUser("student5", "1234");
            default:
                throw new IllegalArgumentException("Unsupported user type");
        }
    }

    public void testUsers(UserType userType, String endpoint, ResultMatcher status) throws Exception {
        String token = getTokenForUserType(userType);
        mockMvc.perform(get(endpoint).cookie(new MockCookie("token", token))).andDo(result -> {
            log.info("Result: {}", result.getResponse().getContentAsString());
        }).andExpect(status);
    }

    // /api/users => admin, creator, student

    // admin
    // /api/users?page=2
    // /api/users?limit=20
    // /api/users?sortBy=""&sort=1
    // /api/users?page=3&limit=15&sortBy=""&sort=1

    @Test
    public void testGetAllUsers_Admin() throws Exception {

        testUsers(UserType.ADMIN, "/api/users", status().isOk());
    }

    @Test
    public void testGetAllUsers_Student() throws Exception {

        testUsers(UserType.STUDENT, "/api/users", status().isUnauthorized());
    }

    @Test
    public void testGetUserById_Admin() throws Exception {
        String userId = "68025caa77b1f241fb5b38cf";

        mockMvc.perform(get("/api/users/{id}", userId).cookie(new MockCookie("token", jwtToken))).andDo(result -> {
            log.info("Result: {}", result.getResponse().getContentAsString());
        }).andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {

        String userId = "68025caa77b1f241fb5b38cf";

        JSONObject updatedPayload = new JSONObject();

        updatedPayload.put("name", "Rohan");
        updatedPayload.put("phone", 1236548970);

        mockMvc.perform(put("/api/users/{id}", userId).cookie(new MockCookie("token", jwtToken))
                .contentType("application/json").content(updatedPayload.toString())).andDo(result -> {
                    log.info("Updated User Result: {}", result.getResponse().getContentAsString());
                }).andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        String userId = "68025caa77b1f241fb5b38cf";

        mockMvc.perform(delete("/api/users/{id}", userId)
                .cookie(new MockCookie("token", jwtToken)))
                .andDo(result -> log.info("Delete User Result: {}", result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

}
