<template>
  <div class="header">
    <el-menu
        :router="true"
        mode="horizontal"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        :ellipsis="false"
        style="width: 100%"
    >

      <div class="logo">
        <img src="../../assets/images/logo.png" alt="Logo" draggable="false"/>
        <span class="logo-font">在线学习平台</span>
      </div>

      <div class="left-menu">
        <el-menu-item
            index="/home"
            :class="{ 'active': $route.path === '/home' }"
        >首 页</el-menu-item>
        <el-menu-item
            index="/course"
            :class="{ 'active': $route.path === '/course' }"
        >全部课程</el-menu-item>
        <el-menu-item
            index="/about"
            :class="{ 'active': $route.path === '/about' }"
        >关于我们</el-menu-item>
      </div>

      <!-- 搜索栏的部分 -->
<!--      <div class="search">-->
<!--        <el-menu-item>-->
<!--          <el-input v-model="input" style="width: 340px; height: 60px;padding-top: 3px;font-size: 16px" placeholder="请输入关键词">-->
<!--            <template #suffix>-->
<!--              <el-button type="primary" style="height:58px;width: 100px;margin-right: -11px;padding-left: 10px">-->
<!--                <el-icon><Search /></el-icon>-->
<!--                <span>搜索</span>-->
<!--              </el-button>-->
<!--            </template>-->
<!--          </el-input>-->
<!--        </el-menu-item>-->
<!--      </div>-->

      <div class="search">
          <el-autocomplete
              v-model="input"
              clearable
              :fetch-suggestions="querySearch"
              placeholder="请输入关键词"
              style="width: 340px;font-size: 16px"
          >
          </el-autocomplete>
        <el-button
            type="primary"
            icon="search"
            style="margin-top: 2px;background-image:linear-gradient(90deg,#f83600 ,#f9d423 100%);border: none">搜索</el-button>
      </div>



      <div class="right-menu">
        <div v-if="!this.isLogged">
          <el-menu-item>
          <span :style="{ color: isHovered ? '#00bc12' : '#ffffff' }" @mouseover="isHovered = true" @mouseout="isHovered = false" @click="toLogin">登录&#160|&#160注册</span>          </el-menu-item>
        </div>
        <div v-else>
          <el-dropdown>
            <el-menu-item>
              <img src="../../assets/images/avatar.jpg" alt="" style="height: 30px;border-radius: 50%">
              <span class="user" style="margin-right: 20px">{{ username }}</span>
              <el-icon><ArrowDown /></el-icon>
            </el-menu-item>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="userCenter">
                  <el-icon><User /></el-icon>
                  个人中心
                </el-dropdown-item>
                <el-dropdown-item @click="userCourse">
                  <el-icon><Suitcase /></el-icon>
                  我的课程
                </el-dropdown-item>
                <el-dropdown-item @click="userCollect">
                  <el-icon><Star /></el-icon>
                  我的收藏
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-icon><clock /></el-icon>
                 历史记录
                </el-dropdown-item>
                <el-dropdown-item @click="userOrder">
                  <el-icon><ShoppingCartFull /></el-icon>
                  我的订单
                </el-dropdown-item>
                <el-dropdown-item @click="userMessage">
                  <el-icon><Bell /></el-icon>
                  我的消息
                  <el-badge class="mark" is-dot :value="5" :max="99"></el-badge>
                </el-dropdown-item>
                <el-dropdown-item @click="showLogoutDialog">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </el-menu>

    <!-- 退出登录对话框 -->
    <el-dialog
        v-model="logoutDialogVisible"
        title="提示"
        width="30%"
        align-center
    >
      <span>确定要退出登录吗？</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="logoutDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmLogout">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import {ArrowDown, Bell, Clock, ShoppingCartFull, Star, Suitcase, SwitchButton, User} from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import Fuse from "fuse.js";
import {mapMutations, mapState} from "vuex";
import request from "@/utils/request.js";
export default {
  components: {User, Clock, SwitchButton, Bell, ShoppingCartFull, Star, Suitcase, ArrowDown, ElMessage },
  data() {
    return {
      isLogged: false,
      input: '',
      logoutDialogVisible: false, // 控制退出登录对话框的显示状态
      datasource: [
          {value: 'C'},
          {value: 'C++'},
          {value: 'Java'},
          {value: 'Python'},
          {value: 'C#'},
          {value: 'MySQL'},
          {value: 'JavaScript'}
      ],
      fuse: null,
      isHovered: false,
      isFocus: false,
    };
  },
  computed: {
    ...mapState(['username', 'token', 'isLogged'])
  },
  created() {
    this.fuse = new Fuse(this.datasource, {
      keys: ['value'],
      threshold: 0.4
    });
    // if(!this.token){
    //   ElMessage({
    //     message: "请先登录",
    //     type: 'warning',
    //   });
    //   this.$router.push('/home');
    // }
    this.isLogged = !!this.token;
    if (this.token){
      this.checkTokenExpiration();
    }
  },
  methods: {
    showLogoutDialog() {
      this.logoutDialogVisible = true;
    },
    confirmLogout() {
      ElMessage({
        message: "退出登录成功",
        type: 'success',
      });
      setTimeout(() => {
        this.$router.push('/home');
      }, 1000);
      this.clearData();
      this.isLogged = false;
      this.logoutDialogVisible = false;
    },
    userCenter() {
      this.$router.push('/user/center');
    },
    userCourse() {
      this.$router.push('/user/course');
    },
    userCollect() {
      this.$router.push('/user/collect');
    },
    userOrder() {
      this.$router.push('/user/order');
    },
    userMessage() {
      this.$router.push('/user/message');
    },
    querySearch(queryString, cb) {
      if (!queryString){
        cb(this.datasource);
        return;
      }
      const results = this.fuse.search(queryString).map(result => result.item);
      cb(results);
    },
    checkTokenExpiration(){
      request.post("http://localhost:8080/token/validateToken?token="+this.token).then(res => {
        if (res === false){
          ElMessage({
            message: "登录已过期，请重新登录",
            type: 'warning',
          });
          this.clearData();
          window.location.reload();
          this.$router.push('/home');
        }
      })
    },
    toLogin(){
      this.$router.push('/login');
    },
    ...mapMutations(['clearData','setIsLogged']),
  },
};
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-between;
  background-color: #545c64;
}

.logo {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  color: #fff;
  margin-left: 20px;
}

.logo img {
  height: 40px;
}

.logo-font {
  font-size: 20px;
  font-weight: bold;
  margin-left: 10px;
  width: 130px;
}

.left-menu {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-left: 50px;
}

.search {
  display: flex;
  padding-left: 100px;
  justify-content: center;
  width: 340px;
  align-items: center;
}

.right-menu {
  display: flex;
  align-items: center;
  flex-grow: 1;
  justify-content: right;
}

.user {
  margin-left: 10px;
}
.active {
  border-bottom: 3px solid;
  border-image-source:linear-gradient(90deg,#FDD819 ,#d5a4fd 100%);
  border-image-slice: 1;
}
.mark{
  font-size: 5px;
}
</style>
