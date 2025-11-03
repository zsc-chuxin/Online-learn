package com.example.backend.service.impl;

import com.example.backend.entity.Course;
import com.example.backend.entity.User;
import com.example.backend.entity.UserCourse;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsernameAndType(String username,String type) {
        User u = userMapper.findByUsernameAndType(username,type);
        return u;
    }

    @Override
    public User findByPhoneAndPassword(String phone, String password) {
        User u = userMapper.findByPhoneAndPassword(phone, password);
        return u;
    }

    @Override
    public User findUserByEmail(String email) {
        User u = userMapper.findUserByEmail(email);
        return u;
    }

    @Override
    public User findByIdAndPassword(Integer id, String password) {
        User u = userMapper.findByIdAndPassword(id, password);
        return u;
    }

    @Override
    public void register(String username, String password) {
        userMapper.add(username, password);
    }

    @Override
    public void updatePassword(String username, String password) {
        userMapper.update(username, password);
    }

    @Override
    public boolean validateUser(String username, String password) {
        User u = userMapper.findByUsernameAndType(username, "user");
        return u != null && u.getPassword().equals(password);
    }

    @Override
    public void advice(String title, String type,String content,String email,String number,String time) {
        userMapper.addAdvice(title,type,content,email,number,time);
    }

    @Override
    public void editUserInfo(String username, String sex, String phone, String email, String address, Integer id) {
        userMapper.editUserInfo(username,sex,phone,email,address,id);
    }

    @Override
    public Course findCourseDetails(String coursename){
        return userMapper.findCourseDetails(coursename);
    }

    @Override
    public List<UserCourse> findUserCourse(Integer id){
        return userMapper.findUserCourse(id);
    }

    @Override
    public int findUserCourseCount(Integer id){
        return userMapper.findUserCourseCount(id);
    }

    @Override
    public boolean findUserCourseByCourseName(Integer id, String coursename){
        return userMapper.findUserCourseByCourseName(id, coursename) != null;
    }

    @Override
    public void addUserCourse(Integer id, String coursename, String courseimage, String buyDate, String orderNumber){
        userMapper.addUserCourse(id, coursename, courseimage, buyDate, orderNumber);
    }

    @Override
    public List<Course> findRecommendVideoCourse(){
        return userMapper.findRecommendVideoCourse();
    }

    @Override
    public List<Course> findVideoCourse(){
        return userMapper.findVideoCourse();
    }

    @Override
    public List<Course> findRecommendImageCourse(){
        return userMapper.findRecommendImageCourse();
    }

    @Override
    public List<Course> findImageCourse(){
        return userMapper.findImageCourse();
    }

    @Override
    public List<Course> findFreeCourse(){
        return userMapper.findFreeCourse();
    }

}
