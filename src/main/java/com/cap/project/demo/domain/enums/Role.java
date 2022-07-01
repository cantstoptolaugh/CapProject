package com.cap.project.demo.domain.enums;

public enum Role {


    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_EXPERT("ROLE_EXPERT");


    private String Authority;

    Role(String authority) {
        this.Authority = authority;
    }

    public String getAuthority() {
        return Authority;
    }
}
