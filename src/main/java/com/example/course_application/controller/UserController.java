package com.example.course_application.controller;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.User;
import com.example.course_application.input.UserInput;
import com.example.course_application.service.UserService;
import com.example.course_application.utils.ErrorMessageConstants;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "", required = false) String sortBy,
            @RequestParam(defaultValue = "1") String sortDirection) {

        if (limit < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }
        int sortDirectionInt;
        if (!sortDirection.equals("-1") && !sortDirection.equals("1")) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        } else {
            sortDirectionInt = Integer.parseInt(sortDirection);
        }
        List<User> result = userService.getAllUsers(page, limit, sortBy, sortDirectionInt);
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<User>>> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        if (!user.isPresent()) {
            return ApiResponse.buildError(ErrorMessageConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ApiResponse.buildResponse(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable String id,
            @RequestBody UserInput userInput) {
        Optional<User> user = userService.getUserById(id);
        if (!user.isPresent()) {
            return ApiResponse.buildError(ErrorMessageConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ApiResponse.buildResponse(userService.updateUser(userInput, id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(String id) {
        userService.deleteUser(id);
    };

}
