package com.example.course_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_application.entity.ApiResponse;
import com.example.course_application.entity.User;
import com.example.course_application.entity.UserFilter;
import com.example.course_application.input.UserInput;
import com.example.course_application.service.UserService;
import com.example.course_application.utils.ErrorMessageConstants;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(
            UserFilter combinedFilter) {

        if (combinedFilter == null) {
            return ApiResponse.buildError("Body is required", HttpStatus.BAD_REQUEST);
        }
        if (combinedFilter.getLimit() < 0) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_LIMIT, HttpStatus.BAD_REQUEST);
        }

        List<String> validSortFields = List.of("title");
        if (combinedFilter.getSort().getField() != ""
                && !validSortFields.contains(combinedFilter.getSort().getField())) {
            return ApiResponse.buildError("Invalid sort field.",
                    HttpStatus.BAD_REQUEST);
        }

        if (combinedFilter.getSort().getOrder() != -1 && combinedFilter.getSort().getOrder() != 1) {
            return ApiResponse.buildError(ErrorMessageConstants.INVALID_SORT_DIRECTION, HttpStatus.BAD_REQUEST);
        }
        List<User> result = userService.getAllUsers(combinedFilter);
        return ApiResponse.buildResponse(result);
    }

    @GetMapping("/get-me")
    public ResponseEntity<ApiResponse<User>> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());

        return ApiResponse.buildResponse(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ApiResponse.buildError(ErrorMessageConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ApiResponse.buildResponse(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable String id,
            @RequestBody UserInput userInput) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ApiResponse.buildError(ErrorMessageConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return ApiResponse.buildResponse(userService.updateUser(userInput, id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(String id) {
        userService.deleteUser(id);
    };

}
