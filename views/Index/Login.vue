<template>
  <div class="logContainer" style="height: 665px">
    <img src="@/assets/images/bg.jpg" alt="" style="position: absolute;height: 100%;width: 100%;z-index: -1;left: 0;top: 0">
    <div style="margin-top: 30px;width: 400px;border:1px solid #dcdfe6;height: 450px;border-radius: 10px;background-color: white;">
      <div style="width: 340px;height: 270px;margin: 40px auto">
        <div style="text-align: center;margin-bottom: 20px;font-size: 20px;color: #333333;width: 340px">欢迎登录在线学习平台</div>
        <el-menu
            mode="horizontal"
            @select="handleSelect"
            :ellipsis="false"
            default-active="1"
        >
          <el-menu-item index="1" style="width: 170px;">
            <span style="font-size: 15px;">账号密码登录</span>
          </el-menu-item>
          <el-menu-item index="2" style="width: 170px;">
            <span style="font-size: 15px">手机号登录</span>
          </el-menu-item>
        </el-menu>
        <div v-if="loginType === 'account'">
          <el-form :model="loginForm" :rules="rules" ref="formRef" style="margin-top: 20px">
          <el-form-item prop="username">
            <el-input placeholder="请输入账号" v-model="loginForm.username" clearable>
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                placeholder="请输入密码"
                show-password
                v-model="loginForm.password"
                type="password"
            >
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="role">
            <el-select v-model="loginForm.role" placeholder="请选择角色">
              <el-option label="用户" value="user"></el-option>
              <el-option label="管理员" value="admin"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button :plain="true" type="primary" style="width: 100%;text-align: center" @click="login">登录</el-button>
          </el-form-item>
          <el-form-item prop="check" style="margin-top: -15px">
              <el-checkbox v-model="loginForm.check" class="custom-checkbox">
                <div style="font-size: 13px">我已阅读并同意
                  <a href="/privacy" target="_blank">《服务条款》</a>
                和
                  <a href="/privacy" target="_blank">《隐私政策》</a>
              </div>
              </el-checkbox>
          </el-form-item>

          <div style="display: flex;margin-top: 15px">
            <div style="flex: 1;text-align: left">
              <a href="/forget">忘记密码?</a>
            </div>
            <div style="flex: 1;text-align: right">
              还没有账号?
              <router-link to="/register">注册</router-link>
            </div>
          </div>
        </el-form>
        </div>
        <div v-else>
          <el-form :model="loginForm2" :rules="rules" ref="formRef" style="margin-top: 20px">
            <el-form-item prop="phone">
              <el-input placeholder="请输入手机号" v-model="loginForm2.phone" clearable>
                <template #prefix>
                  <el-icon><Iphone /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                  placeholder="请输入密码"
                  show-password
                  v-model="loginForm2.password"
                  type="password"
              >
                <template #prefix>
                  <el-icon><Lock /></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="captcha" style="display: flex">
              <el-input placeholder="请输入验证码" v-model="loginForm2.captcha" clearable style="flex: 1.5;height: 40px"></el-input>
              <img :src="captchaImage" alt="" style="margin-right: -10px;flex: 1" draggable="false">
              <div style="display: inline-block;margin-left: 10px;font-size: 13px;color: #999999;flex: 1">看不清？
                <span style="color: #409EFF;cursor: pointer" @click="getCaptcha">换一张</span>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button :plain="true" type="primary" style="width: 100%;text-align: center" @click="phoneLogin">登录</el-button>
            </el-form-item>
            <el-form-item style="margin-top: -10px">
              <el-checkbox v-model="loginForm2.remember" label="记住密码">记住密码</el-checkbox>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import request from "@/utils/request.js";
import {mapMutations} from "vuex";
export default {
  name: 'Login',
  components: {
    ElForm,
    ElFormItem,
    ElInput,
    ElSelect,
    ElOption,
    ElMessage
  },

  data() {
    return {
      loginForm: {
        username: '',
        password: '',
        role: '',
        check: false,
      },
      loginForm2: {
        phone: '',
        password: '',
        captcha: '',
        remember: false
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'blur' }
        ],
        check: [
          { required: true, validator: (rule, value, callback) => {
              if (!value) {
                callback(new Error(''));
              } else {
                callback();
              }
            }, trigger: 'change' }
        ],
        captcha: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      },
      formRef: null,
      loginType: 'account',
      captchaImage: ''
    };
  },
  methods: {
    handleSelect(key) {
      this.loginType = key === '1' ? 'account' : 'phone';
    },
    ...mapMutations(['setUserName', 'setToken', 'setSex', 'setPhone', 'setEmail', 'setAddress', 'setId','setIsLogged','setRole']),
    login() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          request.post("http://localhost:8080/user/login",null,{
            params: {
              username: this.loginForm.username,
              password: this.loginForm.password,
              type: this.loginForm.role
            },
          }).then(res => {
            if (res.code === 0) {
                ElMessage({
                  message: "登录成功",
                  type: 'success',
                });
                this.setUserName(this.loginForm.username);
                this.setId(res.data.id);
                this.setSex(res.data.sex);
                this.setPhone(res.data.phone);
                this.setEmail(res.data.email);
                this.setAddress(res.data.address);
                this.setToken(res.data.token);
                this.setIsLogged(true);
                this.setRole(this.loginForm.role);
                setTimeout(() => {
                  if (this.loginForm.role === 'user'){
                    this.$router.push('/home');
                  } else {
                    this.$router.push('/admin');
                  }
                }, 1000);
            } else {
              ElMessage.error(res.message)
            }
          }).catch(error => {
            ElMessage.error('请求失败: ' + error.message);
          });
        } else if(this.loginForm.username === ''){
          ElMessage.error('请输入用户名');
        } else if(this.loginForm.password === ''){
          ElMessage.error('请输入密码');
        } else if(this.loginForm.role === ''){
          ElMessage.error('请选择角色');
        } else if(this.loginForm.check === false){
          ElMessage.error('请勾选同意协议');
        }
      });
    },
    phoneLogin() {
      this.$refs.formRef.validate((valid) => {
        if (valid){
          request.post("http://localhost:8080/verifyCaptcha",null,{
            params: {
              captcha: this.loginForm2.captcha
            },
            withCredentials: true
          }).then(res => {
            if (res.code === 0) {
              request.post("http://localhost:8080/user/phoneLogin",null,{
                params: {
                  phone: this.loginForm2.phone,
                  password: this.loginForm2.password
                },
              }).then(res => {
                if (res.code === 0) {
                  ElMessage({
                    message: "登录成功",
                    type: 'success',
                  });
                  console.log(res);
                  this.setUserName(res.data.username);
                  this.setToken(res.data.token);
                  this.setId(res.data.id);
                  this.setIsLogged(true);
                  this.setSex(res.data.sex);
                  this.setPhone(res.data.phone);
                  this.setEmail(res.data.email);
                  this.setAddress(res.data.address);
                  this.setRole(res.data.type);
                  setTimeout(() => {
                    if (res.data.type === 'user'){
                      this.$router.push('/home');
                    } else {
                      this.$router.push('/admin');
                    }
                  }, 1000);
              } else {
                ElMessage.error(res.message)
              }
              }).catch(error => {
                ElMessage.error('请求失败: ' + error.message);
              });
            }else {
              ElMessage.error("验证码错误");
            }
          }).catch(error => {
            ElMessage.error('请求失败: ' + error.message);
          });
        }else if(this.loginForm2.phone === ''){
          ElMessage.error('请输入手机号');
        }else if(this.loginForm2.password === ''){
          ElMessage.error('请输入密码');
        }else if(this.loginForm2.captcha === ''){
          ElMessage.error('请输入验证码');
        }
      });
    },
    getCaptcha() {
      request.get("http://localhost:8080/captcha",{
        responseType: 'blob',
        withCredentials: true,
        }).then(res => {
          this.captchaImage = URL.createObjectURL(res);
      })
    },
  },
  mounted() {
    this.getCaptcha();
  }
}
</script>

<style scoped>
.logContainer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
a {
  color: #409EFF;
  text-decoration: none;
}
a:active {
  color: #409EFF;
}
</style>
