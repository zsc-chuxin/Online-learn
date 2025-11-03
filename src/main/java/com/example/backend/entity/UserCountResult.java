package com.example.backend.entity;

import lombok.Data;

@Data
public class UserCountResult {
    private int userCount;
    private int adminCount;

    @Override
    public String toString() {
        return "UserCountResult{" +
                "userCount=" + userCount +
                ", adminCount=" + adminCount +
                '}';
    }
}
