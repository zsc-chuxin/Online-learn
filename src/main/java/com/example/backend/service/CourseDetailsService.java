package com.example.backend.service;

public interface CourseDetailsService {

    void increaseLike(String coursename);

    void increaseCollect(String coursename);

    void increasePurchase(String coursename);

    void increaseShare(String coursename);

    void decreaseLike(String coursename);

    void decreaseCollect(String coursename);

    void decreasePurchase(String coursename);

    void decreaseShare(String coursename);
}
