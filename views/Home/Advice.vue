<template>
  <HomeHeader></HomeHeader>
  <div class="adviceContainer" style="width: 80%;background-color: white;margin: 40px auto auto;">
    <div class="title" style="padding-top: 20px">
      <span style="font-size: 23px;padding-left: 20px">意见反馈</span>
    </div>
    <div class="content" style="margin-top: 30px;display: flex">
      <div v-if="!isSuccess" style="flex: 1.8">
        <el-form ref="form" :model="form" label-width="120px" style="width: 700px" :rules="rules">
          <el-form-item label="标题" prop="title">
            <el-input v-model="form.title" placeholder="6-20字"></el-input>
          </el-form-item>
          <el-form-item label="问题类型" prop="type">
            <el-radio-group v-model="form.type">
              <el-radio label="登录注册">登录注册</el-radio>
              <el-radio label="课程内容">课程内容</el-radio>
              <el-radio label="视频播放">视频播放</el-radio>
              <el-radio label="支付问题">支付问题</el-radio>
              <el-radio label="其他问题或建议">其他问题或建议</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="内容" prop="content">
            <el-input type="textarea" v-model="form.content" placeholder="请详细描述您的建议、意见、问题等"></el-input>
            <span style="color: #999999;font-size: 14px;">内容最多不超过500字</span>
          </el-form-item>
          <el-form-item label="相关图片">
            <el-upload
                action="#"
                list-type="picture-card"
                :auto-upload="false"
                :on-preview="handlePictureCardPreview"
                :file-list="fileList"
                v-model="form.image"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>
            <el-dialog v-model="dialogVisible" :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="预览图片" />
            </el-dialog>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-autocomplete
                v-model="form.email"
                :fetch-suggestions="querySearch"
                placeholder="请输入邮箱,以便于我们第一时间联系您"
                clearable
                debounce="10"
                @blur="addEmailSuffix"
            ></el-autocomplete>
          </el-form-item>
          <el-form-item label="联系方式(+86)" prop="number">
            <el-input v-model="form.number" placeholder="请输入手机号，以便于我们第一时间联系您"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="submitForm">提交</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div v-else style="flex: 1.8">
        <el-result
            icon="success"
            title="提交成功"
            sub-title="感谢您的反馈，我们会尽快处理！"
        >
          <template #extra>
            <el-button type="primary" @click="goHome">返回首页</el-button>
          </template>
        </el-result>
      </div>
      <el-divider direction="vertical" style="height: 450px"></el-divider>
      <div style="flex: 1;margin-left: 40px">
        <div style="font-size: 20px">您的支持和鼓励就是对我们最好的帮助！</div>
        <div style="margin-left: 30px">
          <img src="@/assets/images/thanks.jpg" alt="" width="300">
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import { Plus } from '@element-plus/icons-vue';
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import {success} from "element-angular/release/message/images.js";

export default {
  components: { HomeHeader, Plus },
  data() {
    return {
      form: {
        title: '',
        type: '',
        content: '',
        number: '',
        email: '',
        image: []
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 6, max: 20, message: '标题长度在 6 到 20 个字符', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请输入内容', trigger: 'blur' },
          { min: 6, max: 500, message: '内容长度在 6 到 500 个字符', trigger: 'change' }
        ],
        number: [
          { required: true, message: '请输入联系方式', trigger: 'blur' },
          { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号', trigger: 'change' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '请输入正确的邮箱', trigger: 'change' }
        ],
        emailType: [
          { required: true, message: '请选择邮箱类型', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择问题类型', trigger: 'blur' }
        ]
      },
      fileList: [], // 用于存储已上传的文件列表
      dialogVisible: false, // 控制对话框的显示
      dialogImageUrl: '', // 预览图片的 URL
      isSuccess: false
    };
  },
  methods: {
    success() {
      return success
    },
    addEmailSuffix() {
      if (this.form.email !== '' && !this.form.email.includes('@')) {
        this.form.email += '@qq.com';
      }
    },
    querySearch(queryString, cb) {
      // 如果输入的是邮箱地址或者为空，不进行提示
      if (queryString.includes('@') || queryString === '') {
        cb([]);
      } else {
        const emailDomains = ['qq.com', '163.com', '126.com', 'gmail.com', 'sina.com'];

        const suggestions = emailDomains.map(domain => ({ value: `${queryString}@${domain}` }));
        cb(suggestions);
      }
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    submitForm() {
      console.log(this.form);
      this.$refs.form.validate(valid => {
        if (valid) {
          const currentDate = new Date();
          const year = currentDate.getFullYear();
          const month = String(currentDate.getMonth() + 1).padStart(2, '0');
          const day = String(currentDate.getDate()).padStart(2, '0');
          const hours = String(currentDate.getHours()).padStart(2, '0');
          const minutes = String(currentDate.getMinutes()).padStart(2, '0');
          const seconds = String(currentDate.getSeconds()).padStart(2, '0');
          const formattedTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
          request.post("http://localhost:8080/user/advice?title="+this.form.title+"&type="+this.form.type+"&content="+this.form.content+"&email="+this.form.email+"&number="+this.form.number+"&time="+formattedTime).then(res => {
            console.log(res);
            if (res.code === 0) {
              this.isSuccess = true;
            } else {
              ElMessage({
                message: "提交失败",
                type: 'error',
              });
            }
          }).catch(error => {
            console.log(error);
          })
        }
      });
    },
    goHome() {
      this.$router.push('/home');
    },
    resetForm() {
      this.isSuccess = false;
      this.$refs.form.resetFields();
    }
  },
  mounted() {
    this.resetForm();
  }
};
</script>
