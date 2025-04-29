package com.example.course_application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.BaseFilter;
import com.example.course_application.entity.Transaction;
import com.example.course_application.service.TransactionService;
import com.example.course_application.utils.ErrorMessageConstants;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactions(
            @RequestBody(required = false) @jakarta.annotation.Nullable BaseFilter combinedFilter) {

        if (combinedFilter == null) {
            return ApiResponse.buildError("Body is required", HttpStatus.BAD_REQUEST);
        }
        if (combinedFilter.getLimit() < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }

        List<String> validSortFields = List.of("title");
        if (combinedFilter.getSort().getField() != ""
                && !validSortFields.contains(combinedFilter.getSort().getField())) {
            return ApiResponse.buildError("Invalid sort field.",
                    HttpStatus.BAD_REQUEST);
        }

        if (combinedFilter.getSort().getOrder() != -1 && combinedFilter.getSort().getOrder() != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }

        List<Transaction> result = transactionService.getAllTransactions(combinedFilter);
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Optional<Transaction>>> getTransactionById(@PathVariable String id) {

        Optional<Transaction> result = transactionService.getTransactionById(id);
        if (!result.isPresent()) {
            return ApiResponse.buildError("Transaction Not Found", HttpStatus.NOT_FOUND);
        }
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{course_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactionsByCourseId(@PathVariable String courseId,
            @RequestBody(required = false) @jakarta.annotation.Nullable BaseFilter combinedFilter) {

        if (combinedFilter == null) {
            return ApiResponse.buildError("Body is required", HttpStatus.BAD_REQUEST);
        }
        if (combinedFilter.getLimit() < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }

        List<String> validSortFields = List.of("title");
        if (combinedFilter.getSort().getField() != ""
                && !validSortFields.contains(combinedFilter.getSort().getField())) {
            return ApiResponse.buildError("Invalid sort field.",
                    HttpStatus.BAD_REQUEST);
        }

        if (combinedFilter.getSort().getOrder() != -1 && combinedFilter.getSort().getOrder() != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }

        List<Transaction> result = transactionService.getAllTransactionsByCourseId(courseId, combinedFilter);
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{student_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactionsByStudentId(
            @PathVariable String studentId,
            @RequestBody(required = false) @jakarta.annotation.Nullable BaseFilter combinedFilter) {

        if (combinedFilter == null) {
            return ApiResponse.buildError("Body is required", HttpStatus.BAD_REQUEST);
        }
        if (combinedFilter.getLimit() < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }

        List<String> validSortFields = List.of("title");
        if (combinedFilter.getSort().getField() != ""
                && !validSortFields.contains(combinedFilter.getSort().getField())) {
            return ApiResponse.buildError("Invalid sort field.",
                    HttpStatus.BAD_REQUEST);
        }

        if (combinedFilter.getSort().getOrder() != -1 && combinedFilter.getSort().getOrder() != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }

        List<Transaction> result = transactionService.getAllTransactionsByStudentId(studentId, combinedFilter);
        return ApiResponse.buildResponse(result);
    }
}
