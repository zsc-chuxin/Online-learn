<template>
  <div ref="chartDom" style="width: 500px; height: 300px;"></div>
</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request.js";

export default {
  name: 'PieChart',
  data() {
    return {
      chartDom: null,
      chartInstance: null,
    };
  },
  methods: {
    // 获取用户和管理员数量
    async getUserAndAdminCount() {
      try {
        const res = await request.get("http://localhost:8080/admin/getAllUsersCounts");
        return {
          userCount: res.userCount,
          adminCount: res.adminCount
        };
      } catch (error) {
        console.error(error);
        return {
          userCount: 0,
          adminCount: 0
        };
      }
    },
    // 初始化ECharts实例并设置配置项
    async initChart() {
      this.chartInstance = echarts.init(this.chartDom);

      const {userCount, adminCount} = await this.getUserAndAdminCount();

      const option = {
        title: {
          text: '用户与管理员人数',
          left: 'center',
          top: '20'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)' // 显示占比
        },
        series: [
          {
            name: '人数',
            type: 'pie',
            radius: '50%',
            center: ['50%', '50%'],
            data: [
              {value: userCount, name: '用户'},
              {value: adminCount, name: '管理员'}
            ],
            label: {
              formatter: '{b}: {c} ({d}%)' // 显示占比
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ],

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
