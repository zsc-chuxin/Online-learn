<template>

  <div class="homeMain" style="width: 76%;margin: 50px auto;min-height: 500px">

    <div style="display: flex;align-items: center">
      <div style="flex: 1">
        <span style="font-size: 24px;color: #333333">热门课程</span>
        <i class="iconfont" style="margin-right: 40px;margin-left: 5px">&#xe613;</i>
        <el-button type="success" @click="showVideoCourse">视频课程</el-button>
        <el-button type="primary" @click="showImageCourse">图文课程</el-button>
        <el-button type="warning" @click="showFreeCourse">免费课程</el-button>
      </div>
      <div style="width: 350px">
        <span style="margin-left: 35px;color: #67c23a;font-weight: bold">当前时间：</span>
        <span style="color: #666666">{{ dateTime }}</span>
<!--        星期-->
        <span style="color: #666666;margin-left: 10px">
          {{ day }}</span>
      </div>
    </div>

    <div style="margin-top: 20px;position: relative">
<!--      上一页-->
      <el-button
          @click="prevPage"
          style="position: absolute;
            left: -60px;
            top: 48%;
            transform: translateY(-50%);
            height: 80px;
            z-index: 1000;
            width: 30px"
          color="rgba(31, 45, 61, 0.11)">
        <el-icon><arrow-left/></el-icon>
      </el-button>
<!--      下一页-->
      <el-button
          @click="nextPage"
          style="position: absolute;
            right: -60px;
            top: 48%;
            transform: translateY(-50%);
            z-index:1000;
            height: 80px
            ;width: 30px"
          color="rgba(31, 45, 61, 0.11)">
        <el-icon><arrow-right/></el-icon>
      </el-button>
      <div v-if="!isFreeCourse" style="height: 340px">
        <div class="carousel-container">
          <div class="carousel" :style="{ transform: `translateX(-${currentIndex * (100 * 2 / 9)}%)` }">
            <div class="carousel-item" style="display: flex">
              <!-- 推荐课程部分 -->
              <div style="flex: 1;margin-right: 10px" v-for="reCourse in recommendCourses" :key="reCourse.id">
                <img
                    :src="imageUrl + reCourse.courseimage"
                    alt=""
                    style="width: 100%;height: 280px;border-radius: 10px"
                    @click="goToCourseDetails(reCourse.coursename)"
                    class="image"
                >
                <div style="font-size: 18px;margin-top: 3px;">{{ reCourse.coursename }}</div>
              </div>
              <!-- 课程部分 -->
              <div style="flex: 2;height: 300px">
  <!--              <el-row :gutter="20">-->
  <!--                <el-col :span="8" style="margin-bottom: 10px" v-for="course in paginatedCourses" :key="course.id">-->
  <!--                  <img :src="imageUrl + course.courseimage" alt="" style="width: 100%;height: 120px;border-radius: 10px" @click="goToCourseDetails(course.coursename)">-->
  <!--                  <div style="font-size: 15px;margin-top: 5px;">{{ course.coursename }}</div>-->
  <!--                </el-col>-->
  <!--              </el-row>-->
  <!--              每列两个，从左到右-->
                <div style="display: flex;flex-wrap: wrap;flex-direction: column;height: 320px">
                  <div style="margin-bottom: 10px;width: 32%;margin-left: 12px" v-for="course in courses" :key="course.id">
                    <img :src="imageUrl + course.courseimage" alt="" style="width: 230px;height: 120px;border-radius: 10px" @click="goToCourseDetails(course.coursename)">
                    <div style="font-size: 15px;margin-top: 5px;">{{ course.coursename }}</div>
                  </div>
                </div>
              </div>
          </div>
          </div>
        </div>
      </div>
<!--      免费课程部分-->
      <div v-else style="">
        <el-row :gutter="20">
          <el-col :span="6" style="margin-bottom: 10px" v-for="course in courses" :key="course.id">
            <img :src="imageUrl + course.courseimage" alt="" style="width: 100%;height: 130px;border-radius: 10px" @click="goToCourseDetails(course.coursename)">
            <div style="font-size: 15px;margin-top: 5px;">{{ course.coursename }}</div>
          </el-col>
        </el-row>
      </div>
    </div>

    <div style="margin-top: 20px">
      <span style="font-size: 24px;color: #333333">最新课程</span>
      <i class="iconfont" style="margin-right: 40px;margin-left: 5px">&#xeb0e;</i>
      <span style="margin-left: -80px;color: white">NEW</span>
    </div>
    <div style="display: flex;margin-top: 20px;height: 300px ">
      <div style="flex: 2;margin-right: 10px">
        <el-row :gutter="20">
          <el-col :span="8" style="margin-bottom: 10px">
            <img src="../../assets/images/homeCourseImage/0d6ae969-e8cc-4ea9-af3e-ba471066fe24.jpg" alt="" loading="lazy" style="width: 100%;height: 120px;border-radius: 10px" @click="goToCourseDetails('vue全系列课程:vue预渲染')">
            <div style="font-size: 15px;margin-top: 5px;">vue全系列课程:vue预渲染</div>
          </el-col>
          <el-col :span="8">
            <img src="../../assets/images/homeCourseImage/3bf190b7-4dd4-4f60-a6fa-f2bd752bd5fb.jpg" alt="" loading="lazy" style="width: 100%;height: 120px;border-radius: 10px" @click="goToCourseDetails('Java高级开发课程公开课')">
            <div style="font-size: 15px;margin-top: 5px;">Java高级开发课程公开课</div>
          </el-col>
          <el-col :span="8">
            <img src="../../assets/images/homeCourseImage/bca15f32-1da2-43d2-955a-83bd0bfb1b7f.jpg" alt="" loading="lazy" style="width: 100%;height: 120px;border-radius: 10px" @click="goToCourseDetails('UI设计课程')">
            <div style="font-size: 15px;margin-top: 5px;">UI设计课程</div>
          </el-col>
          <el-col :span="8">
            <img src="../../assets/images/homeCourseImage/8157e335-5aea-4e9c-90ec-54e49966d24c.jpg" alt="" loading="lazy" style="width: 100%;height: 120px;border-radius: 10px" @click="goToCourseDetails('网站开发学C必杀技')">
            <div style="font-size: 15px;margin-top: 5px;">网站开发学C必杀技</div>
          </el-col>
          <el-col :span="8">
            <img src="../../assets/images/homeCourseImage/6a826a85-9c55-46a5-ac5d-99937601d233.jpg" alt="" loading="lazy" style="width: 100%;height: 120px;border-radius: 10px" @click="goToCourseDetails('Linux云计算 Python开发运维')">
            <div style="font-size: 15px;margin-top: 5px;">Linux云计算 Python开发运维</div>
          </el-col>
          <el-col :span="8">
            <img src="../../assets/images/homeCourseImage/321506fa-4409-4834-b2ec-c7428d3e8787.jpg" alt="" loading="lazy" style="width: 100%;height: 120px;border-radius: 10px" @click="goToCourseDetails('SSM框架从入门到实战')">
            <div style="font-size: 15px;margin-top: 5px;">SSM框架从入门到实战</div>
          </el-col>
        </el-row>
      </div>
      <div style="flex: 1;margin-left: 10px">
        <img src="../../assets/images/homeCourseImage/589dc49a-c1ef-49ee-af92-3e2bef634bfe.jpg" alt="" loading="lazy" style="width: 100%;height: 280px;border-radius: 10px" @click="goToCourseDetails('学C从零入门')">
        <div style="font-size: 18px;margin-top: 3px;">零基础学C#</div>
      </div>
    </div>


    <div style="margin-top: 80px">
      <p style="text-align: center;color: #333333;font-size: 25px">让每一个有志于自我提升的人，都能够获得更高质量的教育</p>
      <div style="display: flex;margin-top: 50px">
        <i class="iconfont" style="color: #333333;font-size: 180px;flex: 1;margin-top: 50px">&#xe653;</i>
        <div style="text-align: center;flex: 4">
          <img src="../../assets/images/learn1.jpg" alt="" loading="lazy" style="width: 300px;height: 300px">
          <img src="../../assets/images/learn2.jpg" alt="" loading="lazy" style="width: 300px;height: 300px;margin-left: 20px">
        </div>
        <i class="iconfont" style="color: #333333;font-size: 180px;flex: 1;margin-top: 50px">&#xe652;</i>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request.js";
import {ArrowLeft, ArrowRight} from "@element-plus/icons-vue";
export default {
  components: {ArrowRight, ArrowLeft},
  data() {
    return {
      dateTime: '', // 存储当前日期和时间
      intervalId: null, // 用于存储定时器ID
      day: '', // 存储当前星期
      isFreeCourse: false,
      recommendCourses: [],// 推荐课程
      courses: [],
      imageUrl: '/src/assets/images/courseImage/',
      currentIndex: 0,
      slidesPerView: 4,

    }
  },
  mounted() {
    this.updateDateTime();
    // 设置定时器，每秒钟更新一次时间
    this.intervalId = setInterval(this.updateDateTime, 1000);
    this.showVideoCourse();
  },
  beforeDestroy() {
    // 组件销毁前清除定时器，防止内存泄漏
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  },
  methods: {
    // 更新当前日期和时间
    updateDateTime() {
      const nowDate = new Date();
      const date = {
        year: nowDate.getFullYear(),
        month: nowDate.getMonth() + 1,
        day: nowDate.getDate(),
        hours: nowDate.getHours(),
        minutes: nowDate.getMinutes(),
        seconds: nowDate.getSeconds(),
      };
      // 获取当前星期
      const week = ['日', '一', '二', '三', '四', '五', '六'];
      this.day = '星期' + week[nowDate.getDay()];
      // 小于10在前面添0
      const newMonth = date.month >= 10 ? date.month : '0' + date.month;
      const newDay = date.day >= 10 ? date.day : '0' + date.day;
      const newHours = date.hours >= 10 ? date.hours : '0' + date.hours;
      const newMinutes = date.minutes >= 10 ? date.minutes : '0' + date.minutes;
      const newSeconds = date.seconds >= 10 ? date.seconds : '0' + date.seconds;

      // 更新dateTime属性
      this.dateTime = `${date.year}-${newMonth}-${newDay} ${newHours}:${newMinutes}:${newSeconds}`;
    },
    goToCourseDetails(courseName) {
      this.$router.push({ path: '/course/details/'+courseName});
    },
    getRecommendVideoCourses() {
      request.post("http://localhost:8080/user/findRecommendVideoCourse").then(res => {
        this.recommendCourses = res.data.courses;
      })
    },
    showVideoCourse(){
      this.currentIndex = 0;
      this.isFreeCourse = false;
      this.getRecommendVideoCourses();
      request.post("http://localhost:8080/user/findVideoCourse").then(res => {
        this.courses = res.data.courses;
      })
    },
    getRecommendImageCourses() {
      request.post("http://localhost:8080/user/findRecommendImageCourse").then(res => {
        this.recommendCourses = res.data.courses;
      })
    },
    showImageCourse(){
      this.currentIndex = 0;
      this.isFreeCourse = false;
      this.getRecommendImageCourses();
      request.post("http://localhost:8080/user/findImageCourse").then(res => {
        this.courses = res.data.courses;
      })
    },
    showFreeCourse(){
      this.isFreeCourse = true;
      request.post("http://localhost:8080/user/findFreeCourse").then(res => {
        this.courses = res.data.courses;
      })
    },
    prevPage() {
      if (this.currentIndex > 0) {
        this.currentIndex--;
      }
    },
    nextPage() {
      // 如果当前页不是最后一页
      if ((this.courses.length  - (this.currentIndex * 2 + 6)) > 0) {
        this.currentIndex++;
      }
    }
  },
  computed: {

  }
}
</script>

<style scoped>
img {
  transition: transform 0.2s ease-in-out;
}
img:hover {
  transform: scale(1.02);
}
.carousel-container {
  overflow: hidden;
  position: relative;
  width: 100%;
}
.carousel {
  display: flex;
  transition: transform 0.5s ease;
}
.carousel-item {
  box-sizing: border-box;
}
</style>