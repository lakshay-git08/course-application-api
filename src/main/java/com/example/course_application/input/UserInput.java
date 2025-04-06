package com.example.course_application.input;

import java.sql.Date;

import com.example.course_application.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInput {

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date DOB;

    private long phone;

    private String email;

    private UserType type;

    private String username;

    private String password;
}
