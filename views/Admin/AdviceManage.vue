<template>
  <AdminHeader></AdminHeader>
  <div style="display: flex">
    <AdminMenu></AdminMenu>
    <div class="rightContainer" style="width: 100%;height:600px;">
        <div style="height: 50px;margin: 10px auto 10px 10px;box-shadow: 0 2px 15px 0 rgba(0, 0, 0, .1);background-color: white">
          <div style="font-size: 20px;padding-left: 10px;padding-top: 11px;color: #666666">这是用户反馈的信息，请您及时处理！</div>
        </div>
        <div style="height: 60px;margin: 10px auto 10px 10px;box-shadow: 0 2px 15px 0 rgba(0, 0, 0, .1);background-color: white">
          <el-input placeholder="请输入反馈标题" v-model="input" clearable style="width: 300px;height: 40px;margin-top: 10px;margin-left: 10px" @input="searchAdvices"></el-input>
          <el-button type="primary" icon="search" style="margin-left: 10px;margin-top: 10px;height: 40px" @click="searchAdvices">搜索</el-button>
        </div>
        <div class="adviceTable" style="margin-top: 10px;height: 450px;margin-left: 10px;box-shadow: 0 2px 15px 0 rgba(0, 0, 0, .1);background-color: white">
            <el-table
                :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
                stripe
                style="width: 100%"
                row-key="id"
                show-overflow-tooltip

            >
              <el-table-column  sortable label="编号" prop="id" width="80" />
              <el-table-column  label="标题" prop="title" width="150" />
              <el-table-column
                  label="反馈类型"
                  prop="type"
                  width="150"
                  :filters="[
                      { text: '登录注册', value: '登录注册' },
                      { text: '课程内容', value: '课程内容' },
                      { text: '视频播放', value: '视频播放' },
                      { text: '支付问题', value: '支付问题' },
                      { text: '其他问题或建议', value: '其他问题或建议' },
                  ]"
                  :filter-method="filterHandler"
              >


              </el-table-column>
              <el-table-column  label="反馈内容" prop="content" width="150" />
              <el-table-column  label="邮箱" prop="email" width="150" />
              <el-table-column  label="电话号码" prop="number" width="150"/>
              <el-table-column  label="反馈时间" sortable prop="time" width="150"/>
              <el-table-column label="查看详情" prop="detail" width="150">
                <template #default="scope">
                    <el-button type="success" @click="showAdviceDetail(scope.row)">查看详情</el-button>
                </template>
              </el-table-column>
              <el-table-column
                  label="状态"
                  prop="state"
                  width="100"
                  :filters="[{ text: '未受理', value: '未受理' },{ text: '已受理', value: '已受理' }]"
                  :filter-method="filterHandler"
              >
                <template #default="scope">
                <span :style="{ color: scope.row.state === '未受理' ? 'red' : 'green' }">{{ scope.row.state }}</span>
                </template>
              </el-table-column>
            </el-table>
          <div style="margin-top: 20px">
            <el-pagination
                background
                layout="total, prev, pager, next,jumper"
                :total="tableData.length"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="pageSize"
                style="text-align: center"
            >
            </el-pagination>
          </div>
        </div>
    </div>
  </div>
  <el-dialog title="反馈详情" v-model="adviceDetailDialogVisible" width="50%" align-center>
    <el-form label-width="80px" ref="formRef" :model="adviceDetail" :rules="rules">
      <el-form-item label="标题">
        <el-input v-model="adviceDetail.title" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="反馈类型">
        <el-input v-model="adviceDetail.type" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="反馈内容">
        <el-input type="textarea" v-model="adviceDetail.content" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="相关图片">
        <el-image></el-image>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="adviceDetail.email" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="电话号码">
        <el-input v-model="adviceDetail.number" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="反馈时间">
        <el-input v-model="adviceDetail.time" :disabled="true"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="adviceDetailDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAdvice">受 理</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import AdminHeader from "@/components/admin/AdminHeader.vue";
import AdminMenu from "@/components/admin/AdminMenu.vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";


export default {
  components: {AdminMenu, AdminHeader},
  data() {
    return {
      input: '',
      tableData: [],
      currentPage: 1,
      pageSize: 6,
      adviceDetailDialogVisible: false,
      adviceDetail: {
        id: '',
        title: '',
        type: '',
        content: '',
        email: '',
        number: '',
        time: '',
      },
    }
  },
  mounted() {
    this.getAdviceList();
  },
  methods: {
    getAdviceList() {
      request.get("http://localhost:8080/admin/findAllAdvices").then(res => {
        this.tableData = res.data || [];
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      })
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    showAdviceDetail(row) {
      this.adviceDetail = {...row};
      this.adviceDetailDialogVisible = true;
    },
    handleAdvice() {
      this.adviceDetail.state = '已受理';
      request.post("http://localhost:8080/admin/updateAdviceState?id="+this.adviceDetail.id+"&state="+this.adviceDetail.state).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "受理成功",
            type: 'success',
          });
          this.adviceDetailDialogVisible = false;
          this.getAdviceList();
        }else {
          ElMessage({
            message: "受理失败",
            type: 'error',
          });
        }
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      })
    },
    searchAdvices() {
      if (this.input.trim() === '') {
        this.getAdviceList();
      } else {
        this.tableData = this.tableData.filter(advice =>
            advice.title.toLowerCase().includes(this.input.toLowerCase())
        );
        this.currentPage = 1;
      }
    }
}
}
</script>

<style>

</style>