package com.example.course_application.service;

import java.util.List;
import java.util.Optional;

import com.example.course_application.entity.BaseFilter;
import com.example.course_application.entity.Transaction;

public interface TransactionService {

        public List<Transaction> getAllTransactions(BaseFilter combinedFilter);

        public Optional<Transaction> getTransactionById(String id);

        public List<Transaction> getAllTransactionsByCourseId(String courseId, BaseFilter combinedFilter);

        public List<Transaction> getAllTransactionsByStudentId(String studentId, BaseFilter combinedFilter);
}
