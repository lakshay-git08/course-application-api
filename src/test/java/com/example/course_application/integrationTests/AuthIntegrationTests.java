package com.example.course_application.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class AuthIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    public String generateRandomUsername() {
        return "testUser_" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Test
    public void shouldRegisterStudentSuccessfully() throws Exception {
        String username = generateRandomUsername();
        JSONObject payload = new JSONObject();

        payload.put("name", "Test User");
        payload.put("DOB", "2003-11-08");
        payload.put("phone", "8826854868");
        payload.put("email", username + "@gmail.com");
        payload.put("type", "STUDENT");
        payload.put("username", username);
        payload.put("password", "12345");

        MvcResult result = mockMvc.perform( // mockMvc.perform calls API
                post("/api/auth/register")
                        .contentType("application/json")
                        .content(payload.toString()))
                .andExpect(status().isCreated()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JSONObject responseJSON = new JSONObject(responseBody);

        assertEquals(true, responseJSON.getBoolean("success"));
        assertEquals(username, responseJSON.getJSONObject("content").getString("username"),
                "Username should be equal to: " + username);
    }

}
