package com.example.course_application.entity;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    @Id
    private String id;

    private String course_id;

    private String user_id;

    private Double rating_given;

    private Date subscription_starts_at;

    private Date subscription_ends_at;

}
