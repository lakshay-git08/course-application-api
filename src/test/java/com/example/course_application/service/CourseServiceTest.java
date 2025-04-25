package com.example.course_application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.course_application.entity.Course;
import com.example.course_application.repository.CourseRepository;
import com.example.course_application.serviceImpl.CourseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseServiceImpl courseServiceImpl;

    @Test
    public void testGetAllCourses() {

        Course course1 = Course.builder().url("url_1").build();
        Course course2 = Course.builder().url("url_2").build();

        List<Course> courseList = Arrays.asList(course1, course2);
        Pageable pageable = PageRequest.of(0, 0, Sort.unsorted());

        Page<Course> mockCourse = new PageImpl<>(courseList, pageable, courseList.size());

        Mockito.when(courseRepository.findAll(pageable)).thenReturn(mockCourse);

        List<Course> result = courseServiceImpl.getAllCourses(pageable.getPageNumber() + 1, pageable.getPageSize(), "",
                1);

        assertNotNull(result, "Course List should not be null.");
        assertEquals(courseList.size(), result.size(), "Course list size should be " + courseList.size());
        assertEquals("url1", courseList.get(0).getUrl(), "First Course's url should match. ");
        assertEquals("url2", courseList.get(1).getUrl(), "Second Course's url should match. ");

        Mockito.verify(courseRepository).findAll(pageable);

    }

    @Test
    public void test

}
