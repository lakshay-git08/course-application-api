package com.example.course_application.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.Enrollment;
import com.example.course_application.repository.EnrollmentRepository;
import com.example.course_application.service.EnrollmentService;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getAllEnrollments(int page, int limit, String sortBy, int sortDirection) {
        Sort sort = Sort.unsorted();
        if (!sortBy.equals("")) {
            Sort.Direction direction = sortDirection == 1 ? Sort.Direction.ASC
                    : sortDirection == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return enrollmentRepository.findAll(pageable).getContent();
    };

    public Optional<Enrollment> getEnrollmentById(String id) {
        Optional<Enrollment> enrollmentFromDB = enrollmentRepository.findById(id);
        return enrollmentFromDB;
    }

    public Enrollment getEnrollmentByStudentId(String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

}
