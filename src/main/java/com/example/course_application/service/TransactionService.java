package com.example.course_application.service;

import java.util.List;
import java.util.Optional;

import com.example.course_application.entity.Transaction;

public interface TransactionService {

        public List<Transaction> getAllTransactions(int page, int limit, String sortBy, int sortDirection);

        public Optional<Transaction> getTransactionById(String id);

        public List<Transaction> getAllTransactionsByCourseId(String courseId, int page, int limit, String sortBy,
                        int sortDirection);

        public List<Transaction> getAllTransactionsByStudentId(String studentId, int page, int limit, String sortBy,
                        int sortDirection);
}
