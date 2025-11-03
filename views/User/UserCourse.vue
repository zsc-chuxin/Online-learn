<template>
  <HomeHeader></HomeHeader>
  <div class="userCourseContainer" style="background-color: #f0f0f0;width: 100%">
    <div style="width: 90%;background-color: white;height: 500px;margin: 20px auto auto;box-shadow: 0 2px 15px 0 rgba(0, 0, 0, .1);">
      <div style="padding: 20px 20px 20px 20px;">
        <div style="font-size: 20px">我的课程（{{ userCourseCounts }}）</div>
<!--        查询课程-->
        <div style="margin-top: 20px">
          <el-input placeholder="请输入课程名称" style="width: 300px" v-model="coursename" @input="searchCourse"></el-input>
          <el-button type="primary" icon="search" style="margin-left: 10px" @click="searchCourse">搜索</el-button>
        </div>
        <div style="font-size: 20px;margin-top: 20px">
          <div class="carousel-container">
            <div class="carousel" :style="{ transform: `translateX(-${currentIndex * (100 / slidesPerView)}%)` }">
              <div class="carousel-item" v-for="(course, index) in courses" :key="index" :style="{ width: `${100 / slidesPerView}%` }">
                <el-card style="width: 280px;margin-right: 35px;margin-left: 25px" shadow="hover">
                  <el-image
                      :src="imageUrl + course.courseimage"
                      class="courseImage"
                      style="width: 240px;height: 150px"
                      alt=""
                      @click="goToCourseDetails(course.coursename)"
                  ></el-image>
                  <div>
                    <span
                        style="font-size: 18px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; display: inline-block; max-width: 240px;"
                        v-tooltip="course.coursename"
                    >
                          {{ course.coursename }}
                    </span>
                  </div>
                </el-card>
              </div>
            </div>
            <button v-if="!isFirstPage" @click="prev" class="carousel-button prev" style="display: flex;align-items: center">
              <el-icon><ArrowLeft/></el-icon>
            </button>
            <button v-if="!isLastPage" @click="next" class="carousel-button next" style="display: flex;align-items: center">
              <el-icon><ArrowRight/></el-icon>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <HomeFooter></HomeFooter>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeFooter from "@/components/home/HomeFooter.vue";
import request from "@/utils/request.js";
import {mapState} from "vuex";
import {ArrowLeft, ArrowRight} from "@element-plus/icons-vue";

export default {
  components: {ArrowLeft, HomeHeader, HomeFooter, ArrowRight},
  data() {
    return {
      userCourseCounts: 0,
      slidesPerView: 4,
      currentIndex: 0,
      courses: [],
      imageUrl: '../src/assets/images/courseImage/',
      isFirstPage: true,
      isLastPage: false,
      coursename: ''
    };
  },
  methods: {
    getUserCourse() {
      request.post("http://localhost:8080/user/findUserCourse", null, {
        params: {
          id: this.id
        }
      }).then(res => {
        this.userCourseCounts = res.data.userCourseCounts;
        this.courses = res.data.courses;
      });
    },
    next() {
      if (this.currentIndex < this.courses.length - this.slidesPerView) {
        this.currentIndex++;
        this.isLastPage = this.currentIndex >= this.courses.length - this.slidesPerView;
        this.isFirstPage = false;
      }else {
        this.currentIndex = 0;
        this.isLastPage = true;
        this.isFirstPage = false;
      }
    },
    prev() {
      if (this.currentIndex > 0) {
        this.currentIndex--;
        this.isLastPage = false;
        this.isFirstPage = this.currentIndex === 0;
      }else {
        this.currentIndex = this.courses.length - this.slidesPerView;
        this.isLastPage = true;
        this.isFirstPage = false;
      }
    },
    goToCourseDetails(courseName) {
      this.$router.push({path: '/course/details/'+courseName});
    },
    searchCourse() {
      if (this.coursename.trim() === '') {
        this.getUserCourse(); // 如果输入为空，重新获取所有课程
      } else {
        this.courses = this.courses.filter(course =>
            course.coursename.toLowerCase().includes(this.coursename.toLowerCase())
        );
        this.currentIndex = 0; // 重置当前页为第一页
        this.isFirstPage = true;
        this.isLastPage = this.courses.length <= this.slidesPerView;
      }
    }
  },
  computed: {
    ...mapState(['id'])
  },
  mounted() {
    this.getUserCourse();
  }
}
</script>

<style>
.userCourseContainer .carousel-container {
  position: relative;
  width: 100%;
  overflow: hidden;
}

.userCourseContainer .carousel-container .carousel {
  display: flex;
  transition: transform 0.5s ease;
}

.carousel-item {
  box-sizing: border-box;
}
.carousel-item span{
  cursor: pointer;
}

.carousel-button {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px;
  cursor: pointer;
}

.carousel-button.prev {
  height: 50px;
  left: 0;
}

.carousel-button.next {
  height: 50px;
  right: 0;
}
.carousel-container .courseImage {
  cursor: pointer;
  transition: transform 0.25s ease-in-out;
}
.carousel-container .courseImage:hover {
  transform: scale(1.05);
}
</style>