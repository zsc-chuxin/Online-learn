<template>
  <div class="homeCarousel">
    <div style="display: flex">

      <div style="flex: 76%">
        <el-carousel height="350px" type="card">
          <el-carousel-item v-for="item in carouselData" :key="item">
            <img :src="item" alt="" style="width: 100%;height: 350px">
          </el-carousel-item>
        </el-carousel>
      </div>


    </div>


  </div>
</template>

<script>
import one from "@/assets/images/carouselImage/1.jpg"
import two from "@/assets/images/carouselImage/2.jpg"
import three from "@/assets/images/carouselImage/3.jpg"
import four from "@/assets/images/carouselImage/4.jpeg"
import five from "@/assets/images/carouselImage/5.jpg"

export default {
  data() {
    return{
      carouselData:[
          one,
          two,
          three,
          four,
          five

      ]
    }
  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}
</style>