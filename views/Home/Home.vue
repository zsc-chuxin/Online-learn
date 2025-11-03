<template>
  <div class="language" style="text-align: right;color: #666666;background-color: #f0f0f0">
    <span>{{ $t('messages.language') }}：</span>
    <select v-model="$i18n.locale" style="margin-right: 200px">
      <option value="zh">简体中文</option>
      <option value="en">English</option>
    </select>
  </div>
  <div class="header"><HomeHeader></HomeHeader></div>
  <div class="carousel"><HomeCarousel></HomeCarousel></div>
  <div class="main"><HomeMain></HomeMain></div>
  <div class="footer"><HomeFooter/></div>
  <el-backtop :right="50" :bottom="100" style="color: #545c64"/>
  <el-button class="pendant" size="large" circle style="width: 80px;height: 80px" @click="advicePage">
    <el-icon size="30px"><Edit /></el-icon>
    <div style="font-size:14px; margin-top: 10px">反馈</div>
  </el-button>
  <el-button class="qrcode" size="large" circle style="width: 80px;height: 80px" @mouseenter="showQrCode" @mouseleave="hideQrCode">
    <i class="iconfont" style="color: gray;font-size: 30px">&#xe76e;</i>
    <div style="font-size:14px; margin-top: 10px">联系我们</div>
  </el-button>
  <div v-if="isQrCodeVisible" class="qrcode-popup" style="text-align: center">
    <img src="@/assets/images/qrcode.jpg" alt="" style="width: 150px; height: 150px;">
    <div>
      <span style="font-size: 14px;color: black;">扫码添加官方微信</span>
    </div>
  </div>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeCarousel from "@/components/home/HomeCarousel.vue";
import HomeMain from "@/components/home/HomeMain.vue";
import { defineComponent } from "vue";
import HomeFooter from "@/components/home/HomeFooter.vue";

export default defineComponent({
  components: {
    HomeHeader,
    HomeCarousel,
    HomeMain,
    HomeFooter
  },
  data() {
    return {
      isQrCodeVisible: false
    };
  },
  methods: {
    advicePage() {
      this.$router.push('/home/advice');
    },
    showQrCode() {
      this.isQrCodeVisible = true;
    },
    hideQrCode() {
      this.isQrCodeVisible = false;
    }
  }
});
</script>

<style>
body {
  margin: 0;
  background-color: #f0f0f0!important;
}
.carousel {
  padding-top: 3px;
}
.pendant {
  position: fixed;
  right: 30px;
  bottom: 160px;
  background-color: white;
  border-radius: 50%;
  z-index: 1000;
}
.pendant > span {
  display: block;
}
.qrcode {
  position: fixed;
  right: 30px;
  bottom: 260px;
  background-color: white;
  border-radius: 50%;
  z-index: 1000;
}
.qrcode > span {
  display: block;
}
.qrcode-popup {
  position: fixed;
  right: 120px;
  bottom: 210px;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 12px;
  padding: 10px;
  z-index: 1001;
}
</style>
