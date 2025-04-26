package com.example.course_application.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.course_application.entity.User;
import com.example.course_application.input.UserInput;
import com.example.course_application.repository.UserRepository;
import com.example.course_application.serviceImpl.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Test
    public void testGetAllUsers() {
        User user1 = User.builder().email("abc@gmail.com").build();
        User user2 = User.builder().email("xyz@gmail.com").build();

        List<User> userList = Arrays.asList(user1, user2);
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

        Page<User> mockPage = new PageImpl<>(userList, pageable, userList.size());

        Mockito.when(userRepository.findAll(pageable)).thenReturn(mockPage);

        List<User> result = userServiceImpl.getAllUsers(
                pageable.getPageNumber() + 1,
                pageable.getPageSize(),
                "", 1);

        assertNotNull(result, "User list should not be null");
        assertEquals(userList.size(), result.size(), "User list size should be " + userList.size());
        assertEquals("abc@gmail.com", result.get(0).getEmail(), "First user's email should match");
        assertEquals("xyz@gmail.com", result.get(1).getEmail(), "Second user's email should match");

        Mockito.verify(userRepository).findAll(pageable);
    }

    @Test
    public void testGetUserById() {
        String email = "abc@gmail.com";
        String id = "68025caa77b1f241fb5b38cg";
        User mockUser = User.builder().email(email).build();

        log.info("Setting up mock repository to return user with email: {}", email);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        try {
            log.info("Calling userServiceImpl.getUserById() with id: {}", id);
            User result = userServiceImpl.getUserById(id);

            assertNotNull(result, "Expected user to be not null");
            assertEquals(email, result.getEmail(), "Email should match the mock user");

            log.info("User email matched successfully: {}", result.getEmail());
            Mockito.verify(userRepository).findById(id);
        } catch (Exception e) {
            log.error("Test failed due to exception: ", e);
            fail("Exception thrown during test execution: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateUser() {

        String id = "abcdefghijklmnop";
        String existingEmail = "abc@gmail.com";
        String updatedEmail = "xyz@gmail.com";

        User existingUser = User.builder().id(id).email(existingEmail).build();
        UserInput userInput = new UserInput();
        userInput.setEmail(updatedEmail);
        User updatedUser = User.builder().id(id).email(updatedEmail).build();

        User anyUser = Mockito.any(User.class);
        // Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(anyUser)).thenReturn(updatedUser);

        User result = userServiceImpl.updateUser(userInput, id, existingUser);

        assertNotNull(result);
        assertEquals(updatedEmail, result.getEmail());

        // Mockito.verify(userRepository).findById(id);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void testDeleteUser() {

        String id = "abcdefghijklmnop";

        User mockUser = User.builder().id(id).email("abc@gmail.com").build();

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        userServiceImpl.deleteUser(id);

        Mockito.verify(userRepository).findById(id);
        Mockito.verify(userRepository).deleteById(id);
    }

}
