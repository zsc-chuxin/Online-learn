package com.example.backend.controller;

import com.example.backend.entity.Course;
import com.example.backend.entity.Result;
import com.example.backend.entity.User;
import com.example.backend.entity.UserCourse;
import com.example.backend.service.UserService;
import com.example.backend.utils.JwtUtil;
import com.example.backend.utils.MailUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailUtil mailUtil;

    // 用户注册
    @PostMapping("/register")
    public Result register(String username,String password,String type){

            User u = userService.findByUsernameAndType(username,type);
            if (u == null){
                userService.register(username, password);
                String token = JwtUtil.createToken(username);
                return Result.success().data("token", token);
            }else {
                return Result.error("用户名已存在");
            }
    }

    // 用户登录
    @PostMapping("/login")
    public Result login(String username,String password,String type){
        User u = userService.findByUsernameAndType(username,type);
        if (u == null){
            return Result.error("用户名不存在");
        }else {
            if (u.getPassword().equals(password)){
                String token = JwtUtil.createToken(username);
                return Result.success()
                        .data("token", token)
                        .data("id", u.getId())
                        .data("username", u.getUsername())
                        .data("sex", u.getSex())
                        .data("phone", u.getPhone())
                        .data("email", u.getEmail())
                        .data("address", u.getAddress());
            }else {
                return Result.error("密码错误");
            }
        }
    }

    // 手机号登录
    @PostMapping("/phoneLogin")
    public Result phoneLogin(String phone,String password){
        User u = userService.findByPhoneAndPassword(phone,password);
        if (u == null){
            return Result.error("用户名不存在");
        }else {
            String token = JwtUtil.createToken(phone);
            return Result.success()
                    .data("username", u.getUsername())
                    .data("type", u.getType())
                    .data("token", token)
                    .data("id", u.getId())
                    .data("sex", u.getSex())
                    .data("phone", u.getPhone())
                    .data("email", u.getEmail())
                    .data("address", u.getAddress());
        }
    }

    // 邮箱登录
    @PostMapping("/emailLogin")
    public Result emailLogin(String email) {
        User u = userService.findUserByEmail(email);
        if (u == null) {
            return Result.error("用户名不存在");
        } else {
            String token = JwtUtil.createToken(email);
            return Result.success()
                    .data("token", token)
                    .data("id", u.getId())
                    .data("username", u.getUsername())
                    .data("sex", u.getSex())
                    .data("phone", u.getPhone())
                    .data("email", u.getEmail())
                    .data("type", u.getType())
                    .data("address", u.getAddress());
        }
    }

    // 忘记密码
    @PostMapping("/forget")
    public Result update(String username,String password,String type){
        User u = userService.findByUsernameAndType(username,type );
        if (u == null){
            return Result.error("用户名不存在");
        }else {
            userService.updatePassword(username, password);
            return Result.success();
        }
    }

    // 提交反馈
    @PostMapping("/advice")
    public Result advice(String title, String type,String content,String email,String number,String time){
        userService.advice(title, type, content, email, number, time);
        return Result.success();
    }

    // 修改用户信息
    @PostMapping("/editUser")
    public Result editUser(String username,String sex,String phone,String email,String address,Integer id){
        userService.editUserInfo(username, sex, phone, email, address, id);
        return Result.success();
    }

    // 修改密码
    @PostMapping("/editPassword")
    public Result editPassword(Integer id,String password,String newPassword){
        User u = userService.findByIdAndPassword(id, password);
        if (u == null){
            return Result.error("密码错误");
        }else {
            userService.updatePassword(u.getUsername(), newPassword);
            return Result.success();
        }
    }

    // 查找推荐的视频课程
    @RequestMapping("/findRecommendVideoCourse")
    public Result findRecommendCourse(){
        List<Course> courses = userService.findRecommendVideoCourse();
        return Result.success().data("courses", courses);
    }

    // 查找课程类型为视频课的课程
    @RequestMapping("/findVideoCourse")
    public Result findVideoCourse(){
        List<Course> courses = userService.findVideoCourse();
        return Result.success().data("courses", courses);
    }

    // 查找推荐的图文课程
    @RequestMapping("/findRecommendImageCourse")
    public Result findRecommendImageCourse(){
        List<Course> courses = userService.findRecommendImageCourse();
        return Result.success().data("courses", courses);
    }

    // 查找课程类型为图文课的课程
    @RequestMapping("/findImageCourse")
    public Result findImageCourse(){
        List<Course> courses = userService.findImageCourse();
        return Result.success().data("courses", courses);
    }

    // 查找课程价格为0的课程
    @RequestMapping("/findFreeCourse")
    public Result findFreeCourse(){
        List<Course> courses = userService.findFreeCourse();
        return Result.success().data("courses", courses);
    }

    // 查找课程信息
    @RequestMapping("/findCourseDetails")
    public Result findCourseDetails(String coursename){
        Course c = userService.findCourseDetails(coursename);
        return Result.success()
                .data("like",c.getLike())
                .data("collect",c.getCollect())
                .data("purchase",c.getPurchase())
                .data("share",c.getShare())
                .data("courseimage",c.getCourseimage())
                .data("courseprice",c.getCourseprice());
    }

    // 查找用户已购课程表
    @RequestMapping("/findUserCourse")
    public Result findUserCourse(Integer id){
        // 查找用户已购课程数量
        int count = userService.findUserCourseCount(id);
        // 查找用户已购课程表
        List<UserCourse> courses  = userService.findUserCourse(id);

        for (UserCourse course : courses) {
            Course detail = userService.findCourseDetails(course.getCoursename());
            course.setCourseprice(detail.getCourseprice());
        }

        return Result.success()
                .data("userCourseCounts", count)
                .data("courses", courses);
    }

    //查找用户是否已购买该课程
    @RequestMapping("/isBuyCourse")
    public Result isBuyCourse(Integer id,String coursename){
        if (userService.findUserCourseByCourseName(id, coursename)){
            return Result.success("已购买该课程");
        }else {
            return Result.error("未购买该课程");
        }
    }

    // 添加用户已购课程
    @RequestMapping("/buyCourse")
    public Result buyCourse(Integer id,String coursename,String courseimage,String buyDate,String orderNumber){
        userService.addUserCourse(id, coursename, courseimage, buyDate, orderNumber);
        return Result.success();
    }

    //获取课程介绍
    @RequestMapping("/getCourseIntroduction")
    public Result getCourseIntroduction(String coursename){
        Course c = userService.findCourseDetails(coursename);
        return Result.success().data("introduction",c.getIntroduction());
    }

    //发送邮箱验证码
    @RequestMapping("/sendCodeMail")
    public Result sendMail(@Param("email") String email, HttpServletRequest request, HttpServletResponse response) {
        // 设置http标准
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        // 设置请求头
        response.addHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");

        String code = mailUtil.sendCodeMail(email);
        // 获取session
        HttpSession session = request.getSession(true);
        // 将验证码文本内容放入session
        session.setAttribute("code", code);
        session.setAttribute("time", System.currentTimeMillis());
        return Result.success().data("code", code);
    }

    //验证邮箱验证码
    @PostMapping("/valifyCode")
    public Result valifyCode(@Param("captcha") String captcha, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session == null){
            return Result.error("请先获取验证码");
        }
        String sessionCode = (String) session.getAttribute("code");
        Long generateTime = (Long) session.getAttribute("time");
        if (generateTime == null || sessionCode == null){
            return Result.error("验证码已过期,请重新获取");
        }
        long currentTime = System.currentTimeMillis();
        long validTime = 5 * 60 * 1000; //5分钟
        if (currentTime - generateTime > validTime){
            session.removeAttribute("code");
            session.removeAttribute("time");
            return Result.error("验证码已过期,请重新获取");
        }
        if (sessionCode.equalsIgnoreCase(captcha)){
            session.removeAttribute("code");
            session.removeAttribute("time");
            return Result.success();
        }else {
            return Result.error("验证码错误");
        }
    }
}
