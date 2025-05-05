package com.example.course_application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.BaseFilter;
import com.example.course_application.entity.Course;
import com.example.course_application.entity.CourseFilter;
import com.example.course_application.input.CourseInput;
import com.example.course_application.service.CourseService;
import com.example.course_application.utils.ErrorMessageConstants;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("")
    // @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses(
            CourseFilter combinedFilter) {

        if (combinedFilter == null) {
            return ApiResponse.buildError("Body is required", HttpStatus.BAD_REQUEST);
        }
        if (combinedFilter.getLimit() < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }

        List<String> validSortFields = List.of("title");
        if (combinedFilter.getSort().getField() != ""
                && !validSortFields.contains(combinedFilter.getSort().getField())) {
            return ApiResponse.buildError("Invalid sort field.",
                    HttpStatus.BAD_REQUEST);
        }

        if (combinedFilter.getSort().getOrder() != -1 && combinedFilter.getSort().getOrder() != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }

        List<Course> result = courseService.getAllCourses(combinedFilter);
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<Optional<Course>>> getCourseById(@PathVariable String id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (!course.isPresent()) {
            return ApiResponse.buildError(ErrorMessageConstants.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ApiResponse.buildResponse(course);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CREATOR')")
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody CourseInput courseInput) {
        return ApiResponse.buildResponse(courseService.createCourse(courseInput), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CREATOR')")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable String id,
            @RequestBody CourseInput courseInput) {
        return ApiResponse.buildResponse(courseService.updateCourse(id, courseInput));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
    };

    @GetMapping("/{creator_id}")
    public ResponseEntity<ApiResponse<List<Course>>> getAllCoursesByCreatorId(@PathVariable String creatorId,
            @RequestBody(required = false) @jakarta.annotation.Nullable BaseFilter combinedFilter) {

        if (combinedFilter == null) {
            return ApiResponse.buildError("Body is required", HttpStatus.BAD_REQUEST);
        }
        if (combinedFilter.getLimit() < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }

        List<String> validSortFields = List.of("title");
        if (combinedFilter.getSort().getField() != ""
                && !validSortFields.contains(combinedFilter.getSort().getField())) {
            return ApiResponse.buildError("Invalid sort field.",
                    HttpStatus.BAD_REQUEST);
        }

        if (combinedFilter.getSort().getOrder() != -1 && combinedFilter.getSort().getOrder() != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }
        List<Course> result = courseService.getAllCoursesByCreatorId(creatorId, combinedFilter);
        return ApiResponse.buildResponse(result);
    }

}
