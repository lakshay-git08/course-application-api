package com.example.course_application.entity;

import lombok.Data;

@Data
public class SortFilter {

    private String field = "";

    private int order = 1;
}