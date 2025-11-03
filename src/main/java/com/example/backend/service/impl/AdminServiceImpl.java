package com.example.backend.service.impl;

import com.example.backend.entity.*;
import com.example.backend.mapper.AdminMapper;
import com.example.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<User> findAllUsers()
    {
        return adminMapper.findAllUsers();
    }

    @Override
    public List<Course> findAllCourses()
    {
        return adminMapper.findAllCourses();
    }

    @Override
    public List<User> findAllAdmins()
        {
        return adminMapper.findAllAdmins();
    }

    @Override
    public List<Advice> findAllAdvices(){return adminMapper.findAllAdvices();}

    @Override
    public void addUser(String username, String password, String sex, String email, String address) {
        adminMapper.addUser(username,password,sex,email,address);
    }

    @Override
    public void deleteUser(String username) {
        adminMapper.deleteUser(username);
    }

    @Override
    public void updateUser(String username, String type, String sex, String address, String email) {
        adminMapper.updateUser(username,type,sex,address,email);
    }

    @Override
    public void batchDeleteUsers(List<String> usernames) {
        for (String username : usernames) {
            adminMapper.deleteUser(username);
        }

    }

    @Override
    public void addAdmin(String username, String password, String sex, String email, String address, String type) {
        adminMapper.addAdmin(username,password,sex,email,address,type);
    }

    @Override
    public void deleteAdmin(String username) {
        adminMapper.deleteUser(username);
    }

    @Override
    public void batchDeleteAdmins(List<String> usernames) {
        for (String username : usernames) {
            adminMapper.deleteUser(username);
        }
    }

    @Override
    public void updateAdmin(String username, String type, String sex, String address, String email) {
        adminMapper.updateUser(username,type,sex,address,email);
    }

    @Override
    public void addCourse(String coursename, String coursetype, String introduction,String courseprice, String coursetime, String courseimage) {
        adminMapper.addCourse(coursename,coursetype,introduction,courseprice,coursetime,courseimage);
    }


    @Override
    public void deleteCourse(String coursename) {
        adminMapper.deleteCourse(coursename);
    }

    @Override
    public void batchDeleteCourses(List<String> coursenames) {
        for (String coursename : coursenames) {
            adminMapper.deleteCourse(coursename);
        }
    }

    @Override
    public void updateCourse(String coursename, String coursetype, String introduction,String courseprice, String coursetime, String courseimage) {
        adminMapper.updateCourse(coursename,coursetype,introduction,courseprice,coursetime,courseimage);
    }

    @Override
    public void updateAdviceState(Integer id, String state) {
        adminMapper.updateAdviceState(id,state);
    }

    @Override
    public UserCountResult getAllCounts() {
        return adminMapper.getAllUsersCounts();
    }

    @Override
    public CourseCountResult getAllCoursesCounts() {
        return adminMapper.getAllCoursesCounts();
    }

    @Override
    public void updateAdminPassword(String username, String newPassword, String oldPassword) {
        adminMapper.updateAdminPassword(username,newPassword,oldPassword);
    }

    @Override
    public boolean checkOldPassword(String username, String oldPassword) {
        User u = adminMapper.checkOldPassword(username, oldPassword);
        return u != null;
    }

    @Override
    public Income getTotalIncome() {
        return adminMapper.getTotalIncome();
    }

    @Override
    public Income getVideoIncome() {
        return adminMapper.getVideoIncome();
    }

    @Override
    public Income getImageIncome() {
        return adminMapper.getImageIncome();
    }
}


