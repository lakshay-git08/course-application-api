package com.example.course_application.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    private String id;

    private String name;

    private Date DOB;

    private long phone;

    private String email;

    private int roleType;

    private int status;

    private String username;

    @JsonIgnore
    private String password;

    private Date created_at;

    private Date updated_at;

}
