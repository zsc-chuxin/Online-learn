package com.example.backend.service;

import com.example.backend.entity.Course;
import com.example.backend.entity.User;
import com.example.backend.entity.UserCourse;

import java.util.List;

public interface UserService {
    User findByUsernameAndType(String username,String type);

    User findByPhoneAndPassword(String phone, String password);

    User findUserByEmail(String email);

    User findByIdAndPassword(Integer id,String password);

    void register(String username, String password);

    void updatePassword(String username, String password);

    boolean validateUser(String username, String password);

    void advice(String title, String type,String content,String email,String number,String time);

    void editUserInfo(String username, String sex, String phone, String email, String address, Integer id);

    Course findCourseDetails(String coursename);

    List<UserCourse> findUserCourse(Integer id);

    int findUserCourseCount(Integer id);

    boolean findUserCourseByCourseName(Integer id, String coursename);

    void addUserCourse(Integer id, String coursename, String courseimage, String buyDate, String orderNumber);

    List<Course> findVideoCourse();

    List<Course> findImageCourse();

    List<Course> findFreeCourse();

    List<Course> findRecommendVideoCourse();

    List<Course> findRecommendImageCourse();
}
