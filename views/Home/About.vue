<template>
    <HomeHeader></HomeHeader>
    <div class="container">
      <div class="about" style="width: 100%;box-shadow: 0 0 30px 0 rgba(100, 100, 111, 0.2);text-align: center;height: 100px;margin: 20px auto;padding-top: 15px">
        <h1>发现你的潜力，轻松掌握新技能——在线学习，让梦想触手可及！</h1>
    </div>
      <div style="display: flex;height: 500px;box-shadow: 0 0 30px 0 rgba(100, 100, 111, 0.2)">
      <div class="schoolLogo" style="flex: 1;margin: 50px auto;padding-left: 30px">
          <img src="@/assets/images/school.jpg" alt="" width="400">
      </div>
      <div class="introduction" style="flex:2;margin: 50px auto;padding-right: 50px">
          <span style="font-size: 18px">景德镇学院是一所多科性全日制公办本科院校，创建于1977年，坐落在全国历史文化名城、全国文明城市、中国优秀旅游城市、世界闻名的千年瓷都——江西省景德镇市。</span>
          <p style="font-size: 18px">学校将继续高举习近平新时代中国特色社会主义思想伟大旗帜，深入学习党的二十大精神、党的二十届二中、三中全会精神，认真贯彻落实全国教育大会精神和习近平总书记考察江西重要讲话精神，坚持和加强党对学校工作的全面领导，聚焦“走在前、勇争先、善作为”的目标要求，牢记为党育人、为国育才使命，以高质量发展为主线，扎实开展“大抓落实年”活动，以本科教育教学审核评估和“申硕”为抓手，全力推进专业认证、产教融合、内涵建设三大任务，扎实推进应用转型、实体强院、服务地方、管理变革四大行动，奋力加快示范应用型本科高校建设步伐。</p>
          <span style="font-size: 18px">摘自学校官网=></span>
          <span style="font-size: 18px">
            <a href="https://www.jdzu.edu.cn/" target="_blank" style="text-decoration: none">链接(前往访问)</a>
          </span>

      </div>
    </div>
    </div>
    <HomeFooter></HomeFooter>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeFooter from "@/components/home/HomeFooter.vue";

export default {
  name: "About",
  components: {HomeFooter, HomeHeader}
}
</script>

<style scoped>
.container{
  background-color: white;
}
</style>