package com.example.course_application.dto;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {

    @Id
    private String id;

    private String course_id;

    private String user_id;

    private Double rating_given;

    private Date subscription_starts_at;

    private Date subscription_ends_at;

}
