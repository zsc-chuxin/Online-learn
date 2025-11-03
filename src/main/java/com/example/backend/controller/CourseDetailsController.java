package com.example.backend.controller;

import com.example.backend.entity.Result;
import com.example.backend.service.CourseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courseDetails")
@CrossOrigin
public class CourseDetailsController {

    @Autowired
    private CourseDetailsService courseDetailsService;

    @RequestMapping("/increaseLike")
    public Result increaseLike(String coursename){
        courseDetailsService.increaseLike(coursename);
        return Result.success();
    }
    @RequestMapping("/increaseCollect")
    public Result increaseCollect(String coursename){
        courseDetailsService.increaseCollect(coursename);
        return Result.success();
    }
    @RequestMapping("/increasePurchase")
    public Result increasePurchase(String coursename){
        courseDetailsService.increasePurchase(coursename);
        return Result.success();
    }
    @RequestMapping("/increaseShare")
    public Result increaseShare(String coursename){
        courseDetailsService.increaseShare(coursename);
        return Result.success();
    }
    @RequestMapping("/decreaseLike")
    public Result decreaseLike(String coursename){
        courseDetailsService.decreaseLike(coursename);
        return Result.success();
    }
    @RequestMapping("/decreaseCollect")
    public Result decreaseCollect(String coursename){
        courseDetailsService.decreaseCollect(coursename);
        return Result.success();
    }
    @RequestMapping("/decreasePurchase")
    public Result decreasePurchase(String coursename){
        courseDetailsService.decreasePurchase(coursename);
        return Result.success();
    }
    @RequestMapping("/decreaseShare")
    public Result decreaseShare(String coursename){
        courseDetailsService.decreaseShare(coursename);
        return Result.success();
    }

}
