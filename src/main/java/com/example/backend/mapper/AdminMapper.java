package com.example.backend.mapper;

import com.example.backend.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select * from users where type = 'user'")
    List<User> findAllUsers();

    @Select("select * from courses")
    List<Course> findAllCourses();

    @Select("select * from users where type = 'admin'")
    List<User> findAllAdmins();

    @Select("select * from advices")
    List<Advice> findAllAdvices();

    @Insert("insert into users (username,password,sex,email,address) values(#{username},#{password},#{sex},#{email},#{address})")
    void addUser(@Param("username") String username, @Param("password") String password, @Param("sex") String sex, @Param("email") String email, @Param("address") String address);

    @Delete("delete from users where username=#{username}")
    void deleteUser(String username);

    @Update("update users set type=#{type}, sex=#{sex}, address=#{address}, email=#{email} where username=#{username}")
    void updateUser(@Param("username") String username,@Param("type") String type,@Param("sex") String sex,@Param("address") String address,@Param("email") String email);

//    void batchDeleteUsers(@Param("usernames") List<String> usernames);

    @Insert("insert into users (username,password,sex,email,address,type) values(#{username},#{password},#{sex},#{email},#{address},#{type})")
    void addAdmin(@Param("username") String username, @Param("password") String password, @Param("sex") String sex, @Param("email") String email, @Param("address") String address, @Param("type") String type);

    @Insert("insert into courses (coursename, coursetype, introduction,courseprice, coursetime, courseimage) values (#{coursename},#{coursetype},#{introduction},#{courseprice},#{coursetime},#{courseimage})")
    void addCourse(@Param("coursename") String coursename, @Param("coursetype") String coursetype, @Param("introduction") String introduction,@Param("courseprice") String courseprice, @Param("coursetime") String coursetime, @Param("courseimage") String courseimage);

    @Delete("delete from courses where coursename=#{coursename}")
    void deleteCourse(String coursename);

//    void batchDeleteCourses(@Param("coursenames") List<String> coursenames);

    @Update("update courses set coursetype=#{coursetype},introduction=#{introduction},courseprice=#{courseprice},coursetime=#{coursetime},courseimage=#{courseimage} where coursename=#{coursename}")
    void updateCourse(@Param("coursename") String coursename,@Param("coursetype") String coursetype,@Param("introduction") String introduction,@Param("courseprice") String courseprice,@Param("coursetime") String coursetime,@Param("courseimage") String courseimage);

    @Update("update advices set state=#{state} where id=#{id}")
    void updateAdviceState(@Param("id") Integer id,@Param("state") String state);

    @Select("SELECT " +
            "(SELECT COUNT(*) FROM users WHERE type = 'user') AS userCount, " +
            "(SELECT COUNT(*) FROM users WHERE type = 'admin') AS adminCount")
    UserCountResult getAllUsersCounts();

    @Select("SELECT COUNT(courses.coursename)as courseCount from courses")
    CourseCountResult getAllCoursesCounts();

    //修改管理员密码
    @Update("update users set password=#{newPassword} where username=#{username} and password=#{oldPassword}")
    void updateAdminPassword(@Param("username") String username,@Param("newPassword") String newPassword, @Param("oldPassword") String oldPassword);

    //检验原密码是否正确
    @Select("select * from users where username=#{username} and password=#{oldPassword}")
    User checkOldPassword(@Param("username") String username, @Param("oldPassword") String oldPassword);

    // 通过user_course表的所有订单号查询课程名称
    // 再通过课程名称查询课程价格
    // 最后计算总收入
    @Select("SELECT SUM(c.courseprice) AS totalIncome " +
            "FROM courses c " +
            "JOIN user_course uc ON c.coursename = uc.coursename " +
            "WHERE uc.orderNumber IN (SELECT orderNumber FROM user_course)")
    Income getTotalIncome();

    // 计算所有视频课程的总收入
    @Select("SELECT SUM(c.courseprice) AS videoIncome " +
            "FROM courses c " +
            "JOIN user_course uc ON c.coursename = uc.coursename " +
            "WHERE uc.orderNumber IN (SELECT orderNumber FROM user_course) " +
            "AND c.coursetype = '视频课'")
    Income getVideoIncome();

    // 计算所有图文课程的总收入
    @Select("SELECT SUM(c.courseprice) AS imageIncome " +
            "FROM courses c " +
            "JOIN user_course uc ON c.coursename = uc.coursename " +
            "WHERE uc.orderNumber IN (SELECT orderNumber FROM user_course) " +
            "AND c.coursetype = '图文课'")
    Income getImageIncome();
}
