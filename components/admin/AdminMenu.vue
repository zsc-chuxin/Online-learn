<template>
  <div class="menu" style="width: 220px;height: 100%">
    <el-menu
        active-text-color="#006eff"
        class="el-menu-vertical-demo"
        text-color="#666666"
        :default-active="activeIndex"
        :unique-opened="false"
    >
      <el-menu-item index="/home" @click="adminPage">
        <el-icon><HomeFilled /></el-icon>
        系统首页
      </el-menu-item>
      <el-sub-menu index="1">
        <template #title>
          <el-icon><Menu /></el-icon>
          <span>信息管理</span>
        </template>
        <el-menu-item index="1-1" @click="courseList">课程列表</el-menu-item>
        <el-menu-item index="1-2">审核管理</el-menu-item>
        <el-menu-item index="1-3" @click="adviceList">反馈信息</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="2">
        <template #title>
          <el-icon><Menu /></el-icon>
          <span>用户管理</span>
        </template>
        <el-menu-item index="2-1" @click="userList">用户信息</el-menu-item>
        <el-menu-item index="2-2" @click="adminList">管理员信息</el-menu-item>
      </el-sub-menu>

    </el-menu>
  </div>
</template>

<script>
import {HomeFilled} from "@element-plus/icons-vue";
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";

export default {
  components: {HomeFilled},
  setup () {
    const route = useRoute();
    const activeIndex = ref("")

    onMounted(()=> {
      setActiveIndex()
    });
    const setActiveIndex = () => {
      const path = route.path
      switch (path) {
        case '/admin':
          activeIndex.value = "/home"
          break;
        case '/admin/courseManage':
          activeIndex.value = "1-1"
          break;
        case '/admin/adviceManage':
          activeIndex.value = "1-3"
          break;
        case '/admin/userManage':
          activeIndex.value = "2-1"
          break;
        case '/admin/adminManage':
          activeIndex.value = "2-2"
          break;
        default:
          activeIndex.value = ""
      }
    };
    return {
      activeIndex,
      setActiveIndex
    }
  },
  methods: {
    courseList() {
      this.$router.push('/admin/courseManage');
      this.setActiveIndex()
    },
    userList() {
      this.$router.push('/admin/userManage');
      this.setActiveIndex()
    },
    adminPage() {
      this.$router.push('/admin');
      this.setActiveIndex()
    },
    adminList() {
      this.$router.push('/admin/adminManage');
      this.setActiveIndex()
    },
    adviceList() {
      this.$router.push('/admin/adviceManage');
      this.setActiveIndex()
    },
    courseInfo() {
      this.$router.push('/admin/courseInfo');
      this.setActiveIndex()
    }
  }
}
</script>

<style scoped>
body{
  margin: 0;
}
.el-menu .el-menu-item.is-active,
.el-menu .el-menu-item.router-link-active {
  background-color: #cce2ff;
  border-right: 3px solid #006eff;
}

.el-menu .el-sub-menu .el-menu-item.is-active,
.el-menu .el-sub-menu .el-menu-item.router-link-active {
  background-color: #cce2ff;
  border-right: 3px solid #006eff;
}
</style>