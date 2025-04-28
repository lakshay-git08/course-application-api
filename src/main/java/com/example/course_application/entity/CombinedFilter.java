package com.example.course_application.entity;

import lombok.Data;

@Data
public class CombinedFilter {

    private String category = "";

    private int limit = 10;

    private int page = 1;

    private SortFilter sort = new SortFilter();

    private String searchTerm = "";

}
