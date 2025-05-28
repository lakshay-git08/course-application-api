package com.example.course_application.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.course_application.entity.Course;
import com.example.course_application.input.CourseInput;
import com.example.course_application.repository.CourseRepository;
import com.example.course_application.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<Course> getAllCourses(int page, int limit, String sortBy, int sortDirection) {
        Sort sort = Sort.unsorted();
        if (!sortBy.equals("")) {
            Sort.Direction direction = sortDirection == 1 ? Sort.Direction.ASC
                    : sortDirection == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        return courseRepository.findAll(pageable).getContent();
    };

    public Optional<Course> getCourseById(String id) {
        return courseRepository.findById(id);
    };

    public Course createCourse(CourseInput courseInput) {
        Course course = modelMapper.map(courseInput, Course.class);
        course.setDeleted(false);
        Course newCourse = courseRepository.save(course);
        return newCourse;
    };

    public Course updateCourse(String id, CourseInput courseInput) {
        Optional<Course> courseFromDB = courseRepository.findById(id);

        if (courseFromDB.isPresent()) {
            Course course = courseFromDB.get();

            if (courseInput.getName() != null) {
                course.setName(courseInput.getName());
            }
            if (courseInput.getPrice() != 0) {
                course.setPrice(courseInput.getPrice());
            }
            if (courseInput.getDiscounted_price() != 0) {
                course.setDiscounted_price(courseInput.getDiscounted_price());
            }
            if (courseInput.getDuration() != null) {
                course.setDuration(courseInput.getDuration());
            }
            if (courseInput.getCategory() != null) {
                course.setCategory(courseInput.getCategory());
            }
            if (courseInput.getLanguage() != null) {
                course.setLanguage(courseInput.getLanguage());
            }
            if (courseInput.getUrl() != null) {
                course.setUrl(courseInput.getUrl());
            }
            if (courseInput.getThumbnail_image() != null) {
                course.setThumbnail_image(courseInput.getThumbnail_image());
            }
            if (courseInput.getShort_description() != null) {
                course.setShort_description(courseInput.getShort_description());
            }
            if (courseInput.getLong_description() != null) {
                course.setLong_description(courseInput.getLong_description());
            }

            Course updatedCourse = courseRepository.save(course);
            return updatedCourse;
        }
        return null;
    };

    public Boolean deleteCourse(String id) {
        Course courseFromDB = courseRepository.findById(id).orElse(null);
        if (courseFromDB != null) {
            courseFromDB.setDeleted(true);
            courseRepository.save(courseFromDB);
            return true;
        }
        return false;
    };

    public List<Course> getAllCoursesByCreatorId(String creatorId, int page, int limit, String sortBy,
            int sortDirection) {

        Sort sort = Sort.unsorted();
        if (!sortBy.equals("")) {
            Sort.Direction direction = sortDirection == 1 ? Sort.Direction.ASC
                    : sortDirection == -1 ? Sort.Direction.DESC : null;
            sort = direction == null ? Sort.unsorted() : Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        List<Course> coursesFromDB = courseRepository.findAllCoursesByCreatorId(creatorId, pageable);
        return coursesFromDB;
    }

}
