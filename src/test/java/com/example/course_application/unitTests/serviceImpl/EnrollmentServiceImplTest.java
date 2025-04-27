package com.example.course_application.unitTests.serviceImpl;

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

import com.example.course_application.entity.Enrollment;
import com.example.course_application.repository.EnrollmentRepository;
import com.example.course_application.serviceImpl.EnrollmentServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceImplTest {

    @Mock
    EnrollmentRepository enrollmentRepository;

    @InjectMocks
    EnrollmentServiceImpl enrollmentServiceImpl;

    @Test
    public void testGetAllEnrollments() {
        Enrollment enrollment1 = Enrollment.builder().course_id("enrollment_1").build();
        Enrollment enrollment2 = Enrollment.builder().course_id("enrollment_2").build();

        List<Enrollment> enrollmentList = Arrays.asList(enrollment1, enrollment2);
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

        Page<Enrollment> mockEnrollment = new PageImpl<>(enrollmentList, pageable, enrollmentList.size());

        Mockito.when(enrollmentRepository.findAll(pageable)).thenReturn(mockEnrollment);

        List<Enrollment> result = enrollmentServiceImpl.getAllEnrollments(pageable.getPageNumber() + 1,
                pageable.getPageSize(), "",
                1);

        assertNotNull(result, "Enrollment List should not be null");
        assertEquals(enrollmentList.size(), result.size(), "Enrollment list size should be " + enrollmentList.size());
        assertEquals(enrollment1.getCourse_id(), enrollmentList.get(0).getCourse_id(),
                "First Enrollment's url should match ");
        assertEquals(enrollment2.getCourse_id(), enrollmentList.get(1).getCourse_id(),
                "Second Enrollment's url should match ");

        Mockito.verify(enrollmentRepository).findAll(pageable);
    }

    @Test
    public void testGetEnrollmentById() {
        String id = "abcdefghijklmnop";
        String courseId = "courseId";

        Enrollment mockEnrollment = Enrollment.builder().course_id(courseId).build();

        try {
            Mockito.when(enrollmentRepository.findById(id)).thenReturn(Optional.of(mockEnrollment));

            Optional<Enrollment> result = enrollmentServiceImpl.getEnrollmentById(id);

            assertNotNull(result, "Expected Enrollment to be not null");
            assertEquals(courseId, result.get().getCourse_id(), "");
        } catch (Exception e) {
            fail("Exception thrown during test execution: " + e.getMessage());

        }
    }

}
