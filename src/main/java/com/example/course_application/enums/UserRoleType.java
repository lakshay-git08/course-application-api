package com.example.course_application.enums;

public enum UserRoleType {
    STUDENT(0),
    ADMIN(1),
    CREATOR(2);

    public final int roleType;

    UserRoleType(int roleType) {
        this.roleType = roleType;
    }

    public static UserRoleType fromRoleType(int roleType) {
        for (UserRoleType type : values()) {
            if (type.roleType == roleType) {
                return type;
            }
        }
        throw new IllegalArgumentException("invalid role");
    }

}
