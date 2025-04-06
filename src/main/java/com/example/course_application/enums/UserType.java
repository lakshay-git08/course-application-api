package com.example.course_application.enums;

public enum UserType {

    STUDENT("student"),
    ADMIN("admin"),
    CREATOR("creator");

    public final String userType;

    private UserType(String userType) {
        this.userType = userType;
    }

}
