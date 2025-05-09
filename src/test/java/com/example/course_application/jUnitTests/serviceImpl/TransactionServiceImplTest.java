package com.example.course_application.jUnitTests.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.course_application.entity.Transaction;
import com.example.course_application.repository.TransactionRepository;
import com.example.course_application.serviceImpl.TransactionServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

        @Mock
        TransactionRepository transactionRepository;

        @InjectMocks
        TransactionServiceImpl transactionServiceImpl;

        @Test
        public void testGetAllTransactions() {
                Transaction transaction_1 = Transaction.builder().id("one").build();
                Transaction transaction_2 = Transaction.builder().id("two").build();

                List<Transaction> transactionList = List.of(transaction_1, transaction_2);
                Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

                Page<Transaction> mockTransaction = new PageImpl<>(transactionList, pageable, transactionList.size());

                Mockito.when(transactionRepository.findAll(pageable)).thenReturn(mockTransaction);

                List<Transaction> result = transactionServiceImpl.getAllTransactions(pageable.getPageNumber() + 1,
                                pageable.getPageSize(), "",
                                1);

                assertNotNull(result, "Result should not be null.");
                assertEquals(transactionList.size(), result.size(),
                                "Transaction List size should be equal to: " + transactionList.size());
                assertEquals("one", result.get(0).getId(), "First Transaction Id should match");
                assertEquals("two", result.get(1).getId(), "Second Transaction Id should match");

                Mockito.verify(transactionRepository).findAll(pageable);
        }

        @Test
        public void testGetTransactionById() {

                String id = "abcdefghijklmnop";
                String userId = "givenUserId";

                Transaction mockTransaction = Transaction.builder().id(id).user_id(userId).build();

                Mockito.when(transactionRepository.findById(id)).thenReturn(Optional.of(mockTransaction));

                Optional<Transaction> result = transactionServiceImpl.getTransactionById(id);

                assertNotNull(result, "Result should not be null");
                assertEquals(userId, result.get().getUser_id(), "Result User id should match");

                Mockito.verify(transactionRepository).findById(id);
        }

        @Test
        public void testGetAllTransactionsByCourseId() {

                String givenCourseId = "one";

                Transaction transaction_1 = Transaction.builder().course_id(givenCourseId).build();
                Transaction transaction_2 = Transaction.builder().course_id(givenCourseId).build();
                Transaction transaction_3 = Transaction.builder().course_id(givenCourseId).build();

                List<Transaction> transactionList = List.of(transaction_1, transaction_2, transaction_3);
                Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

                Mockito.when(transactionRepository.findAllByCourseId(givenCourseId, pageable))
                                .thenReturn(transactionList);

                List<Transaction> result = transactionServiceImpl.getAllTransactionsByCourseId(givenCourseId,
                                pageable.getPageNumber() + 1, pageable.getPageSize(), "",
                                1);

                assertNotNull(result, "Result should not be null");
                assertEquals(transactionList.size(), result.size(),
                                "Transaction List size should be: " + transactionList.size());
                assertEquals(givenCourseId, result.get(0).getCourse_id());

                Mockito.verify(transactionRepository).findAllByCourseId(givenCourseId, pageable);
        }

        @Test
        public void testGetAllTransactionsByStudentId() {

                String givenStudentId = "one";

                Transaction transaction_1 = Transaction.builder().user_id(givenStudentId).build();
                Transaction transaction_2 = Transaction.builder().user_id(givenStudentId).build();
                Transaction transaction_3 = Transaction.builder().user_id(givenStudentId).build();

                List<Transaction> transactionList = List.of(transaction_1, transaction_2, transaction_3);
                Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

                Mockito.when(transactionRepository.findAllByStudentId(givenStudentId, pageable))
                                .thenReturn(transactionList);

                List<Transaction> result = transactionServiceImpl.getAllTransactionsByStudentId(givenStudentId,
                pageable.getPageNumber() + 1, pageable.getPageSize(), "",
                1);

                assertNotNull(result, "Result should not be null");
                assertEquals(transactionList.size(), result.size(),
                                "Transaction List size should be: " + transactionList.size());
                assertEquals(givenStudentId, result.get(0).getUser_id());

                Mockito.verify(transactionRepository).findAllByStudentId(givenStudentId, pageable);
        }

}