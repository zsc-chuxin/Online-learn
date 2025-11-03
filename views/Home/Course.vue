<template>
  <HomeHeader></HomeHeader>
  <div style="width: 100%; margin: 5px auto; display: flex; height: 700px; box-shadow: 0 0 30px 0 rgba(100, 100, 111, 0.2);" class="container">
    <div style="margin: 20px auto" class="table">
      <el-input v-model="input" style="width: 220px;padding-left: 10px" placeholder="请输入课程名称" @input="searchCourses"></el-input>
      <el-button type="success" icon="search" style="margin-left: 10px" @click="searchCourses">搜索</el-button>
      <el-button type="danger" icon="refreshRight" style="margin-left: 10px" @click="getCourseList">刷新课程</el-button>
      <div v-loading="loading" element-loading-text="加载中..." style="width: 100%;">
        <el-table
            :data="formattedTableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
            stripe
            style="width: 100%;margin-top: 15px"
            size="small"
            @row-click="handleRowClick"
        >
          <el-table-column prop="courseimage" label="课程图片" width="220">
            <template #default="scope">
              <el-image
                  :src="scope.row.courseimage"
                  style="width: 150px;height: 70px"
                  :preview-src-list="[scope.row.courseimage]"
                  :preview-teleported="true"
                  :hide-on-click-modal="true"
              ></el-image>
            </template>
          </el-table-column>
          <el-table-column prop="coursename" label="课程名称" width="220">
            <template #default="scope">
              <router-link :to="`/course/details/${scope.row.coursename}`" target="_blank">{{ scope.row.coursename }}</router-link>
            </template>
          </el-table-column>
          <el-table-column prop="coursetype" label="课程类型" width="220" />
          <el-table-column prop="courseprice" label="课程价格/元" sortable width="220" />
          <el-table-column prop="coursetime" label="发布时间" sortable width="220" />
        </el-table>
        <div style="margin-top: 20px">
            <el-pagination
              background
              layout="total, prev, pager, next,jumper"
              :total="tableData.length"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-size="pageSize"
              style="text-align: center"
            ></el-pagination>
        </div>
      </div>
    </div>
  </div>
  <HomeFooter></HomeFooter>
</template>

<script lang="ts">
import HomeHeader from "@/components/home/HomeHeader.vue";
import { ref } from "vue";
import request from "@/utils/request.js";
import HomeFooter from "@/components/home/HomeFooter.vue";
import { ElMessage } from "element-plus";

export default {
  name: "Course",
  components: { HomeFooter, HomeHeader },
  data() {
    return {
      currentPage: 1,
      pageSize: 6,
      tableData: [],
      input: '',
      loading: false,
      imageUrl: '../src/assets/images/courseImage/'
    }
  },
  methods: {
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },
    getCourseList() {
      this.loading = true;
      request.get("http://localhost:8080/admin/findAllCourses").then(res => {
        this.tableData = res.data || [];
        setTimeout(() => {
          this.loading = false;
        }, 1500);
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
        this.loading = false; // 加载失败
      });
    },
    searchCourses() {
      if (this.input.trim() === '') {
        this.getCourseList(); // 如果输入为空，重新获取所有课程
      } else {
        this.tableData = this.tableData.filter(course =>
            course.coursename.toLowerCase().includes(this.input.toLowerCase())
        );
        this.currentPage = 1; // 重置当前页为第一页
      }
    },
    handleRowClick(row) {
      window.open(`/course/details/${row.coursename}`, '_blank');
    }
  },
  computed: {
    formattedTableData() {
      return this.tableData.map(item => ({
        ...item,
        courseimage: this.imageUrl + item.courseimage
      }));
    }
  },
  mounted() {
    this.getCourseList();
  }
}
</script>

<style>
.container {
  background-color: white;
}
a {
  text-decoration: none;
}
.router-link-exact-active {
  text-decoration: none;
}
.example-showcase .el-loading-mask {
  z-index: 9;
}
</style>
