package com.example.backend.entity;

import lombok.Data;

@Data
public class Course {
    private String coursename;
    private String coursetype;
    private float courseprice;
    private String coursetime;
    private String courseimage;
    private int like;
    private int collect;
    private int purchase;
    private int share;
    private String introduction;
    private int recommend;

    @Override
    public String toString() {
        return "Course{" +
                "coursename='" + coursename + '\'' +
                ", coursetype='" + coursetype + '\'' +
                ", courseprice=" + courseprice +
                ", coursetime='" + coursetime + '\'' +
                ", courseimage='" + courseimage + '\'' +
                ", like=" + like +
                ", collect=" + collect +
                ", purchase=" + purchase +
                ", share=" + share +
                ", introduction='" + introduction + '\'' +
                ", recommend=" + recommend +
                '}';
    }
}
