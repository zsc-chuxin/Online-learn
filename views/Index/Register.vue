<template>
  <div class="registerContainer" style="height: 665px">
    <img src="@/assets/images/bg.jpg" alt="" style="position: absolute;height: 100%;width: 100%;z-index: -1;left: 0;top: 0">

    <div style="margin-top: 30px;width: 400px;border:1px solid #dcdfe6;height: 350px;border-radius: 10px;background-color: white">

      <div style="width: 340px;height: 270px;margin: 30px auto">

        <div style="text-align: center;margin-bottom: 20px;font-size: 25px;color: #333333;width: 340px">用户注册</div>
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

          <el-button :plain="true" type="primary" style="width: 100%;text-align: center" @click="register" :disabled="isButtonDisabled">注册</el-button>
          <div style="flex: 1;text-align: left;margin-top: 20px">
            已有账号?
            <router-link to="/login">登录</router-link>
          </div>
        </el-form>
      </div>

    </div>
  </div>
</template>

<script>import { ref } from 'vue';
import { ElForm, ElFormItem, ElInput, ElMessage } from 'element-plus';
import { userRegisterService } from '@/api/user.js';
import { useRouter } from 'vue-router';
import request from "@/utils/request.js";
import {mapMutations} from "vuex";


export default {
  components: {
    ElForm,
    ElFormItem,
    ElInput
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
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirm: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          {  validator: validateConfirmPassword, trigger: 'blur'}
        ]
      },
      formRef: null,
      isButtonDisabled: true
    };
  },
  methods: {
    ...mapMutations(['setUserName', 'setToken']),
    register () {
      this.$refs.formRef.validate((valid) => {
        if(valid) {
          request.post("http://localhost:8080/user/register?username=" + this.form.username + "&password=" + this.form.password).then(res => {
            if (res.code === 0) {
              ElMessage({
                message: "注册成功",
                type: 'success',
              });
              this.setUserName(this.form.username);
              this.setToken(res.data.token);
              setTimeout(() => {
                this.$router.push('/home');
              }, 1000)
            } else {
              ElMessage({
                message: "注册失败",
                type: 'error',
              });
            }
          }).catch(error => {
            ElMessage.error('请求失败: ' + error.message);
          })
        }else {
          ElMessage.error('请先输入账号和密码');
        }
      });

    }
  }
}
</script>


<style scoped>
.registerContainer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style>
