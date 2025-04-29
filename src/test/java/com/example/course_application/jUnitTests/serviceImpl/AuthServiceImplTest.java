package com.example.course_application.jUnitTests.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.course_application.entity.User;
import com.example.course_application.enums.UserRoleType;
import com.example.course_application.enums.UserType;
import com.example.course_application.input.UserInput;
import com.example.course_application.repository.UserRepository;
import com.example.course_application.serviceImpl.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthServiceImpl authServiceImpl;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void testRegister() {

        String username = "user123";
        String password = "12345";
        String encodedPassword = "encoded12345";

        UserInput userInput = UserInput.builder().type(UserType.STUDENT).username(username).password(password).build();
        User mappedUser = User.builder().roleType(UserRoleType.STUDENT.value).username(username).password(password)
                .build();

        Mockito.when(modelMapper.map(userInput, User.class)).thenReturn(mappedUser);
        Mockito.when(bCryptPasswordEncoder.encode(password))
                .thenReturn(encodedPassword);
        Mockito.when(userRepository.save(mappedUser)).thenReturn(mappedUser);

        User result = authServiceImpl.register(userInput);

        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getCreated_at(), "created_at should not be null");
        assertEquals(mappedUser.getUsername(), result.getUsername());

        Mockito.verify(modelMapper).map(userInput, User.class);
        Mockito.verify(userRepository).save(mappedUser);
        Mockito.verify(bCryptPasswordEncoder).encode(password);
    }

    @Test
    public void testLoadUserByUsername() throws Exception {

        String username = "username";
        String password = "password";

        User mockUser = User.builder().username(username).roleType(UserRoleType.STUDENT.value).password(password)
                .build();

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        UserDetails result = authServiceImpl.loadUserByUsername(username);

        assertNotNull(result, "Result should not be null");
        assertEquals(username, result.getUsername(), "username equal");

        Mockito.verify(userRepository).findByUsername(username);

    }

}
