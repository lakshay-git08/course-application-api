package com.example.course_application.service;

import java.util.List;
import java.util.Optional;

import com.example.course_application.entity.CombinedFilter;
import com.example.course_application.entity.Enrollment;

public interface EnrollmentService {

    public List<Enrollment> getAllEnrollments(CombinedFilter combinedFilter);

    public Optional<Enrollment> getEnrollmentById(String id);

    public List<Enrollment> getEnrollmentsByStudentId(String studentId);
}
