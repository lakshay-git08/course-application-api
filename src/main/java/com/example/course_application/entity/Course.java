package com.example.course_application.entity;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id
    private String id;

    private String name;

    @Builder.Default
    private Double price = 0.0;

    @Builder.Default
    private Double discounted_price = 0.0;

    private String duration;

    private String expiresIn;

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
