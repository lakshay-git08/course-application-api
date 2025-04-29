package com.example.course_application.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.BaseFilter;
import com.example.course_application.entity.Enrollment;
import com.example.course_application.repository.EnrollmentRepository;
import com.example.course_application.service.EnrollmentService;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getAllEnrollments(BaseFilter combinedFilter) {
        Sort sort = Sort.unsorted();
        if (!combinedFilter.getSort().getField().equals("")) {
            Sort.Direction direction = combinedFilter.getSort().getOrder() == 1 ? Sort.Direction.ASC
                    : combinedFilter.getSort().getOrder() == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, combinedFilter.getSort().getField());
        }
        Pageable pageable = PageRequest.of(combinedFilter.getPage() - 1, combinedFilter.getLimit(), sort);

        return enrollmentRepository.findAll(pageable).getContent();
    };

    public Optional<Enrollment> getEnrollmentById(String id) {
        Optional<Enrollment> enrollmentFromDB = enrollmentRepository.findById(id);
        return enrollmentFromDB;
    }

    public List<Enrollment> getEnrollmentsByStudentId(String studentId) {
        return enrollmentRepository.findAllByStudentId(studentId);
    }

}
