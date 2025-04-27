package com.example.course_application.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.course_application.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {

    @Query(" { user_id: ?0 } ")
    public List<Enrollment> findAllByStudentId(String studentId);
}
