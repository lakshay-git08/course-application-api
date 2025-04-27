package com.example.course_application.integrationTests;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.course_application.controller.UserController;
import com.example.course_application.entity.User;
import com.example.course_application.repository.UserRepository;
import com.example.course_application.serviceImpl.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    // @Autowired
    // UserServiceImpl userServiceImpl;

    @Test
    public void testGetAllUsers() throws Exception {
        // User user1 = User.builder().email("abc@gmail.com").build();
        // User user2 = User.builder().email("xyz@gmail.com").build();

        // List<User> userList = Arrays.asList(user1, user2);
        // Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

        // Mockito.when(userServiceImpl.getAllUsers(pageable.getPageNumber() + 1,
        // pageable.getPageSize(), "", 1))
        // .thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")).andExpect(MockMvcResultMatchers.status().isOk());

        // Mockito.verify(userServiceImpl).getAllUsers(pageable.getPageNumber() + 1,
        // pageable.getPageSize(), "", 1);
    }

}
