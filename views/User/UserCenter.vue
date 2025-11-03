<template>
  <HomeHeader></HomeHeader>
  <div style="width: 80%; margin: 40px auto;box-shadow: 0 0 15px 0 rgba(100, 100, 111, 0.2);background-color: white">
    <div style="display: flex">
      <div style="flex: 1">
        <el-image
            :src="avatar"
            alt=""
            fit="cover"
            :preview-src-list="avatarImage"
            style="border-radius: 50%;margin-top: 9px"
        >
        </el-image>
      </div>

      <div style="flex: 4">
        <div style="margin: 60px">
          <span style="font-weight: bold; font-size: 25px">{{ username }}</span>
          <!--男：&#xe632; 女：&#xe61f;-->
          <i class="iconfont" style="margin-left: 10px;color: red" v-if="sex === '女'">&#xe61f;</i>
          <i class="iconfont" style="margin-left: 10px;color: deepskyblue" v-else>&#xe632;</i>
        </div>
      </div>

      <div style="flex: 4">
        <div style="margin: 60px auto;text-align: center">
          <el-button type="primary" @click="showEditDialog">修改资料</el-button>
          <el-button type="primary" @click="showEditPasswordDialog">账号与安全</el-button>
        </div>
      </div>
    </div>
  </div>
  <div style="display: flex;width: 80%;margin: -20px auto;background-color: white">
        <div class="introduction" style="flex: 1;margin-right: 10px;height: 480px;box-shadow: 0 0 15px 0 rgba(100, 100, 111, 0.2)">
          <div style="text-align: center;padding-top: 20px">
            <span style="font-weight: bold; font-size: 20px;color: #666666">基本信息</span>
            <el-divider></el-divider>
          </div>
          <div style="margin: 30px;font-size: 15px">用户名：{{ username }}</div>
          <el-divider></el-divider>
          <div style="margin: 30px;font-size: 15px">性别：{{ sex }}</div>
          <el-divider></el-divider>
          <div style="margin: 30px;font-size: 15px">手机号：{{ phone }}</div>
          <el-divider></el-divider>
          <div style="margin: 30px;font-size: 15px">个人邮箱：{{ email }}</div>
          <el-divider></el-divider>
          <div style="margin: 30px;font-size: 15px">地址：{{ address }}</div>
        </div>
        <div style="flex: 2;margin-left: 10px;box-shadow: 0 0 15px 0 rgba(100, 100, 111, 0.2)">
          <el-menu mode="horizontal" default-active="1" @select="handleSelect" :ellipsis="false" style="display: flex">
            <el-menu-item index="1" style="flex: 1">
              <span style="font-size: 18px;color: #222226">课程</span>
            </el-menu-item>
            <el-menu-item index="2" style="flex: 1">
              <span style="font-size: 18px;color: #222226">文章</span>
            </el-menu-item>
            <el-menu-item index="3" style="flex: 1">
              <span style="font-size: 18px;color: #222226">收藏</span>
            </el-menu-item>
            <el-menu-item index="4" style="flex: 1">
              <span style="font-size: 18px;color: #222226">评论</span>
            </el-menu-item>
            <el-menu-item index="5" style="flex: 1">
              <span style="font-size: 18px;color: #222226">积分</span>
            </el-menu-item>
            <el-menu-item index="6" style="flex: 1">
              <span style="font-size: 18px;color: #222226">历史</span>
            </el-menu-item>
          </el-menu>
          <div v-if="selectedMenu === '1'" style="padding: 20px">
            <span>这是课程部分</span>
          </div>
          <div v-else-if="selectedMenu === '2'" style="padding: 20px">
            <span>这是文章部分</span>
          </div>
          <div v-else-if="selectedMenu === '3'" style="padding: 20px">
            <span>这是收藏部分</span>
          </div>
          <div v-else-if="selectedMenu === '4'" style="padding: 20px">
            <span>这是评论部分</span>
          </div>
          <div v-else-if="selectedMenu === '5'" style="padding: 20px">
            <div>
              <el-calendar v-model="dateValue" :first-day-of-week="1" :week-number="true">

              </el-calendar>
            </div>
          </div>
          <div v-else-if="selectedMenu === '6'" style="padding: 20px">
            <span>这是历史部分</span>
          </div>
        </div>
    </div>
  <HomeFooter style="margin-top: 40px"></HomeFooter>
  <el-dialog title="修改资料" v-model="editDialogVisible" width="600px" align-center>
    <el-form :model="userInfo" label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="userInfo.username"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio v-model="userInfo.sex" label="男">男</el-radio>
        <el-radio v-model="userInfo.sex" label="女">女</el-radio>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="userInfo.phone"></el-input>
      </el-form-item>
      <el-form-item label="个人邮箱">
        <el-input v-model="userInfo.email"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="userInfo.address"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUser">确 定</el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog title="修改密码" v-model="editPasswordDialogVisible" width="600px" align-center>
    <el-form :model="editUserPassword" label-width="80px" ref="editPasswordFormRef" :rules="rules">
      <el-form-item label="旧密码" prop="password">
        <el-input v-model="editUserPassword.password" show-password type="password"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="editUserPassword.newPassword" show-password type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="newPassword2">
        <el-input v-model="editUserPassword.newPassword2" show-password type="password"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editPasswordDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editPassword">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeFooter from "@/components/home/HomeFooter.vue";
import avatar from '@/assets/images/avatar.jpg';
import {mapMutations, mapState} from "vuex";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";

export default {
  components: { HomeHeader, HomeFooter },
  data() {
    return {
      avatar,
      avatarImage: [avatar], // 预览图片列表
      editDialogVisible: false,
      editPasswordDialogVisible: false,
      userInfo: {
        username: this.username,
        sex: this.sex,
        phone: this.phone,
        email: this.email,
        address: this.address,
        id: this.id
      },
      editUserPassword: {
        password: '',
        newPassword: '',
        newPassword2: ''
      },
      rules: {
        password: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        newPassword2: [
          { required: true, validator: (rule, value, callback) => {
            if (value !== this.editUserPassword.newPassword) {
              callback(new Error('两次密码不一致'));
            } else {
              callback();
            }
          }, trigger: 'blur' }
        ]
      },
      editUserPasswordFormRef: null,
      selectedMenu: '1',
      dateValue: new Date(),
    };
  },
  computed: {
    ...mapState(['username', 'sex', 'phone', 'email', 'address','id']),
  },
  methods: {
    ...mapMutations(['setUserName', 'setSex', 'setPhone', 'setEmail', 'setAddress','setId']),
    showEditDialog() {
      this.userInfo={
        username: this.username,
        sex: this.sex,
        phone: this.phone,
        email: this.email,
        address: this.address,
        id: this.id
      }
      this.editDialogVisible = true;
    },
    closeEditUserDialog() {
      this.editDialogVisible = false;
    },
    editUser() {
      request.post("http://localhost:8080/user/editUser",null,{
        params: {
          username: this.userInfo.username,
          sex: this.userInfo.sex,
          phone: this.userInfo.phone,
          email: this.userInfo.email,
          address: this.userInfo.address,
          id: this.userInfo.id
        }
      }).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "修改成功",
            type: 'success',
          });
          this.closeEditUserDialog();
          this.setUserName(this.userInfo.username);
          this.setSex(this.userInfo.sex);
          this.setPhone(this.userInfo.phone);
          this.setEmail(this.userInfo.email);
          this.setAddress(this.userInfo.address);
        }else {
          ElMessage({
            message: "修改失败",
            type: 'error',
          })
        }
      }).catch(error => {
        ElMessage.error('请求失败:'+ error.message)
      });
    },
    showEditPasswordDialog() {
      this.editPasswordDialogVisible = true;
    },
    closeEditPasswordDialog() {
      this.editPasswordDialogVisible = false;
    },
    editPassword() {
      this.$refs.editPasswordFormRef.validate((valid) => {
        if (valid) {
          request.post("http://localhost:8080/user/editPassword",null,{
            params: {
              id: this.id,
              password: this.editUserPassword.password,
              newPassword: this.editUserPassword.newPassword
            }
          }).then(res => {
            if (res.code === 0) {
              ElMessage({
                message: "修改成功",
                type: 'success',
              });
              this.closeEditPasswordDialog();
              this.editUserPassword={
                password: '',
                newPassword: '',
                newPassword2: ''
              };
            } else {
              ElMessage.error(res.message)
            }
          }).catch(error => {
            ElMessage.error('请求失败: ' + error.message);
          });
        }
      });
    },
    handleSelect(item) {
      this.selectedMenu = item;
    },
  },
  mounted() {
  }
};
</script>

<style>
</style>
