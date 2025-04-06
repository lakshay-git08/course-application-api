package com.example.course_application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.Transaction;
import com.example.course_application.service.TransactionService;
import com.example.course_application.utils.ErrorMessageConstants;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactions(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "", required = false) String sortBy,
            @RequestParam(defaultValue = "1") int sortDirection) {

        if (limit < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }
        if (sortDirection != -1 && sortDirection != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }
        List<Transaction> result = transactionService.getAllTransactions(page, limit, sortBy, sortDirection);
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Transaction>>> getTransactionById(@PathVariable String id) {

        Optional<Transaction> result = transactionService.getTransactionById(id);
        if (!result.isPresent()) {
            return ApiResponse.buildError("Transaction Not Found", HttpStatus.NOT_FOUND);
        }
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{course_id}")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactionsByCourseId(@PathVariable String courseId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "", required = false) String sortBy,
            @RequestParam(defaultValue = "1") int sortDirection) {
        if (limit < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }
        if (sortDirection != -1 && sortDirection != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }
        List<Transaction> result = transactionService.getAllTransactionsByCourseId(courseId, page, limit, sortBy,
                sortDirection);
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{student_id}")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactionsByStudentId(
            @PathVariable String studentId, @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "", required = false) String sortBy,
            @RequestParam(defaultValue = "1") int sortDirection) {
        if (limit < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }
        if (sortDirection != -1 && sortDirection != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }
        List<Transaction> result = transactionService.getAllTransactionsByStudentId(studentId, page, limit, sortBy,
                sortDirection);
        return ApiResponse.buildResponse(result);
    }
}
