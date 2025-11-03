<template>
  <HomeHeader></HomeHeader>
  <img src="@/assets/images/404.jpg" alt="" style="position: absolute;height: 100%;width: 100%;z-index: -1;left: 0;top: 0">
  <div style="text-align: center;margin-top: 420px">
    <div style="font-size: 25px;color: #fff">很抱歉</div>
    <div style="font-size: 25px;color: #fff;margin-top: 20px">您访问的页面不存在,请点击下方按钮返回首页</div>
    <el-button type="primary" style="width: 100px;height: 40px;margin-top: 40px" @click="goHome">
      <span style="font-size: 17px">返回首页</span>
    </el-button>
  </div>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
export default {
  components: {HomeHeader},
  data() {
    return {

    }
  },
  methods:{
    goHome() {
      this.$router.push('/home');
    }
  }
}
</script>

<style>
body{
  margin: 0;
}
</style>