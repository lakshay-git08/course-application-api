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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.course_application.entity.Course;
import com.example.course_application.input.CourseInput;
import com.example.course_application.repository.CourseRepository;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    CourseServiceImpl courseServiceImpl;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void testGetAllCourses() {

        Course course1 = Course.builder().url("url_1").build();
        Course course2 = Course.builder().url("url_2").build();

        List<Course> courseList = Arrays.asList(course1, course2);
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

        Page<Course> mockCourse = new PageImpl<>(courseList, pageable, courseList.size());

        Mockito.when(courseRepository.findAll(pageable)).thenReturn(mockCourse);

        List<Course> result = courseServiceImpl.getAllCourses(pageable.getPageNumber() + 1, pageable.getPageSize(), "",
                1);

        assertNotNull(result, "Course List should not be null");
        assertEquals(courseList.size(), result.size(), "Course list size should be " + courseList.size());
        assertEquals(course1.getUrl(), courseList.get(0).getUrl(), "First Course's url should match ");
        assertEquals(course2.getUrl(), courseList.get(1).getUrl(), "Second Course's url should match ");

        Mockito.verify(courseRepository).findAll(pageable);

    }

    @Test
    public void testGetCourseById() {
        String id = "abcdefghijklmnop";
        String url = "url";

        Course mockCourse = Course.builder().url(url).build();

        try {
            Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(mockCourse));

            Optional<Course> result = courseServiceImpl.getCourseById(id);

            assertNotNull(result, "Expected Course to be not null");
            assertEquals(url, result.get().getUrl(), "");
        } catch (Exception e) {
            fail("Exception thrown during test execution: " + e.getMessage());

        }
    }

    @Test
    public void testCreateCourse() {
        String id = "ljakjghbfhbdc";
        String url = "url";

        CourseInput courseInput = CourseInput.builder().url(url).build();
        Course mappedCourse = Course.builder().url(url).build();
        Course createdCourse = Course.builder().id(id).url(url).build();

        Mockito.when(modelMapper.map(courseInput, Course.class)).thenReturn(mappedCourse);
        Mockito.when(courseRepository.save(Mockito.any(Course.class))).thenReturn(createdCourse);

        Course result = courseServiceImpl.createCourse(courseInput);

        assertNotNull(result, "Result should not be null");
        assertEquals(id, result.getId());

        Mockito.verify(modelMapper).map(courseInput, Course.class);
        Mockito.verify(courseRepository).save(Mockito.any(Course.class));
    }

}
