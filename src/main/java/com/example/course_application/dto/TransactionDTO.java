package com.example.course_application.dto;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    @Id
    private String id;

    private Double amount;

    private String user_id;

    private String course_id;

    private Date created_at;

    private Integer status;

}
