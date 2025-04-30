package com.example.course_application.integrationTests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Disabled
    public void testGetAllUsers() throws Exception {

        mockMvc.perform(get("/api/users")).andDo(result -> {
            log.info("Result: {}", result.getResponse().getContentAsString());
        }).andExpect(status().isOk());

    }
}
