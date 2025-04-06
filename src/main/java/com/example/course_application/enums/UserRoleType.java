package com.example.course_application.enums;

public enum UserRoleType {
    STUDENT(0),
    ADMIN(1),
    CREATOR(2);

    public final int roleType;

    private UserRoleType(int roleType) {
        this.roleType = roleType;
    }

}
