package com.example.course_application.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

    private static String jwtToken = null;

    @Autowired
    MockMvc mockMvc;

    public void loginUser(String userType) throws Exception {

        if (jwtToken == null) {
            // String studentUsername = "student5";
            String adminUsername = "admin";
            String password = "12345";

            JSONObject payload = new JSONObject();

            payload.put("username", adminUsername);
            payload.put("password", password);

            MvcResult result = mockMvc.perform(
                    post("/api/auth/login")
                            .contentType("application/json")
                            .content(payload.toString()))
                    .andExpect(status().isOk()).andReturn();

            String responseStr = result.getResponse().getContentAsString();
            JSONObject jsonResponse = new JSONObject(responseStr);

            assertEquals(true, jsonResponse.getBoolean("success"));
            jwtToken = jsonResponse.getJSONObject("content").getString("token");
            assertNotNull(jwtToken);
        }
    }

    @BeforeEach
    public void setup() throws Exception {
        loginUser(UserType.STUDENT.userType);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users").cookie(new MockCookie("token", jwtToken))).andDo(result -> {
            log.info("Result: {}", result.getResponse().getContentAsString());
        }).andExpect(status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception {
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
