<template>
  <HomeHeader></HomeHeader>
  <div class="user-order-container" style="background-color: #f0f0f0">
    <div style="width: 90%;margin: 20px auto auto;background-color: white;box-shadow: 0 2px 15px 0 rgba(0, 0, 0, .1);">
      <div class="user-order-header" style="padding: 20px 20px 20px 20px;width: 300px">
        <span style="font-size: 20px">我的订单（{{ userCourseCounts }}）</span>
      </div>
<!--      查找订单号-->
      <div>
        <el-input placeholder="请输入订单号" style="width: 300px;margin-left: 20px" v-model="orderNumber" @input="searchOrder"></el-input>
        <el-button type="primary" icon="search" style="margin-left: 10px" @click="searchOrder">搜索</el-button>
      </div>
      <div style="margin-top: 20px">
        <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)" style="padding-left: 10px" size="small">
          <el-table-column prop="orderNumber" label="订单号" width="200" />

          <el-table-column prop="coursename" label="课程名称" width="200" show-overflow-tooltip />

          <el-table-column prop="courseimage" label="课程图片" width="200">
            <template #default="scope">
              <el-image
                  :src="imageUrl + scope.row.courseimage"
                  style="width: 150px;height: 70px"
                  :preview-src-list="[imageUrl + scope.row.courseimage]"
                  :preview-teleported="true"
                  :hide-on-click-modal="true"
              ></el-image>
            </template>
          </el-table-column>

          <el-table-column prop="buyDate" label="下单时间" width="200" sortable />

          <el-table-column prop="courseprice" label="订单金额" width="200" :formatter="formatPrice"></el-table-column>

          <el-table-column prop="orderStatus" label="订单状态" width="200">
            <template #default="scope">
              <el-tag type="success" size="large" effect="dark">已支付</el-tag>
            </template>
          </el-table-column>
        </el-table>

        <div style="padding: 20px 20px 20px 20px">
          <el-pagination
              background
              layout="total, prev, pager, next, jumper"
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

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeFooter from "@/components/home/HomeFooter.vue";
import {mapState} from "vuex";
import request from "@/utils/request.js";
export default {
  components: {HomeHeader, HomeFooter,},
  data() {
    return {
      tableData: [],
      imageUrl: '../src/assets/images/courseImage/',
      currentPage: 1,
      pageSize: 5,
      userCourseCounts: 0,
    };
  },
  methods: {
    getUserCourse(){
      request.post("http://localhost:8080/user/findUserCourse", null, {
        params: {
          id: this.id
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.courses;
        this.userCourseCounts = res.data.userCourseCounts;
      });
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },
    // 查找订单
    searchOrder(){
      if (this.orderNumber.trim() === '') {
        this.getUserCourse(); // 如果输入为空，重新获取所有课程
      } else {
        this.tableData = this.tableData.filter(course =>
            course.orderNumber.toLowerCase().includes(this.orderNumber.toLowerCase())
        );
        this.currentPage = 1; // 重置当前页为第一页
      }
    },
    formatPrice(row, column, cellValue) {
      return '￥' + cellValue;
    },
  },
  created() {
    this.getUserCourse();
  },
  computed: {
    ...mapState(['username', 'token', 'isLogged', 'id'])
  },
};
</script>

<style>

</style>