package com.example.course_application.integrationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class EnrollmentIntegrationTests {

    private static String jwtToken = null;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllEnrollments() throws Exception {
        mockMvc.perform(get("/api/enrollments").cookie(new MockCookie("token", jwtToken))).andDo(result -> {
            log.info("Result: {}", result.getResponse().getContentAsString());
        }).andExpect(status().isOk());
    }

    @Test
    public void testGetEnrollmentById() throws Exception {
        String enrollmentId = "1";

        mockMvc.perform(get("/api/enrollments/{id}", enrollmentId).cookie(new MockCookie("token", jwtToken)))
                .andDo(result -> {
                    log.info("Result: {}", result.getResponse().getContentAsString());
                }).andExpect(status().isOk());
    }

}
