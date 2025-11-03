<template>
  <div ref="chartDom" style="width: 500px; height: 300px"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'BarChart',
  data() {
    return {
      chartDom: null,
      chartInstance: null
    };
  },
  methods: {
    // 初始化ECharts实例并设置配置项
    async initChart() {
      await this.$nextTick(); // 确保DOM已经渲染完成
      this.chartInstance = echarts.init(this.chartDom);

      const option = {
        title: {
          text: '用户使用人数',
          left: 'center',
          top: '20'
        },
        tooltip: {},
        xAxis: {
          data: ['第一天', '第二天', '第三天', '第四天', '第五天']
        },
        center: ['50%', '50%'],
        yAxis: {},
        series: [
          {
            type: 'line', // 这里可以是'line'、'bar'、'pie'等，根据图表类型选择
            data: [2, 1, 3, 5, 4]
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
    }
  },
  mounted() {
    this.chartDom = this.$refs.chartDom;
    this.initChart();
  },
  beforeDestroy() {
    this.destroyChart();
  }
};
</script>

<style scoped>
/* 可以添加一些样式 */
</style>
