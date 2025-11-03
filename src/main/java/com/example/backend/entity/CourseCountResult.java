package com.example.backend.entity;

import lombok.Data;

@Data
public class CourseCountResult {
    private int courseCount;

    @Override
    public String toString() {
        return "CourseCountResult{" +
                "courseCount=" + courseCount +
                '}';
    }
}
