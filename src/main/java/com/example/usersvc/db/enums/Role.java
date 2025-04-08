package com.example.usersvc.db.enums;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MODERATOR;

    public String getAuthority() {
        return name();
    }
}
