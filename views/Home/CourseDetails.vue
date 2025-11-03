<template>
  <HomeHeader></HomeHeader>
  <div class="header" style="margin: 10px auto">
    <el-page-header @back="goBack">
      <template #title>
        <span style="font-size: 15px;color: #666666" @click="goBack">返回上一级</span>
      </template>
      <template #content>
        <span style="font-size: 20px;font-weight: bold">课程详情</span>
      </template>
    </el-page-header>
    <div style="margin-top: -15px">
      <el-divider border-style="dashed"></el-divider>
    </div>
  </div>
  <div style="width: 80%; margin: 20px auto">
    <div style="display: flex">
      <div style="flex: 5; height: 400px; text-align: center; margin-top: 20px">
        <el-image :src="imageUrl + this.courseimage" style="width: 480px;height: 300px"></el-image>
        <div style="display: flex; text-align: center; width: 550px;margin:20px auto">
          <span class="like" style="flex: 1" @click="like" @mouseover="isLikeHovered = true" @mouseleave="isLikeHovered = false">
            <i class="iconfont" :style="{ fontSize: '25px', color: isLikeHovered ? 'deepskyblue' : 'gray' }">&#xe600;</i>
            <span :style="{ color: isLikeHovered ? 'deepskyblue' : 'black' }" style="margin-left: 5px">{{ likeCount }}</span>
          </span>
          <span class="collect" style="flex: 1" @click="collect" @mouseover="isCollectHovered = true" @mouseleave="isCollectHovered = false">
            <i class="iconfont" :style="{ fontSize: '25px', color: isCollectHovered ? 'deepskyblue' : 'gray' }">&#xe604;</i>
            <span :style="{ color: isCollectHovered ? 'deepskyblue' : 'black' }" style="margin-left: 5px">{{ collectCount }}</span>
          </span>
          <span class="purchase" style="flex: 1" @click="purchase" @mouseover="isPurchaseHovered = true" @mouseleave="isPurchaseHovered = false">
            <i class="iconfont" :style="{ fontSize: '25px', color: isPurchaseHovered ? 'deepskyblue' : 'gray' }">&#xe603;</i>
            <span :style="{ color: isPurchaseHovered ? 'deepskyblue' : 'black' }" style="margin-left: 5px">{{ purchaseCount }}</span>
          </span>
          <span class="share" style="flex: 1" @click="share" @mouseover="isShareHovered = true" @mouseleave="isShareHovered = false">
            <i class="iconfont" :style="{ fontSize: '25px', color: isShareHovered ? 'deepskyblue' : 'gray' }">&#xe627;</i>
            <span :style="{ color: isShareHovered ? 'deepskyblue' : 'black' }" style="margin-left: 5px">{{ shareCount }}</span>
          </span>
        </div>
      </div>
      <div style="flex: 3; height: 400px;margin-top: 20px">
        <span style="font-size: 25px;font-weight: bold">{{ coursename }}</span>
        <div style="margin-top: 20px">
          <span style="font-size: 20px;font-weight: bold">课程介绍:</span>
        </div>
        <div style="margin-top: 10px">
          <span style="font-size: 15px">{{ introduction }}</span>
        </div>
        <div style="margin-top: 100px">
          <span style="font-size: 20px;font-weight: bold">课程价格:</span>
          <span v-if="courseprice != 0" style="font-size: 20px;margin-left: 10px;color: #ff7a3e;font-weight: bold">￥ {{ courseprice }}</span>
          <span v-else style="font-size: 20px;margin-left: 10px;color: #00CC7E;font-weight: bold">免费</span>
        </div>
      </div>
    </div>
    <div style="width: 400px;margin:auto;text-align: center">
      <el-button v-if="this.isLogged === true && isBuy === true" type="success" size="large" style="margin-right: 30px" @click="play">立即观看</el-button>
      <el-button v-else type="primary" size="large" style="margin-right: 30px" @click="play">免费试听</el-button>
      <el-button v-if="this.isLogged === true && isBuy === true" type="success" size="large" style="margin-left: 30px" disabled>已购买</el-button>
      <el-button v-else type="primary" size="large" style="margin-left: 30px" @click="buyCourse">立即购买</el-button>
    </div>
  </div>
  <el-dialog
      v-model="loginDialogVisible"
      title="登录"
      width="35%"
      align-center
      custom-class="dialog-content"
  >
    <div>
      <el-menu
          mode="horizontal"
          :ellipsis="false"
          default-active="1"
          @select="handleSelect"
      >
        <el-menu-item index="1" style="width: 170px;">
          <span style="font-size: 15px;">账号密码登录</span>
        </el-menu-item>
        <el-menu-item index="2" style="width: 170px;">
          <span style="font-size: 15px">手机号登录</span>
        </el-menu-item>
        <el-menu-item index="3" style="width: 170px;">
          <span style="font-size: 15px">邮箱登录</span>
        </el-menu-item>
      </el-menu>
<!--      账号密码登录-->
      <div v-if="loginType === 'account'" class="login-form" style="margin-top: 30px">
        <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" style="width: 80%;margin: auto" size="large">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" placeholder="请输入用户名" clearable>
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" placeholder="请输入密码" type="password" show-password>
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="check" style="margin-top: -10px">
            <el-checkbox v-model="loginForm.check">
                <span style="font-size: 13px">记住密码
                  <!--                <router-link to="">服务条款</router-link>-->
                  <!--                和-->
                  <!--                <router-link to="">隐私协议</router-link>-->
              </span>
            </el-checkbox>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
<!--      手机号登录-->
      <div v-if="loginType === 'phone'" class="login-form" style="margin-top: 30px">
        <el-form ref="loginFormRef" :model="phoneLoginForm" :rules="phoneLoginRules" style="width: 80%;margin: auto" size="large">
          <el-form-item prop="phone">
            <el-input v-model="phoneLoginForm.phone" placeholder="请输入手机号" clearable>
              <template #prefix>
                <el-icon><Phone /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="phoneLoginForm.password" placeholder="请输入密码" type="password" show-password>
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="captcha" style="display: flex;">
            <el-input v-model="phoneLoginForm.captcha" style="flex: 2" placeholder="请输入验证码" clearable></el-input>
            <img :src="captchaImage" alt="" style="margin-right: -10px;flex: 1" draggable="false">
            <div style="display: inline-block;margin-left: 10px;font-size: 13px;color: #999999;flex: 1">看不清？
              <span style="color: #409EFF;cursor: pointer" @click="getCaptcha">换一张</span>
            </div>
          </el-form-item>
          <el-form-item prop="check" style="margin-top: -10px">
            <el-checkbox v-model="phoneLoginForm.check">
                <span style="font-size: 13px">记住密码
                  <!--                <router-link to="">服务条款</router-link>-->
                  <!--                和-->
                  <!--                <router-link to="">隐私协议</router-link>-->
              </span>
            </el-checkbox>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="phoneLogin">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
<!--      邮箱登录-->
      <div v-if="loginType === 'email'" class="login-form" style="margin-top: 30px">
        <el-form ref="loginFormRef" :model="emailLoginForm" :rules="emailLoginRules" style="width: 80%;margin: auto" size="large">
          <el-form-item prop="email">
            <el-autocomplete
                v-model="emailLoginForm.email"
                :fetch-suggestions="querySearch"
                placeholder="请输入邮箱"
                clearable
                debounce="10"
                @blur="addEmailSuffix">
              <template #prefix>
                <el-icon><Message /></el-icon>
              </template>
            </el-autocomplete>
          </el-form-item>
          <el-form-item prop="captcha" style="display: flex">
            <el-input v-model="emailLoginForm.captcha" style="width: 60%" placeholder="请输入验证码" clearable></el-input>
            <el-button type="success" style="width: 25%;flex-grow: 1;margin-left: 20px" @click="getEmailCaptcha" :disabled="isSend">{{ buttonText }}</el-button>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="emailLogin">登录</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div style="width: 80%; margin: auto; display: flex; justify-content: space-between; align-items: center;">
        <span class="forget">忘记密码?</span>
        <span class="register">立即注册</span>
      </div>
    </div>
  </el-dialog>
  <el-dialog title="购买确认" v-model="sliderDialogVisible" align-center width="355px" @open="resetSlider">
    <div>
      <div
          class="canvas-container"
          style="width: 320px;height: 155px;position: relative;"
          v-loading="loading"
          element-loading-text="获取验证码中..."
          element-loading-background="white"
          v-if="!isSuccess"
      >
        <img :src="canvasImage" alt="" class="canvas-image">
        <img :src="blockImage" alt="" class="block-image" :style="blockStyle">
      </div>
      <div v-else class="success-container" style="width: 320px;height: 155px">
        <el-result title="验证成功" icon="success"></el-result>
      </div>
      <div style="margin-top: 20px">
        <el-slider
            v-model="X"
            :min="0"
            :max="260"
            :step="1"
            :show-tooltip="false"
            :disabled="isSliderDisabled"
            @input="handleSliderInput"
            @change="handleSliderChange"
            style="width: 95%;margin: auto"
        ></el-slider>
      </div>
    </div>
    <div style="display: flex;justify-content: flex-end;position: absolute;right: 25px;top: 50px;">
      <el-icon class="refresh" size="25px" style="cursor: pointer;margin-top: 10px" @click="getSliderCaptcha"><RefreshRight /></el-icon>
    </div>

  </el-dialog>
  <HomeFooter></HomeFooter>
</template>

<script lang="ts">
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeFooter from "@/components/home/HomeFooter.vue";
import request from "@/utils/request.js";
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import {ElMessage} from "element-plus";
import {mapMutations, mapState} from "vuex";
import {Message} from "@element-plus/icons-vue";
export default {
  components: {Message, HomeHeader, HomeFooter},
  setup() {
    const route = useRoute();
    const coursename = ref("");
    onMounted(() => {
      const paramsCourseName = route.params.coursename;
      if (Array.isArray(paramsCourseName)) {
        coursename.value = paramsCourseName[0];
      } else {
        coursename.value = paramsCourseName || "";
      }
    });
    return {
      coursename
    };
  },
  data() {
    return {
      isLikeHovered: false,
      isCollectHovered: false,
      isPurchaseHovered: false,
      isShareHovered: false,
      likeCount: 0,
      collectCount: 0,
      purchaseCount: 0,
      shareCount: 0,
      introduction: '',
      courseprice: 0,
      isBuy: false,
      sliderDialogVisible: false,
      canvasImage: '',
      blockImage: '',
      blockX: 0,
      blockY: 0,
      X: 0,
      isSliderDisabled: false,
      loading: false,
      isSuccess: false,
      imageUrl: '/src/assets/images/courseImage/',
      courseimage: '',
      buyDate: '',
      loginDialogVisible: false,
      loginType: 'account',
      loginForm: {
        username: '',
        password: '',
        check: false
      },
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ]
      },
      captchaImage: '',
      phoneLoginForm: {
        phone: '',
        password: '',
        captcha: '',
        check: false
      },
      phoneLoginRules: {
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        captcha: [
          { required: true, message: '', trigger: 'change' },
        ]
      },
      emailLoginForm: {
        email: '',
        captcha: '',
      },
      emailLoginRules: {
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
        ],
        captcha: [
          { required: true, message: '', trigger: 'change' },
        ]
      },
      orderNumber: '',
      buttonText: '发送验证码',
      countdown: 60,
      isSend: false,
    }
  },
  methods: {
    like() {
      request.post("http://localhost:8080/courseDetails/increaseLike?coursename="+this.coursename).then(res => {
        if (res.code === 0) {
          ElMessage.success("点赞成功")
        }else {
          ElMessage.error("点赞失败")
        }
        this.getCourseDetails();
      })
    },
    collect() {
      request.post("http://localhost:8080/courseDetails/increaseCollect?coursename="+this.coursename).then(res => {
        if (res.code === 0) {
          ElMessage.success("收藏成功")
        }else {
          ElMessage.error("收藏失败")
        }
        this.getCourseDetails();
      })
    },
    purchase() {
      request.post("http://localhost:8080/courseDetails/increasePurchase?coursename="+this.coursename).then(res => {
        if (res.code === 0) {
          ElMessage.success("购买成功")
        }else {
          ElMessage.error("购买失败")
        }
        this.getCourseDetails();
      })
    },
    share() {
      request.post("http://localhost:8080/courseDetails/increaseShare?coursename="+this.coursename).then(res => {
        if (res.code === 0) {
          ElMessage.success("分享成功")
        }else {
          ElMessage.error("分享失败")
        }
        this.getCourseDetails();
      })
    },
    play(){
      if (this.isLogged === false){
        this.loginDialogVisible = true;
      }else {
        this.loginDialogVisible = false;
        window.open('/course/player','_blank');
      }
    },
    goBack() {
      this.$router.back();
    },
    getCourseDetails(){
      request.post("http://localhost:8080/user/findCourseDetails?coursename="+this.coursename).then(res => {
          this.likeCount = res.data.like;
          this.collectCount = res.data.collect;
          this.purchaseCount = res.data.purchase;
          this.shareCount = res.data.share;
          this.courseimage = res.data.courseimage;
          this.courseprice = res.data.courseprice;
      }).catch(error => {
          ElMessage.error('请求失败: ' + error.message);
      })
    },
    isBuyCourse(){
      request.post("http://localhost:8080/user/isBuyCourse", null, {
        params: {
          id: this.id,
          coursename: this.coursename,
        }
      }).then(res => {
        if (res.code === 0) {
          this.isBuy = true;
        }else {
          this.isBuy = false;
        }
      })
    },
    getSliderCaptcha(){
      this.isSuccess = false;
      this.loading = true;
      this.isSliderDisabled = true;
      const data = {
        captcha: 123456
      };
      request.post("http://localhost:8080/generateSliderCaptcha",data).then(res => {
        this.canvasImage = res.data.canvasSrc;
        this.blockImage = res.data.blockSrc;
        this.blockX = res.data.blockX;
        this.blockY = res.data.blockY;
        this.X = 0;
        this.isSliderDisabled = false;
      }).finally(()=>{
        setTimeout(()=>{
          this.loading = false;
        },1000)
      });
    },
    handleSliderInput(value){
      this.X = value;
    },
    handleSliderChange(value){
      this.isSliderDisabled = true;
      if (Math.abs(value-this.blockX) < 5){
        setTimeout(()=>{
          this.isSuccess = true;
          this.sliderDialogVisible = false;
          this.buyDate = new Date().toLocaleString();
          this.generateOrderNumber();
          request.post("http://localhost:8080/user/buyCourse", null, {
            params: {
              id: this.id,
              coursename: this.coursename,
              courseimage: this.courseimage,
              buyDate: this.buyDate,
              orderNumber: this.orderNumber
            }
          }).then(res => {
            if (res.code === 0) {
              ElMessage.success("购买成功");
              this.isBuy = true;
            } else {
              ElMessage.error("购买失败")
            }
          })
        },200);
      }else {
        ElMessage({
          message: `验证失败`,
          type: 'error',
        });
        setTimeout(()=>{
          this.getSliderCaptcha();
          this.X = 0;
        },750)
      }
    },
    resetSlider(){
      this.X = 0;
      this.getSliderCaptcha()
    },
    buyCourse(){
      if (this.isLogged === false){
        this.loginDialogVisible = true;
      }else {
        this.sliderDialogVisible = true;
      }
    },
    login() {
      this.$refs.loginFormRef.validate((valid) => {
        if (valid) {
          request.post("http://localhost:8080/user/login",null,{
            params: {
              username: this.loginForm.username,
              password: this.loginForm.password,
              type: 'user'
            }
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
              this.setRole('user');
              console.log(this.isLogged);
              window.location.reload();
            }
          }).catch(error => {
            ElMessage.error('请求失败: ' + error.message);
          });
          // 如果用户勾选了记住密码，将用户名和密码保存到 localStorage 中
          if (this.loginForm.check) {
            localStorage.setItem('savedUsername', this.loginForm.username);
            localStorage.setItem('savedPassword', this.loginForm.password);
          } else {
            // 如果用户没有勾选记住密码，从localStorage 中删除保存的用户名和密码
            localStorage.removeItem('savedUsername');
            localStorage.removeItem('savedPassword');
          }
          this.loginDialogVisible = false;
        }
      })
    },
    getCaptcha() {
      request.get("http://localhost:8080/captcha",{
        responseType: 'blob',
        withCredentials: true,
      }).then(res => {
        this.captchaImage = URL.createObjectURL(res);
      })
    },
    phoneLogin(){
      this.$refs.loginFormRef.validate((valid) => {
        if (valid){
          request.post("http://localhost:8080/verifyCaptcha",null,{
            params:{
              captcha: this.phoneLoginForm.captcha
            },
            withCredentials: true
          }).then(res => {
            if (res.code === 0){
              request.post("http://localhost:8080/user/phoneLogin",null,{
                params: {
                  phone: this.phoneLoginForm.phone,
                  password: this.phoneLoginForm.password,
                },
              }).then(res =>{
                if (res.code === 0){
                  ElMessage({
                    message: "登录成功",
                    type: 'success',
                  });
                  this.setUserName(res.data.username);
                  this.setId(res.data.id);
                  this.setSex(res.data.sex)
                  this.setPhone(res.data.phone);
                  this.setEmail(res.data.email);
                  this.setAddress(res.data.address);
                  this.setToken(res.data.token);
                  this.setIsLogged(true);
                  this.setRole('user');
                  setTimeout(()=>{
                    this.loginDialogVisible = false;
                    window.location.reload();
                  },1000)
                }else {
                  ElMessage.error(res.message);
                }
              }).catch(error => {
                ElMessage.error('请求失败: ' + error.message);
              });
            }else {
              ElMessage.error('验证码错误');
            }
          })
        }
      });
    },
    getCourseIntroduction(){
      request.post("http://localhost:8080/user/getCourseIntroduction", null, {
        params: {
          coursename: this.coursename
        }
      }).then(res => {
        this.introduction = res.data.introduction;
      })
    },
    generateOrderNumber(){
      const year = new Date().getFullYear();
      const month = new Date().getMonth() + 1;
      const day = new Date().getDate();
      const hour = new Date().getHours();
      const minute = new Date().getMinutes();
      const second = new Date().getSeconds();

      const newMonth = month >= 10 ? month : '0' + month;
      const newDay = day >= 10 ? day : '0' + day;
      const newHour = hour >= 10 ? hour : '0' + hour;
      const newMinute = minute >= 10 ? minute : '0' + minute;
      const newSecond = second >= 10 ? second : '0' + second;

       this.orderNumber = `${year}${newMonth}${newDay}${newHour}${newMinute}${newSecond}${this.id}`;
    },
    handleSelect(index){
      this.loginType = index === '1' ? 'account' : index === '2' ? 'phone' : 'email';
    },
    addEmailSuffix(){
      // 如果输入的是邮箱地址或者为空，不进行提示
      if (this.emailLoginForm.email !== '' && !this.emailLoginForm.email.includes('@')){
        this.emailLoginForm.email += '@qq.com';
      }
    },
    getEmailCaptcha(){
      if (this.emailLoginForm.email === ''){
        ElMessage.error('请输入邮箱');
      }else {
        let count = this.countdown;
        const timer = setInterval(() => {
          if (count === 0) {
            this.isSend = false;
            clearInterval(timer);
            this.buttonText = '发送验证码';
            count = this.countdown;
          } else {
            this.isSend = true;
            count--;
            this.buttonText = `${count}s后重新发送`;
          }
        }, 1000);
        request.post("http://localhost:8080/user/sendCodeMail", null, {
          params: {
            email: this.emailLoginForm.email
          },
          withCredentials: true
        }).then(res => {
          if (res.code === 0) {
            ElMessage.success('验证码发送成功,请注意查收');
          } else {
            ElMessage.error('验证码发送失败,请稍后重试');
          }
        })
      }
    },
    emailLogin(){
      this.$refs.loginFormRef.validate((valid) => {
        if (valid) {
            request.post("http://localhost:8080/user/valifyCode",null,{
              params: {
                captcha: this.emailLoginForm.captcha
              },
              withCredentials: true
            }).then(res => {
              if (res.code === 0) {
                request.post("http://localhost:8080/user/emailLogin",null,{
                  params: {
                    email: this.emailLoginForm.email,
                  },
                }).then(res =>{
                  if (res.code === 0){
                    ElMessage({
                      message: "登录成功",
                      type: 'success',
                    });
                    this.setUserName(res.data.username);
                    this.setId(res.data.id);
                    this.setSex(res.data.sex);
                    this.setPhone(res.data.phone);
                    this.setEmail(res.data.email);
                    this.setAddress(res.data.address);
                    this.setToken(res.data.token);
                    this.setIsLogged(true);
                    this.setRole('user');
                    setTimeout(()=>{
                      this.loginDialogVisible = false;
                      window.location.reload();
                    },1000)
                  }else {
                    ElMessage.error(res.message);
                  }
                }).catch(error => {
                  ElMessage.error('请求失败: ' + error.message);
                });
              }else {
                ElMessage.error(res.message);
              }
            })
        }else if (this.emailLoginForm.email === ''){
          ElMessage.error('请输入邮箱');
        }else {
          ElMessage.error('请输入验证码');
        }
      });

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
    ...mapMutations(['setUserName', 'setId', 'setSex', 'setPhone', 'setEmail', 'setAddress', 'setToken', 'setIsLogged','setRole']),
  },
  mounted() {
    this.getCourseDetails();
    this.isBuyCourse();
    this.getCaptcha();
    this.getSliderCaptcha();
    this.getCourseIntroduction();
    // 从 localStorage 中读取用户名和密码
    const savedUsername = localStorage.getItem('savedUsername');
    const savedPassword = localStorage.getItem('savedPassword');
    if (savedUsername && savedPassword) {
      this.loginForm.username = savedUsername;
      this.loginForm.password = savedPassword;
      this.loginForm.check = true;
    }
  },
  computed: {
    ...mapState(['username', 'token', 'isLogged', 'id']),
    blockStyle(){
      return `left:${this.X}px;top:${this.blockY}px;`
    },
  },
};
</script>

<style>
.like:hover,
.collect:hover,
.purchase:hover,
.share:hover {
  cursor: pointer;
}
.block-image{
  position: absolute;
}
.refresh:hover{
  color: #409EFF;
}
.el-slider__button{
  width: 31px;
  height: 31px;
  margin-top: 14.9px;
  border-radius: 0;
  pointer-events: auto;
}
.el-slider__runway{
  width: 30px; /* 滑块按钮的宽度 */
  height: 30px; /* 滑块按钮的高度 */
  margin-top: 20px;
  background-color: gray; /* 滑块按钮的颜色 */
  transform: translateY(-50%);
  transition: all 0.3s ease;
  pointer-events: none;
}
.el-slider__bar {
  background-color: #409eff; /* 已滑动部分的颜色 */
  transform: translateY(-50%);
  margin-top: 15px;
  height: 30px; /* 滑块路径的高度 */
}
.forget:hover {
  cursor: pointer;
  color: #000000;
}
.register:hover {
  cursor: pointer;
  color: #000000;
}
</style>
