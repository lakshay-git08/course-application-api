package com.example.course_application.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserFilter extends BaseFilter {
    
    private String searchTerm = "";

}
