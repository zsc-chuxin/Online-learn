package com.example.backend.entity;

import lombok.Data;

@Data
public class Advice {
    private Integer id;
    private String title;
    private String type;
    private String content;
    private String email;
    private String number;
    private String time;
    private String image;
    private String state;


    @Override
    public String toString() {
        return "Advice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", time='" + time + '\'' +
                ", image='" + image + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
