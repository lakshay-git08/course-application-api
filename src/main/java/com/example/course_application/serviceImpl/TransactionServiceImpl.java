package com.example.course_application.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.Transaction;
import com.example.course_application.repository.TransactionRepository;
import com.example.course_application.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions(int page, int limit, String sortBy, int sortDirection) {
        Sort sort = Sort.unsorted();
        if (!sortBy.equals("")) {
            Sort.Direction direction = sortDirection == 1 ? Sort.Direction.ASC
                    : sortDirection == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return transactionRepository.findAll(pageable).getContent();
    };

    @Override
    public Optional<Transaction> getTransactionById(String id) {
        Optional<Transaction> transactionFromDB = transactionRepository.findById(id);
        return transactionFromDB;
    }

    @Override
    public List<Transaction> getAllTransactionsByCourseId(String courseId, int page, int limit, String sortBy,
            int sortDirection) {
        Sort sort = Sort.unsorted();
        if (!sortBy.equals("")) {
            Sort.Direction direction = sortDirection == 1 ? Sort.Direction.ASC
                    : sortDirection == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        List<Transaction> transactionsFromDB = transactionRepository.findAllByCourseId(courseId, pageable);
        return transactionsFromDB;
    }

    @Override
    public List<Transaction> getAllTransactionsByStudentId(String studentId, int page, int limit, String sortBy,
            int sortDirection) {

        Sort sort = Sort.unsorted();
        if (!sortBy.equals("")) {
            Sort.Direction direction = sortDirection == 1 ? Sort.Direction.ASC
                    : sortDirection == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        List<Transaction> transactionsFromDB = transactionRepository.findAllByStudentId(studentId, pageable);
        return transactionsFromDB;
    }

}
