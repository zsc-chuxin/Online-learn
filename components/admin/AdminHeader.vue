<template>
  <div class="header" style="display:flex;">
    <div class="logo" style="margin-left: 20px  ">
      <img src="../../assets/images/logo.png" alt="Logo"/>
      <span class="logo-font">后台管理系统</span>
    </div>
    <div class="admin" style="display: flex;width: 150px;height: 60px">
      <el-dropdown>
        <div class="el-dropdown-link" style="justify-content: center;width: 150px">
          <img src="../../assets/images/admin.jpg" alt="" style="width: 40px;height: 40px;border-radius: 50%">
          <span class="user-name" style="color: white;font-size: 15px;margin-left: 10px;margin-right: 20px">{{ username }}</span>
          <el-icon class="el-icon--right" color="#ffffff"><arrow-down /></el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="showEditAdminPasswordDialog">修改密码</el-dropdown-item>
            <el-dropdown-item @click="showLogoutDialog">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>

  <el-dialog
      v-model="editAdminPasswordDialogVisible"
      title="修改密码"
      width="30%"
      align-center
  >
    <el-form :model="editAdminPasswordForm" :rules="rules" ref="editAdminPasswordForm" label-width="80px">
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input v-model="editAdminPasswordForm.oldPassword" show-password></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="editAdminPasswordForm.newPassword" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="editAdminPasswordForm.confirmPassword" show-password></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
        <span class="dialog-footer">
          <el-button @click="editAdminPasswordDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="editAdminPassword">确定</el-button>
        </span>
    </template>
  </el-dialog>
  <el-dialog
      v-model="logoutDialogVisible"
      title="提示"
      width="30%"
      align-center
  >
    <span>确定要退出登录吗？</span>
    <template #footer>
        <span class="dialog-footer">
          <el-button @click="logoutDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="confirmLogout">确定</el-button>
        </span>
    </template>
  </el-dialog>
</template>

<script lang="ts">
import {ArrowDown, HomeFilled} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import course from "@/views/Home/Course.vue";
import {mapMutations, mapState} from "vuex";
import request from "@/utils/request.js";

export default {
  components: {ArrowDown, HomeFilled,ElMessage},
  data() {
    return {
      logoutDialogVisible: false,
      editAdminPasswordDialogVisible: false,
      editAdminPasswordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      rules: {
        oldPassword: [
          { required: true, message: '请输入旧密码', trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { required: true, validator: (rule, value, callback) => {
              if (value !== this.editAdminPasswordForm.newPassword) {
                callback(new Error('两次密码不一致'));
              } else {
                callback();
              }
            }, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    course() {
      return course
    },
    ...mapState(['username', 'token','role','id'])
  },
  created() {
    if(!this.token){
      ElMessage({
        message: "请先登录",
        type: 'warning',
      });
      this.$router.push('/login');
    }else if(this.token && this.role === 'user'){
      ElMessage({
        message:"您没有权限访问此页面",
        type: 'warning',
      });
      this.$router.push('/home');
    }else if (this.token && this.role === 'admin'){
      this.checkTokenExpiration();
    }
  },
  methods: {
    showLogoutDialog() {
      this.logoutDialogVisible = true;
    },
    confirmLogout() {
      ElMessage({
        message: "退出登录成功",
        type: 'success',
      });
      setTimeout(() => {
        this.$router.push('/login');
      }, 1000);
      this.clearData();
    },
    checkTokenExpiration(){
      request.post("http://localhost:8080/token/validateToken?token="+this.token).then(res => {
        if (res === false){
          ElMessage({
            message: "登录已过期，请重新登录",
            type: 'warning',
          });
          this.clearData();
          this.$router.push('/home');
        }
      })
    },
    showEditAdminPasswordDialog() {
      this.editAdminPasswordDialogVisible = true;
    },
    editAdminPassword() {
      this.$refs.editAdminPasswordForm.validate((valid) => {
        if (valid) {
          request.post("http://localhost:8080/admin/updateAdminPassword",null,{
            params:{
              username: this.username,
              oldPassword: this.editAdminPasswordForm.oldPassword,
              newPassword: this.editAdminPasswordForm.newPassword
            }
          }).then(res => {
            console.log(res);
            if (res.code === 0){
              ElMessage({
                message: "修改密码成功",
                type: 'success',
              });
              this.editAdminPasswordDialogVisible = false;
              this.editAdminPasswordForm = {
                oldPassword: '',
                newPassword: '',
                confirmPassword: ''
              };
            }else {
              ElMessage.error(res.message);
            }
          })
        } else {
          ElMessage.error("请填写完整的信息")
        }
      });
    },
    ...mapMutations(['clearData']),
  }
}
</script>

<style scoped>
body{
  margin: 0;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #545c64;
  height: 60px;
}

.logo {
  display: flex;
  align-items: center;
  color: #fff;
}

.logo img {
  height: 40px;
}

.logo-font {
  font-size: 20px;
  font-weight: bold;
  margin-left: 10px;
}
.el-dropdown-link {
  display: flex;
  align-items: center;
  outline: none;
}
.emi :active{
  background-color: #e6f1ff;
  border-right: 3px solid;
}
</style>