<template>
    <HomeHeader></HomeHeader>
    <div class="user-collect-container">
      <div style="width: 90%;margin: 20px auto auto;background-color: white;box-shadow: 0 2px 15px 0 rgba(0, 0, 0, .1);">
        <div class="user-collect-header" style="padding: 20px 20px 20px 20px;width: 300px">
          <span style="font-size: 20px">我的收藏（{{  }}）</span>
        </div>
      </div>
    </div>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeFooter from "@/components/home/HomeFooter.vue";
export default {
    components: {HomeHeader, HomeFooter,},
};
</script>

<style>

</style>