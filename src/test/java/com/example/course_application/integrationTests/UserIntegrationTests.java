package com.example.course_application.integrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.course_application.entity.User;
import com.example.course_application.enums.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public JSONObject makeJsonObject(MvcResult result) throws Exception {

        String resultString = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(resultString);
        return jsonObject;
    }

    public String generateRandomUsername() {
        return "testUser_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public User createUserInDB(UserType userType) throws Exception {
        String username = generateRandomUsername();
        JSONObject payload = new JSONObject();

        payload.put("name", "Test User");
        payload.put("DOB", "2003-11-08");
        payload.put("phone", "8826854868");
        payload.put("email", username + "@gmail.com");
        payload.put("type", userType.name());
        payload.put("username", username);
        payload.put("password", "12345");

        MvcResult result = mockMvc.perform( // mockMvc.perform calls API
                post("/api/auth/register")
                        .contentType("application/json")
                        .content(payload.toString()))
                .andExpect(status().isCreated()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        JSONObject responseJSON = new JSONObject(responseBody);

        JSONObject content = responseJSON.optJSONObject("content");
        if (content != null) {
            User user = objectMapper.readValue(content.toString(), User.class);
            return user;
        }
        return null;
    }

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

        JSONObject jsonResponse = makeJsonObject(result);

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
                return loginUser("creator_1", "1234");
            case STUDENT:
                return loginUser("student5", "1234");
            default:
                throw new IllegalArgumentException("Unsupported user type");
        }
    }

    public ResultActions testGetUsers(UserType userType, String endpoint,
            ResultMatcher status) throws Exception {
        String token = getTokenForUserType(userType);
        return mockMvc.perform(get(endpoint).cookie(new MockCookie("token",
                token))).andDo(result -> {
                    log.info("Result: {}", result.getResponse().getContentAsString());
                }).andExpect(status);
    }

    @Test
    public void testGetAllUsers_Admin() throws Exception {

        // endpoint: /api/users
        // cookie: token
        // expected status: OK

        testGetUsers(UserType.ADMIN, "/api/users", status().isOk());
    }

    // /api/users?page=2
    @Test
    public void testGetAllUsers_onSpecificPage_Admin() throws Exception {

        // endpoint: /api/users?page=100
        // cookie: token
        // expected status: OK

        int page = 1;
        int defaultPageLimit = 20;

        MvcResult result = testGetUsers(UserType.ADMIN, "/api/users?page=" + page,
                status().isOk()).andReturn();
        JSONObject jsonResult = makeJsonObject(result);

        Boolean success = jsonResult.getBoolean("success");
        JSONArray content = jsonResult.getJSONArray("content");

        assertEquals(true, success);
        assertTrue(content.length() <= defaultPageLimit);

    }

    // /api/users?limit=20
    @Test
    public void testGetAllUsers_forSpecificLimit_Admin() throws Exception {

        // endpoint: /api/users?limit=20
        // cookie: token
        // expected status: OK

        int limit = 20;

        MvcResult result = testGetUsers(UserType.ADMIN, "/api/users?limit=" + limit,
                status().isOk()).andReturn();
        JSONObject jsonResult = makeJsonObject(result);

        Boolean success = jsonResult.getBoolean("success");
        JSONArray content = jsonResult.optJSONArray("content");

        assertEquals(true, success);
        assertNotNull(content);
        assertTrue(content.length() <= limit);

    }

    public boolean checkSorting(String sortBy, String sortDirection, JSONArray contentArray) throws Exception {

        List<String> nameList = new ArrayList<>();

        for (int i = 0; i < contentArray.length(); i++) {
            String sortByStr = contentArray.getJSONObject(i).getString(sortBy);
            nameList.add(sortByStr);
        }

        List<String> sortedList = new ArrayList<>(nameList);

        if (sortDirection.equals("1")) {
            Collections.sort(sortedList);
        } else if (sortDirection.equals("-1")) {
            Collections.sort(sortedList, Collections.reverseOrder());
        } else {
            return false;
        }

        return nameList.equals(sortedList);
    }

    // /api/users?sortBy=""&sort=1
    @Test
    public void testGetAllUsers_afterSpecificSort_Admin() throws Exception {

        // endpoint: /api/users?sortBy="name"&sortDirection=1
        // cookie: token
        // expected status: OK

        String sortBy = "name";
        String sortDir = "1";

        MvcResult result = testGetUsers(UserType.ADMIN, "/api/users?sortBy=" + sortBy + "&sortDirection=" + sortDir,
                status().isOk()).andReturn();
        JSONObject jsonResult = makeJsonObject(result);

        Boolean success = jsonResult.getBoolean("success");
        JSONArray content = jsonResult.getJSONArray("content");

        assertEquals(true, success);
        assertEquals(true, checkSorting(sortBy, sortDir, content));
    }

    // /api/users?page=3&limit=15&sortBy=""&sort=1
    @Test
    public void testGetAllUsers_onSortingPagination_Admin() throws Exception {

        // endpoint: /api/users
        // cookie: token
        // expected status: OK

        String page = "1";
        String limit = "10";
        String sortBy = "name";
        String sortDir = "1";

        MvcResult result = testGetUsers(UserType.ADMIN,
                "/api/users?page=" + page + "&limit=" + limit + "&sortBy=" + sortBy + "&sortDirection=" + sortDir,
                status().isOk()).andReturn();
        JSONObject jsonResult = makeJsonObject(result);

        Boolean success = jsonResult.getBoolean("success");
        JSONArray content = jsonResult.getJSONArray("content");

        assertEquals(true, success);
        assertEquals(10, content.length());
        assertEquals(true, checkSorting(sortBy, sortDir, content));
    }

    @Test
    public void testGetAllUsers_Student() throws Exception {

        // endpoint: /api/users
        // cookie: token
        // expected status: Unauthorized

        testGetUsers(UserType.STUDENT, "/api/users", status().isUnauthorized());
    }

    @Test
    public void testGetAllUsers_Creator() throws Exception {

        // endpoint: /api/users
        // cookie: token
        // expected status: Unauthorized

        testGetUsers(UserType.CREATOR, "/api/users", status().isUnauthorized());
    }

    @Test
    public void testGetUserById_Admin() throws Exception {

        // endpoint: /api/users
        // cookie: token
        // expected status: OK

        String id = "68025cb977b1f241fb5b38d0";
        testGetUsers(UserType.ADMIN, "/api/users/" + id, status().isOk());
    }

    @Test
    public void testGetUserById_Student() throws Exception {

        // endpoint: /api/users/{id}
        // cookie: token
        // expected status: Unauthorized

        String id = "68025caa77b1f241fb5b38cf";
        testGetUsers(UserType.STUDENT, "/api/users/" + id, status().isUnauthorized());
    }

    @Test
    public void testGetUserById_Creator() throws Exception {

        // endpoint: /api/users/{id}
        // cookie: token
        // expected status: Unauthorized

        String id = "68186f9af3ed5e164b44430b";
        testGetUsers(UserType.CREATOR, "/api/users/" + id, status().isUnauthorized());
    }

    public void testUpdateUsers(JSONObject updatedPayload, UserType userType,
            String endpoint,
            ResultMatcher status)
            throws Exception {
        String token = getTokenForUserType(userType);

        mockMvc.perform(put(endpoint).cookie(new MockCookie("token", token))
                .contentType("application/json").content(updatedPayload.toString())).andDo(result -> {
                    log.info("Updated User Result: {}",
                            result.getResponse().getContentAsString());
                }).andExpect(status);
    }

    @Test
    public void testUpdateUser_Admin() throws Exception {

        // endpoint: /api/users/{id}
        // cookie: token
        // expected status: Ok

        String userId = "68123f1ba6f88f6df6e5fb80";

        JSONObject updatedPayload = new JSONObject();

        updatedPayload.put("name", "Rohan");
        updatedPayload.put("phone", 1236548970);

        testUpdateUsers(updatedPayload, UserType.ADMIN, "/api/users/" + userId,
                status().isOk());
    }

    @Test
    public void testUpdateUser_Student() throws Exception {

        // endpoint: /api/users/{id}
        // cookie: token
        // expected status: Unauthorized

        String userId = "68025caa77b1f241fb5b38cf";

        JSONObject updatedPayload = new JSONObject();

        updatedPayload.put("name", "Rohan");
        updatedPayload.put("phone", 1236548970);

        testUpdateUsers(updatedPayload, UserType.STUDENT, "/api/users/" + userId,
                status().isUnauthorized());
    }

    @Test
    public void testUpdateUser_Creator() throws Exception {

        // endpoint: /api/users/{id}
        // cookie: token
        // expected status: Unauthorized

        String userId = "68025caa77b1f241fb5b38cf";

        JSONObject updatedPayload = new JSONObject();

        updatedPayload.put("name", "Rohan");
        updatedPayload.put("phone", 1236548970);

        testUpdateUsers(updatedPayload, UserType.CREATOR, "/api/users/" + userId,
                status().isUnauthorized());
    }

    public void deleteUsers(UserType userType, String endpoint, ResultMatcher status) throws Exception {

        String token = getTokenForUserType(userType);

        mockMvc.perform(delete(endpoint)
                .cookie(new MockCookie("token", token)))
                .andDo(result -> log.info("Delete User Result: {}",
                        result.getResponse().getContentAsString()))
                .andExpect(status);

    }

    @Test
    public void testDeleteUser_Admin() throws Exception {

        // endpoint: /api/users/{id}
        // cookie: token
        // expected status: OK

        User user = createUserInDB(UserType.ADMIN);

        assertNotNull(user, "User should not be null");

        String userId = user.getId();
        deleteUsers(UserType.ADMIN, "/api/users/" + userId, status().isOk());
    }

    @Test
    public void testDeleteUser_Student() throws Exception {

        // endpoint: /api/users/{id}
        // cookie: token
        // expected status: Unauthorized

        String userId = "68025caa77b1f241fb5b38cf";
        deleteUsers(UserType.STUDENT, "/api/users/" + userId, status().isUnauthorized());

    }

    @Test
    public void testDeleteUser_Creator() throws Exception {

        // endpoint: /api/users/{id}
        // cookie: token
        // expected status: Unauthorized

        String userId = "68186f9af3ed5e164b44430b";
        deleteUsers(UserType.CREATOR, "/api/users/" + userId, status().isUnauthorized());
    }

}
