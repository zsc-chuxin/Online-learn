package com.example.backend.mapper;

import com.example.backend.entity.Course;
import com.example.backend.entity.User;
import com.example.backend.entity.UserCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    // 根据用户名和类型查找用户
    @Select("select * from users where username=#{username} and type=#{type}")
    User findByUsernameAndType(@Param("username") String username, @Param("type") String type);

    // 根据手机号和密码查找用户
    @Select("select * from users where phone=#{phone} and password=#{password}")
    User findByPhoneAndPassword(@Param("phone")String phone,@Param("password")String password);

    // 根据邮箱查找用户
    @Select("select * from users where email=#{email}")
    User findUserByEmail(@Param("email") String email);

    // 根据id和密码查找用户
    @Select("select * from users where id=#{id} and password=#{password}")
    User findByIdAndPassword(@Param("id") Integer id,@Param("password") String password);

    // 添加用户
    @Insert("insert into users (username,password) values(#{username},#{password})")
    void add(@Param("username") String username,@Param("password") String password);

    // 修改密码
    @Update("update users set password=#{password} where username=#{username}")
    void update(@Param("username") String username,@Param("password") String password);

    // 添加建议
    @Insert("insert into advices (title,type,content,email,number,time) values (#{title},#{type},#{content},#{email},#{number},#{time})")
    void addAdvice(@Param("title") String title, @Param("type")String type, @Param("content")String content, @Param("email")String email, @Param("number")String number, @Param("time")String time);

    // 修改用户信息
    @Update("update users set username=#{username},sex=#{sex},phone=#{phone},email=#{email},address=#{address} where id=#{id}")
    void editUserInfo(@Param("username") String username, @Param("sex") String sex, @Param("phone") String phone, @Param("email") String email, @Param("address") String address, @Param("id") Integer id);

    // 查找推荐的视频课程
    @Select("select * from courses where recommend = 1 and coursetype = '视频课'")
    List<Course> findRecommendVideoCourse();

    // 查找课程类型为视频课的课程
    @Select("select * from courses where coursetype = '视频课' and recommend = 0")
    List<Course> findVideoCourse();

    // 查找推荐的图文课程
    @Select("select * from courses where recommend = 1 and coursetype = '图文课'")
    List<Course> findRecommendImageCourse();

    // 查找课程类型为图文课的课程
    @Select("select * from courses where coursetype = '图文课' and recommend = 0")
    List<Course> findImageCourse();

    // 查找课程价格为0的课程
    @Select("select * from courses where courseprice = 0")
    List<Course> findFreeCourse();

    // 查找课程信息
    @Select("select * from courses where coursename = #{coursename}")
    Course findCourseDetails(@Param("coursename") String coursename);

    // 查找用户已购课程表
    @Select("select * from user_course where id = #{id}")
    List<UserCourse> findUserCourse(@Param("id") Integer id);

    // 查找用户已购课程数量
    @Select("select COUNT(*) from user_course where id = #{id}")
    Integer findUserCourseCount(@Param("id") Integer id);

    //查找用户是否已购买该课程
    @Select("select * from user_course where id = #{id} and coursename = #{coursename}")
    UserCourse findUserCourseByCourseName(@Param("id") Integer id, @Param("coursename") String coursename);

    // 添加用户已购课程
    @Insert("insert into user_course (id,coursename,courseimage,buyDate,orderNumber) values(#{id},#{coursename},#{courseimage},#{buyDate},#{orderNumber})")
    void addUserCourse(@Param("id") Integer id, @Param("coursename") String coursename, @Param("courseimage") String courseimage, @Param("buyDate") String buyDate,@Param("orderNumber") String orderNumber);

}
