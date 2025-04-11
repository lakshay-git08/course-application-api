package com.example.course_application.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.course_application.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query(" { username: ?0} ")
    public Optional<User> findByUsername(String username);
}
