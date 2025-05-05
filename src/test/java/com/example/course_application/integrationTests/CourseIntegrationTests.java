package com.example.course_application.integrationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
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
public class CourseIntegrationTests {

    private static String jwtToken = null;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllCourses() throws Exception {
        mockMvc.perform(get("/api/courses").cookie(new MockCookie("token", jwtToken))).andDo(result -> {
            log.info("Result: {}", result.getResponse().getContentAsString());
        }).andExpect(status().isOk());
    }

    @Test
    public void testGetCourseById() throws Exception {
        String courseId = "67f170b15185eb4f668a568d";

        mockMvc.perform(get("/api/courses/{id}", courseId).cookie(new MockCookie("token", jwtToken))).andDo(result -> {
            log.info("Result: {}", result.getResponse().getContentAsString());
        }).andExpect(status().isOk());
    }

    @Test
    public void testUpdateCourse() throws Exception {

        String courseId = "67f171445185eb4f668a568e";

        JSONObject updatedPayload = new JSONObject();

        updatedPayload.put("name", "AWS Cloud Computing");
        updatedPayload.put("price", 500);

        mockMvc.perform(put("/api/courses/{id}", courseId).cookie(new MockCookie("token", jwtToken))
                .contentType("application/json").content(updatedPayload.toString())).andDo(result -> {
                    log.info("Updated Course Result: {}", result.getResponse().getContentAsString());
                }).andExpect(status().isOk());
    }

    @Test
    public void testDeleteCourse() throws Exception {
        String courseId = "67f171445185eb4f668a568e";

        mockMvc.perform(delete("/api/courses/{id}", courseId)
                .cookie(new MockCookie("token", jwtToken)))
                .andDo(result -> log.info("Delete Course Result: {}", result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

}
