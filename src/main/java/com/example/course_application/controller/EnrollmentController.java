package com.example.course_application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.Enrollment;
import com.example.course_application.service.EnrollmentService;
import com.example.course_application.utils.ErrorMessageConstants;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Enrollment>>> getAllEnrollments(
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
        List<Enrollment> result = enrollmentService.getAllEnrollments(page, limit, sortBy, sortDirection);
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Enrollment>>> getEnrollmentById(@PathVariable String id) {
        return ApiResponse.buildResponse(enrollmentService.getEnrollmentById(id));
    }
}
