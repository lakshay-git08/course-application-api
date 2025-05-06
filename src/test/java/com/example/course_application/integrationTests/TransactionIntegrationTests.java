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
public class TransactionIntegrationTests {

    private static String jwtToken = null;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllTransactions() throws Exception {
        mockMvc.perform(get("/api/transactions").cookie(new MockCookie("token", jwtToken))).andDo(result -> {
            log.info("Result: {}", result.getResponse().getContentAsString());
        }).andExpect(status().isOk());
    }

    @Test
    public void testGetTransactionById() throws Exception {
        String transactionId = "1";

        mockMvc.perform(get("/api/transactions/{id}", transactionId).cookie(new MockCookie("token", jwtToken)))
                .andDo(result -> {
                    log.info("Result: {}", result.getResponse().getContentAsString());
                }).andExpect(status().isOk());
    }

    @Test
    public void testGetAllTransactionsByCourseId() throws Exception {
        String courseId = "1";

        mockMvc.perform(get("/api/transactions/{id}", courseId)
                .cookie(new MockCookie("token", jwtToken)))
                .andDo(result -> {
                    log.info("Result: {}", result.getResponse().getContentAsString());
                }).andExpect(status().isOk());
    }

    @Test
    public void testGetTransactionsByStudentId() throws Exception {
        String studentId = "1";

        mockMvc.perform(get("/api/transactions/{id}", studentId)
                .cookie(new MockCookie("token", jwtToken)))
                .andDo(result -> {
                    log.info("Result: {}", result.getResponse().getContentAsString());
                }).andExpect(status().isOk());
    }
}
