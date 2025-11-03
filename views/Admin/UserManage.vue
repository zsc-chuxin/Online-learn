
模板
<template>
  <AdminHeader></AdminHeader>
  <div class="main" style="display: flex">
    <AdminMenu></AdminMenu>
    <div class="main-right" style="width: 100%;height: 800px">
      <div class="search" style="height: 50px;margin: 10px auto 10px 10px;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .1);background-color: white">
        <el-input v-model="input" style="width: 220px;padding-left: 10px;padding-top: 10px" placeholder="请输入用户名" @input="searchUsers"></el-input>
        <el-button type="primary" icon="search" style="margin-left: 10px;margin-top: 10px" @click="searchUsers">查询</el-button>
      </div>
      <div class="operation" style="height: 50px;margin: 10px auto 10px 10px;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .1);background-color: white">
        <el-button type="primary" icon="plus" style="margin-left: 10px;margin-top: 10px" @click="showAddUserDialog()">添加用户</el-button>
        <el-button type="danger" icon="delete" style="margin-left: 10px;margin-top: 10px" @click="showBatchDeleteUserDialog()">批量删除</el-button>
      </div>
      <div class="table" style="height: 400px;margin: 10px auto 10px 10px;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .1);background-color: white">
        <el-table
            :data="tableData && tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
            stripe
            style="width: 99%"
            size="small"
            row-key="username"
            @selection-change="handleSelectionChange"
        >
          <el-table-column :reserve-selection="true" type="selection" width="50" />
          <el-table-column  label="id" sortable prop="id" width="80" />
          <el-table-column  label="用户名" prop="username" width="150" />
          <el-table-column  label="用户角色" prop="type" width="150" />
          <el-table-column  label="性别" prop="sex" width="150" />
          <el-table-column  label="归属地" prop="address" width="150" />
          <el-table-column  label="邮箱" prop="email" width="150" />
          <el-table-column  label="手机号" prop="phone" width="150"/>
          <el-table-column  label="操作" prop="operation" align="center" width="200">
            <template #default="scope">
              <el-button type="primary" icon="edit" @click="showEditUserDialog(scope.row)">编辑</el-button>
              <el-button type="danger" icon="delete" @click="showDeleteUserDialog(scope.row.username)">删除</el-button>
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
              style="text-align: center">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
  <el-dialog title="添加用户" v-model="addUserDialogVisible" width="30%" align-center>
    <el-form label-width="80px" ref="formRef" :model="newUser" :rules="rules">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="newUser.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="newUser.password" type="password" show-password placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="newUser.sex" placeholder="请选择性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="newUser.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="归属地" prop="address">
        <el-input v-model="newUser.address" placeholder="请输入归属地"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer" >
        <el-button @click="closeAddUserDialog" style="margin-left: 130px">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="批量删除用户" v-model="batchDeleteUserDialogVisible" width="30%" align-center>
    <span>确认删除选中的 {{ selectedUsers.length }} 个用户吗？</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeBatchDeleteUserDialog" style="margin-left: 80px">取 消</el-button>
      <el-button type="primary" @click="batchDeleteUsers">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="删除用户" v-model="deleteUserDialogVisible" width="30%" align-center>
    <span>确认删除该用户吗？</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDeleteUserDialog" style="margin-left: 120px">取 消</el-button>
      <el-button type="primary" @click="deleteUser">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="编辑用户" v-model="editUserDialogVisible" width="30%" align-center>
    <el-form label-width="80px" ref="formRef" :model="editUser" :rules="rules">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="editUser.username" placeholder="请输入用户名" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="用户角色" prop="type">
        <el-select v-model="editUser.type" placeholder="请选择用户类型">
          <el-option label="管理员" value="admin"></el-option>
          <el-option label="用户" value="user"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="editUser.sex" placeholder="请选择性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="归属地" prop="address">
        <el-input v-model="editUser.address" placeholder="请输入归属地"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="editUser.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer" >
        <el-button @click="closeEditUserDialog" style="margin-left: 130px">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import AdminHeader from "@/components/admin/AdminHeader.vue";
import AdminMenu from "@/components/admin/AdminMenu.vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import {rowKey} from "element-plus/es/components/table-v2/src/common";
export default {
  computed: {
    rowKey() {
      return rowKey
    }
  },
  components: {AdminHeader,AdminMenu},
  data() {
    return {
      input: '',
      currentPage: 1,
      pageSize: 5,
      tableData: [],
      addUserDialogVisible: false,//添加用户对话框
      batchDeleteUserDialogVisible: false,//批量删除用户对话框
      deleteUserDialogVisible: false,//删除用户对话框
      editUserDialogVisible: false,//编辑用户对话框
      newUser: {
        username: '',
        password: '',
        sex: '',
        email: '',
        address: ''
      },
      editUser: {
        username: '',
        type: '',
        sex: '',
        email: '',
        address: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'blur' },
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入归属地', trigger: 'blur' },
        ]
      },
      selectedUsers: [],
      savedSelection: [],
    }
  },
  methods: {
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },
    getUserList() {
      request.get("http://localhost:8080/admin/findAllUsers").then(res => {
        this.tableData = res.data || [];
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      });
    },
    showAddUserDialog() {
      this.addUserDialogVisible = true;
    },
    closeAddUserDialog() {
      this.addUserDialogVisible = false;
    },
    addUser() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          request.post("http://localhost:8080/admin/addUser?username="+this.newUser.username+"&password="+this.newUser.password+"&sex="+this.newUser.sex+"&email="+this.newUser.email+"&address="+this.newUser.address).then(res => {
            if (res.code === 0) {
              ElMessage({
                message: "添加用户成功",
                type: 'success',
              });
              this.closeAddUserDialog();
              this.getUserList();
              this.newUser = {
                username: '',
                password: '',
                sex: '',
                email: '',
                address: ''
              };
            }else {
              ElMessage({
                message: "添加用户失败",
                type: 'error',
              });
            }
          })
        }else {
          ElMessage.error('请输入完整的用户信息！');
        }
      });
    },//添加用户
    showDeleteUserDialog(username) {
      this.usernameToDelete = username;
      this.deleteUserDialogVisible = true;
    },
    closeDeleteUserDialog() {
      this.deleteUserDialogVisible = false;
    },
    deleteUser() {
      request.get(`http://localhost:8080/admin/deleteUser?username=${this.usernameToDelete}`).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "删除用户成功",
            type: 'success',
          });
          this.deleteUserDialogVisible = false;
          this.getUserList();
        }else {
          ElMessage({
            message: "删除用户失败",
            type: 'error',
          })
        }
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      })
    },//删除单个用户
    showEditUserDialog(row){
      this.editUser={...row};
      this.editUserDialogVisible=true;
    },
    closeEditUserDialog(){
      this.editUserDialogVisible=false;
    },
    saveUser(){
      request.post("http://localhost:8080/admin/updateUser?username="+this.editUser.username+"&type="+this.editUser.type+"&sex="+this.editUser.sex+"&address="+this.editUser.address+"&email="+this.editUser.email).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "修改用户信息成功",
            type: 'success',
          });
          this.closeEditUserDialog();
          this.getUserList();
        }else {
          ElMessage({
            message: "修改用户信息失败",
            type: 'error',
          })
        }
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      })
    },//保存编辑
    handleSelectionChange(selection) {
      this.selectedUsers = selection.map(user => user.username);
    },
    showBatchDeleteUserDialog(){
      this.savedSelection = this.selectedUsers.slice();
      if (this.selectedUsers.length === 0) {
        ElMessage.warning('请先选择要删除的用户');
      }else {
        this.batchDeleteUserDialogVisible = true;
      }
    },
    closeBatchDeleteUserDialog(){
      this.batchDeleteUserDialogVisible = false;
    },
    batchDeleteUsers() {
      const usernames = this.selectedUsers;
      request.post("http://localhost:8080/admin/batchDeleteUsers", usernames).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "批量删除用户成功",
            type: 'success',
          });
          this.closeBatchDeleteUserDialog();
          this.selectedUsers = [];
          this.getUserList();
        } else {
          ElMessage({
            message: "批量删除用户失败",
            type: 'error',
          })
        }
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      })
    },//批量删除用户
    searchUsers() {
      if (this.input.trim() === '') {
        this.getUserList();
      } else {
        this.tableData = this.tableData.filter(user =>
            user.username.toLowerCase().includes(this.input.toLowerCase())
        );
        this.currentPage = 1;
      }
    }
  },
  mounted() {
    this.getUserList();
  }
}
</script>

<style>
</style>