package com.example.course_application.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.BaseFilter;
import com.example.course_application.entity.Transaction;
import com.example.course_application.repository.TransactionRepository;
import com.example.course_application.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions(BaseFilter combinedFilter) {
        Sort sort = Sort.unsorted();
        if (!combinedFilter.getSort().getField().equals("")) {
            Sort.Direction direction = combinedFilter.getSort().getOrder() == 1 ? Sort.Direction.ASC
                    : combinedFilter.getSort().getOrder() == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, combinedFilter.getSort().getField());
        }
        Pageable pageable = PageRequest.of(combinedFilter.getPage() - 1, combinedFilter.getLimit(), sort);

        return transactionRepository.findAll(pageable).getContent();
    };

    public Optional<Transaction> getTransactionById(String id) {
        Optional<Transaction> transactionFromDB = transactionRepository.findById(id);
        return transactionFromDB;
    }

    public List<Transaction> getAllTransactionsByCourseId(String courseId, BaseFilter combinedFilter) {
        Sort sort = Sort.unsorted();
        if (!combinedFilter.getSort().getField().equals("")) {
            Sort.Direction direction = combinedFilter.getSort().getOrder() == 1 ? Sort.Direction.ASC
                    : combinedFilter.getSort().getOrder() == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, combinedFilter.getSort().getField());
        }
        Pageable pageable = PageRequest.of(combinedFilter.getPage() - 1, combinedFilter.getLimit(), sort);

        List<Transaction> transactionsFromDB = transactionRepository.findAllByCourseId(courseId, pageable);
        return transactionsFromDB;
    }

    public List<Transaction> getAllTransactionsByStudentId(String studentId, BaseFilter combinedFilter) {
        Sort sort = Sort.unsorted();
        if (!combinedFilter.getSort().getField().equals("")) {
            Sort.Direction direction = combinedFilter.getSort().getOrder() == 1 ? Sort.Direction.ASC
                    : combinedFilter.getSort().getOrder() == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, combinedFilter.getSort().getField());
        }
        Pageable pageable = PageRequest.of(combinedFilter.getPage() - 1, combinedFilter.getLimit(), sort);

        List<Transaction> transactionsFromDB = transactionRepository.findAllByStudentId(studentId, pageable);
        return transactionsFromDB;
    }

}
