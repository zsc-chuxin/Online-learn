<template>
  <div class="forgetContainer" style="height: 665px">
    <img src="@/assets/images/bg.jpg" alt="" style="position: absolute;height: 100%;width: 100%;z-index: -1;left: 0;top: 0">

    <div style="margin-top: 30px;width: 400px;border:1px solid #dcdfe6;height: 350px;border-radius: 10px;background-color: white">

      <div style="width: 340px;height: 270px;margin: 40px auto">

        <div style="text-align: center;margin-bottom: 20px;font-size: 25px;color: #333333;width: 340px">重置密码</div>
        <el-form :model="form" :rules="rules" ref="formRef">
          <el-form-item prop="username">
            <el-input placeholder="请输入账号" v-model="form.username">
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input placeholder="请输入密码" show-password v-model="form.password">
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="confirm">
            <el-input placeholder="确认密码" show-password v-model="form.confirm">
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>

          <el-button :plain="true" type="primary" style="width: 100%;text-align: center" @click="reset" :disabled="isButtonDisabled">重置</el-button>
          <div style="flex: 1;text-align: right;margin-top: 20px">
            返回
            <router-link to="/login">登录</router-link>
          </div>
        </el-form>
      </div>

    </div>
  </div>
</template>

<script>
import { ElForm, ElFormItem, ElInput, ElMessage } from 'element-plus';
import request from "@/utils/request.js";
export default {
  name: 'Login',
  components: {
    ElForm,
    ElFormItem,
    ElInput,
    ElMessage
  },
  data() {

      const validateConfirmPassword = (rule, value, callback) => {
        if (value !== this.form.password) {
          callback(new Error('两次密码不一致'));
          this.isButtonDisabled = true; // 密码不一致时禁用按钮
        } else {
          callback();
          this.isButtonDisabled = false; // 密码一致时启用按钮
        }
      };

    return {
      form: {
        username: '',
        password: '',
        confirm: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
        ],
        confirm: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur'}
        ]
      },
      formRef: null,
      isButtonDisabled: true
    }
  },
  methods: {
    reset() {
        request.post("http://localhost:8080/user/forget?username="+ this.form.username+"&password="+this.form.password+"&type=user").then(res => {
          if (res.code === 0) {
            ElMessage({
              message: "重置成功",
              type: 'success',
            });
            setTimeout(() => {
              this.$router.push('/login');
            }, 1000)
          }else {
            ElMessage({
              message: "重置失败",
              type: 'error',
            })
          }
        }).catch(error => {
          ElMessage.error('请求失败: ' + error.message);
        })
    }
  }
}
</script>


<style scoped>
.forgetContainer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>
