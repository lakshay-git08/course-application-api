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
public class UserDTO {
    @Id
    private String id;

    private String name;

    private Date DOB;

    private long phone;

    private String email;

    private int role;

    private int status;

    private String username;

    private String password;

    private Date created_at;

    private Date updated_at;
}
