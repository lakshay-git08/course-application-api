package com.example.course_application.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.course_application.entity.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    @Query(" { course_id:?0} ")
    public List<Transaction> findAllByCourseId(String courseId, Pageable pageable);

    @Query(" { user_id:?0 } ")
    public List<Transaction> findAllByStudentId(String studentId, Pageable pageable);

}
