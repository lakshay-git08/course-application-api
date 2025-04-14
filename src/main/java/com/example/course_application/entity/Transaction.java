package com.example.course_application.entity;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private String id;

    private Double amount;

    private String user_id;

    private String course_id;

    private Date created_at;

    private Integer status;

}
