package com.example.backend.service;

import com.example.backend.entity.*;

import java.util.List;

public interface AdminService {

    List<User> findAllUsers();

    List<Course> findAllCourses();

    List<User> findAllAdmins();

    List<Advice> findAllAdvices();

    void addUser(String username, String password, String sex, String email, String address);

    void deleteUser(String username);

    void updateUser(String username, String type, String sex, String address, String email);

    void batchDeleteUsers(List<String> usernames);

    void addAdmin(String username, String password, String sex, String email, String address, String type);

    void deleteAdmin(String username);

    void batchDeleteAdmins(List<String> usernames);

    void updateAdmin(String username, String type, String sex, String address, String email);

    void addCourse(String coursename, String coursetype, String introduction,String courseprice, String coursetime, String courseimage);

    void deleteCourse(String coursename);

    void batchDeleteCourses(List<String> coursenames);

    void updateCourse(String coursename, String coursetype, String introduction, String courseprice, String coursetime, String courseimage);

    void updateAdviceState(Integer id, String state);

    UserCountResult getAllCounts();

    CourseCountResult getAllCoursesCounts();

    void updateAdminPassword(String username, String newPassword, String oldPassword);

    boolean checkOldPassword(String username, String oldPassword);

    Income getTotalIncome();

    Income getVideoIncome();

    Income getImageIncome();
}
