package com.example.course_application.service;

import java.util.List;
import java.util.Optional;

import com.example.course_application.entity.CombinedFilter;
import com.example.course_application.entity.Course;
import com.example.course_application.input.CourseInput;

public interface CourseService {

    public List<Course> getAllCourses(CombinedFilter combinedFilter);

    public Optional<Course> getCourseById(String id);

    public Course createCourse(CourseInput courseInput);

    public Course updateCourse(String id, CourseInput courseInput);

    public void deleteCourse(String id);

    public List<Course> getAllCoursesByCreatorId(String creatorId, CombinedFilter combinedFilter);

}
