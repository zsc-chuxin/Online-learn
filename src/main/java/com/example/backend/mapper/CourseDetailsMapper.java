package com.example.backend.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface CourseDetailsMapper {

    @Update("update courses set `like`=`like`+1 where coursename=#{coursename}")
    void increaseLike(String coursename);

    @Update("update courses set collect=collect+1 where coursename=#{coursename}")
    void increaseCollect(String coursename);

    @Update("update courses set purchase=purchase+1 where coursename=#{coursename}")
    void increasePurchase(String coursename);

    @Update("update courses set share=share+1 where coursename=#{coursename}")
    void increaseShare(String coursename);

    @Update("update courses set `like`=`like`-1 where coursename=#{coursename}")
    void decreaseLike(String coursename);

    @Update("update courses set collect=collect-1 where coursename=#{coursename}")
    void decreaseCollect(String coursename);

    @Update("update courses set purchase=purchase-1 where coursename=#{coursename}")
    void decreasePurchase(String coursename);

    @Update("update courses set share=share-1 where coursename=#{coursename}")
    void decreaseShare(String coursename);
}
