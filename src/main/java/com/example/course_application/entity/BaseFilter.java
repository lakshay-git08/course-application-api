package com.example.course_application.entity;

import lombok.Data;

@Data
public class BaseFilter {

    public int limit = 10;

    public int page = 1;

    public SortFilter sort = new SortFilter();

}
