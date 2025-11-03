package com.example.backend.controller;

import com.example.backend.entity.*;
import com.example.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @RequestMapping("/findAllUsers")
    public Result<List<User>> findAllUsers(){
        List<User> users = adminService.findAllUsers();
        return Result.success(users);
    }

    @RequestMapping("/findAllCourses")
    public Result<List<Course>> findAllCourses(){
        List<Course> courses = adminService.findAllCourses();
        return Result.success(courses);
    }

    @RequestMapping("/findAllAdvices")
    public Result<List<Advice>> findAllAdvices(){
        List<Advice> advices = adminService.findAllAdvices();
        return Result.success(advices);
    }

    @RequestMapping("/findAllAdmins")
    public Result<List<User>> findAllAdmins(){
        List<User> admins = adminService.findAllAdmins();
        return Result.success(admins);
    }

    @RequestMapping("/addUser")
    public Result addUser(String username,String password,String sex,String email,String address){
        adminService.addUser(username,password,sex,email,address);
        return Result.success();
    }

    @RequestMapping("/deleteUser")
    public Result deleteUser(String username){
        adminService.deleteUser(username);
        return Result.success();
    }

    @RequestMapping("/updateUser")
    public Result updateUser(String username,String type,String sex,String address,String email){
        adminService.updateUser(username,type,sex,address,email);
        return Result.success();
    }

    @RequestMapping("/batchDeleteUsers")
    public Result batchDeleteUsers(@RequestBody List<String> usernames) {
        adminService.batchDeleteUsers(usernames);
        return Result.success();
    }

    @RequestMapping("/addAdmin")
    public Result addAdmin(String username,String password,String sex,String email,String address,String type){
        adminService.addAdmin(username,password,sex,email,address,type);
        return Result.success();
    }

    @RequestMapping("/deleteAdmin")
    public Result deleteAdmin(String username){
        adminService.deleteAdmin(username);
        return Result.success();
    }

    @RequestMapping("/batchDeleteAdmins")
    public Result batchDeleteAdmins(@RequestBody List<String> usernames) {
        adminService.batchDeleteUsers(usernames);
        return Result.success();
    }

    @RequestMapping("/updateAdmin")
    public Result updateAdmin(String username,String type,String sex,String address,String email){
        adminService.updateAdmin(username,type,sex,address,email);
        return Result.success();
    }

    @RequestMapping("/addCourse")
    public Result addCourse(
            String coursename,
            String coursetype,
            String introduction,
            String courseprice,
            String coursetime,
            String courseimage){
        adminService.addCourse(coursename,coursetype,introduction,courseprice,coursetime,courseimage);
        return Result.success();
    }

    @RequestMapping("/deleteCourse")
    public Result deleteCourse(String coursename){
        adminService.deleteCourse(coursename);
        return Result.success();
    }

    @RequestMapping("batchDeleteCourses")
    public Result batchDeleteCourses(@RequestBody List<String> coursenames) {
        adminService.batchDeleteCourses(coursenames);
        return Result.success();
    }

    @RequestMapping("/updateCourse")
    public Result updateCourse(
            String coursename,
            String coursetype,
            String introduction,
            String courseprice,
            String coursetime,
            String courseimage){
        adminService.updateCourse(coursename,coursetype,introduction,courseprice,coursetime,courseimage);
        return Result.success();
    }

    @RequestMapping("/updateAdviceState")
    public Result updateAdviceState(Integer id,String state){
        adminService.updateAdviceState(id,state);
        return Result.success();
    }

    @RequestMapping("/getAllUsersCounts")
    public UserCountResult getAllCounts(){
        return adminService.getAllCounts();
    }

    @RequestMapping("/getAllCoursesCounts")
    public CourseCountResult getAllCoursesCounts(){
        return adminService.getAllCoursesCounts();
    }

    @RequestMapping("/updateAdminPassword")
    public Result updateAdminPassword(String username,String newPassword,String oldPassword){
        if (adminService.checkOldPassword(username,oldPassword)){
            adminService.updateAdminPassword(username,newPassword,oldPassword);
            return Result.success();
        }else {
            return Result.error("原密码错误");
        }
    }

    // 获取所有课程总收入
    @RequestMapping("/getTotalIncome")
    public Income getTotalIncome(){
        return adminService.getTotalIncome();
    }

    // 获取所有视频课程总收入
    @RequestMapping("/getVideoIncome")
    public Income getVideoIncome(){
        return adminService.getVideoIncome();
    }

    // 获取所有图文课程总收入
    @RequestMapping("/getImageIncome")
    public Income getImageIncome(){
        return adminService.getImageIncome();
    }
}
