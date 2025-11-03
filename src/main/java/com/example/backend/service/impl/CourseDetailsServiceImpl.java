package com.example.backend.service.impl;

import com.example.backend.mapper.CourseDetailsMapper;
import com.example.backend.service.CourseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseDetailsServiceImpl implements CourseDetailsService {

    @Autowired
    private CourseDetailsMapper courseDetailsMapper;

    @Override
    public void increaseLike(String coursename){
        courseDetailsMapper.increaseLike(coursename);
    }

    @Override
    public void increaseCollect(String coursename){
        courseDetailsMapper.increaseCollect(coursename);
    }

    @Override
    public void increasePurchase(String coursename){
        courseDetailsMapper.increasePurchase(coursename);
    }

    @Override
    public void increaseShare(String coursename){
        courseDetailsMapper.increaseShare(coursename);
    }

    @Override
    public void decreaseLike(String coursename){
        courseDetailsMapper.decreaseLike(coursename);
    }

    @Override
    public void decreaseCollect(String coursename){
        courseDetailsMapper.decreaseCollect(coursename);
    }

    @Override
    public void decreasePurchase(String coursename){
        courseDetailsMapper.decreasePurchase(coursename);
    }

    @Override
    public void decreaseShare(String coursename){
        courseDetailsMapper.decreaseShare(coursename);
    }
}
