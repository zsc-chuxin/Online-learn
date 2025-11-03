package com.example.backend.entity;

import lombok.Data;

@Data
public class UserCourse {
    private int id;
    private String coursename;
    private String courseimage;
    private String buyDate;
    private String orderNumber;
    private float courseprice;
}
