package com.example.course_application.enums;

public enum UserRoleType {
    STUDENT(0),
    ADMIN(1),
    CREATOR(2);

    public final int value;

    UserRoleType(int value) {
        this.value = value;
    }

    public static UserRoleType fromRoleType(int roleType) {
        UserRoleType[] allValues = values();

        // UserRoleType.ADMIN gives ADMIN.
        // UserRoleType.ADMIN.value gives 1.

        for (UserRoleType type : allValues) {
            if (type.value == roleType) {
                return type;
            }
        }
        throw new IllegalArgumentException("invalid role");
    }
}
