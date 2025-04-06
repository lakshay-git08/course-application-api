package com.example.course_application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.Course;
import com.example.course_application.input.CourseInput;
import com.example.course_application.service.CourseService;
import com.example.course_application.utils.ErrorMessageConstants;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "", required = false) String sortBy,
            @RequestParam(defaultValue = "1") int sortDirection) {

        if (limit < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }
        if (sortDirection != -1 && sortDirection != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }
        List<Course> result = courseService.getAllCourses(page, limit, sortBy, sortDirection);
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Course>>> getCourseById(@PathVariable String id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (!course.isPresent()) {
            return ApiResponse.buildError(ErrorMessageConstants.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ApiResponse.buildResponse(course);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody CourseInput courseInput) {
        return ApiResponse.buildResponse(courseService.createCourse(courseInput), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable String id,
            @RequestBody CourseInput courseInput) {
        return ApiResponse.buildResponse(courseService.updateCourse(id, courseInput));
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
    };

    @GetMapping("/{creator_id}")
    public ResponseEntity<ApiResponse<List<Course>>> getAllCoursesByCreatorId(@PathVariable String creatorId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "", required = false) String sortBy,
            @RequestParam(defaultValue = "1") int sortDirection) {

        if (limit < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }
        if (sortDirection != -1 && sortDirection != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }
        List<Course> result = courseService.getAllCoursesByCreatorId(page, limit, sortBy, sortDirection, creatorId);
        return ApiResponse.buildResponse(result);
    }

}
