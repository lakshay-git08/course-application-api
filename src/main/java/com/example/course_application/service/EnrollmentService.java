package com.example.course_application.service;

import java.util.List;
import java.util.Optional;

import com.example.course_application.entity.Enrollment;

public interface EnrollmentService {

    public List<Enrollment> getAllEnrollments(int page, int limit, String sortBy, int sortDirection);

    public Optional<Enrollment> getEnrollmentById(String id);

    public List<Enrollment> getEnrollmentsByStudentId(String studentId, int page, int limit, String sortBy,
            int sortDirection);
}
