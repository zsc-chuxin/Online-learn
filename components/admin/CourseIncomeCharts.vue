<template>
  <div ref="chartDom" style="width: 500px; height: 300px"></div>
</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request.js";

export default {
  name: 'LineChart',
  data() {
    return {
      chartDom: null,
      chartInstance: null,
      videoIncome: 0,
      imageIncome: 0
    };
  },
  methods: {
    // 初始化ECharts实例并设置配置项
    async initChart() {
      await this.$nextTick(); // 确保DOM已经渲染完成
      this.chartInstance = echarts.init(this.chartDom);

      const option = {
        title: {
          text: '课程收入',
          left: 'center',
          top: '20'
        },
        tooltip: {},
        xAxis: {
          data: ['视频课', '图文课']
        },
        yAxis: {},
        series: [
          {
            name: '课程类型',
            type: 'bar', // 这里可以是'line'、'bar'、'pie'等，根据图表类型选择
            data: [this.videoIncome,this.imageIncome],
            itemStyle: {
              color: function (params) {
                let colorList = ['#f47920', '#00e500'];
                return colorList[params.dataIndex];
              }
            }
          }
        ]
      };

      this.chartInstance.setOption(option);
    },
    // 销毁ECharts实例
    destroyChart() {
      if (this.chartInstance != null && this.chartInstance.dispose) {
        this.chartInstance.dispose();
      }
    },
    // 获取数据
    async getData() {
      try {
        const res1 = await request.get("http://localhost:8080/admin/getVideoIncome");
        const res2 = await request.get("http://localhost:8080/admin/getImageIncome");
        this.videoIncome = res1.videoIncome;
        this.imageIncome = res2.imageIncome;
        this.chartInstance.setOption({
          series: [
            {
              data: [this.videoIncome, this.imageIncome]
            }
          ]
        });
      } catch (error) {
        console.error(error);
      }
    },
  },
  mounted() {
    this.chartDom = this.$refs.chartDom;
    this.initChart();
    this.getData();
  },
  beforeDestroy() {
    this.destroyChart();
  }
};
</script>

<style scoped>
/* 可以添加一些样式 */
</style>
