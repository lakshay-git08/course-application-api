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
public class CourseDTO {
    @Id
    private String id;

    private String name;

    private Double price;

    private Double discounted_price;

    private String duration;

    private String category;

    private String language;

    private String url;

    private Double rating;

    private Integer rating_count;

    private String thumbnail_image;

    private String created_by;

    private String short_description;

    private String long_description;

    private Date created_at;

    private Date updated_at;

}
