package com.example.course_application.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.course_application.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ username: ?0 , password: ?1 }")
    public Optional<User> findUserByUsernameAndPassword(String username, String password);
}
