<template>
  <AdminHeader></AdminHeader>
  <div class="main" style="display: flex">
    <AdminMenu></AdminMenu>
    <div class="main-right" style="width: 100%;height: 800px">

      <div class="main-right-content" style="margin: 10px auto 10px 10px;height: 50px;box-shadow: 0 0 30px 0 rgba(100, 100, 111, 0.2)">
        <div style="font-size: 18px;padding-top: 12px;padding-left: 20px">欢迎您, 管理员!</div>
      </div>

      <div style="width: 98%;height: 120px;box-shadow: 0 0 30px 0 rgba(100, 100, 111, 0.2);margin:0 auto; text-align: center">
        <el-row style="padding-top: 20px">
          <el-col :span="8" style="justify-items: center">
            <el-statistic title="用户总数" :value="totalUsersCount" :suffix="'人'" style="height: 60px;margin-left: 20px"></el-statistic>
            <div class="statistic-footer" style="margin-left: 20px">
              <span style="font-size: 14px;color: #999999">比昨日</span>
              <span style="color: limegreen;font-size: 12px;margin-left: 5px">24%</span>
              <el-icon style="color: limegreen"><CaretTop /></el-icon>
            </div>
          </el-col>
          <el-col :span="8">
            <el-statistic title="课程总数" :value="totalCoursesCount" :suffix="'门'" style="height: 60px;margin-left: 20px"></el-statistic>
          </el-col>
          <el-col :span="8">
            <el-statistic title="课程总收入" :value="income" :prefix="'¥'" style="height: 60px;margin-left: 20px"></el-statistic>
          </el-col>
        </el-row>
      </div>
      <div class="statistics" style="width: 81%;height: 600px;margin:20px auto">
        <div style="height: 300px;display: flex">
          <div style="
            width: 500px;
            margin-left: 25px;
            margin-right: 15px;
            box-shadow: 0 0 30px 0 rgba(100, 100, 111, 0.2)">
            <UserUseECharts></UserUseECharts>
          </div>
          <div style="
            width: 500px;
            margin-left: 15px;
            box-shadow: 0 0 30px 0 rgba(100, 100, 111, 0.2)">
            <CourseIncomeCharts></CourseIncomeCharts>
          </div>
        </div>
        <div style="height: 300px;display: flex;margin-top: 30px">
          <div style="
            width: 500px;
            margin-left: 25px;
            margin-right: 15px;
            box-shadow: 0 0 30px 0 rgba(100, 100, 111, 0.2)">
            <TotalUsersCharts></TotalUsersCharts>
          </div>
          <div style="
            width: 500px;
            margin-left: 15px;
            box-shadow: 0 0 30px 0 rgba(100, 100, 111, 0.2)">
            <CourseIncomeCharts></CourseIncomeCharts>
          </div>
        </div>
      </div>




    </div>
  </div>

</template>
<script lang="ts">
import {ArrowDown, HomeFilled} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import UserUseECharts from '@/components/admin/UserUseCharts.vue'
import CourseIncomeCharts from "@/components/admin/CourseIncomeCharts.vue";
import TotalUsersCharts from "@/components/admin/TotalUsersCharts.vue";
import AdminMenu from "@/components/admin/AdminMenu.vue";
import AdminHeader from "@/components/admin/AdminHeader.vue";
import request from '@/utils/request.js';

export default {
  components: {AdminHeader, ArrowDown, HomeFilled,ElMessage,UserUseECharts,CourseIncomeCharts,AdminMenu,TotalUsersCharts},
  data() {
    return {
        totalUsersCount: 0,
        totalCoursesCount: 0,
        income: 0
    }
  },
  methods: {
    getTotalUsersCount() {
      request.get("http://localhost:8080/admin/getAllUsersCounts").then(res => {
        this.totalUsersCount = res.userCount + res.adminCount;
      })
    },
    getTotalCoursesCount() {
      request.get("http://localhost:8080/admin/getAllCoursesCounts").then(res => {
        this.totalCoursesCount = res.courseCount;
      })
    },
    getTotalIncome() {
      request.get("http://localhost:8080/admin/getTotalIncome").then(res => {
        this.income = res.totalIncome;
      })
    }
  },
  mounted() {
    this.getTotalUsersCount();
    this.getTotalCoursesCount();
    this.getTotalIncome();
  }
}
</script>

<style scoped>
body{
  margin: 0;
}
.main{
  background-color: white;
}
</style>