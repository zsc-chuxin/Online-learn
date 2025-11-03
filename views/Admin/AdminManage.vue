<<template>
  <AdminHeader></AdminHeader>
  <div class="main" style="display: flex">
    <AdminMenu></AdminMenu>
    <div class="main-right" style="width: 100%;height: 800px">
      <div class="search" style="height: 50px;margin: 10px auto 10px 10px;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .1);background-color: white">
        <el-input v-model="input" style="width: 220px;padding-left: 10px;padding-top: 10px" placeholder="请输入用户名" @input="searchAdmins"></el-input>
        <el-button type="primary" icon="search" style="margin-left: 10px;margin-top: 10px" @click="searchAdmins">查询</el-button>
      </div>
      <div class="operation" style="height: 50px;margin: 10px auto 10px 10px;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .1);background-color: white">
        <el-button type="primary" icon="plus" style="margin-left: 10px;margin-top: 10px" @click="showAddAdminDialog">添加管理员</el-button>
        <el-button type="danger" icon="delete" style="margin-left: 10px;margin-top: 10px" @click="showBatchDeleteAdminDialog()">批量删除</el-button>
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
          <el-table-column  label="账号" prop="username" width="150" />
          <el-table-column  label="用户角色" prop="type" width="150" />
          <el-table-column  label="性别" prop="sex" width="150" />
          <el-table-column  label="归属地" prop="address" width="150" />
          <el-table-column  label="邮箱" prop="email" width="150" />
          <el-table-column  label="手机号" prop="phone" width="150" />
          <el-table-column  label="操作" prop="operation" align="center" width="200">
            <template #default="scope">
              <el-button type="primary" icon="edit" @click="showEditAdminDialog(scope.row)">编辑</el-button>
              <el-button type="danger" icon="delete" @click="showDeleteAdminDialog(scope.row.username)">删除</el-button>
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
  <el-dialog title="添加管理员" v-model="addAdminDialogVisible" width="30%" align-center>
    <el-form label-width="80px" ref="formRef" :model="newAdmin" :rules="rules">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="newAdmin.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="newAdmin.password" type="password" show-password placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="newAdmin.sex" placeholder="请选择性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="newAdmin.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="归属地" prop="address">
        <el-input v-model="newAdmin.address" placeholder="请输入归属地"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer" >
        <el-button @click="closeAddAdminDialog" style="margin-left: 130px">取 消</el-button>
        <el-button type="primary" @click="addAdmin">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="批量删除管理员" v-model="batchDeleteAdminDialogVisible" width="30%" align-center>
    <span>确认删除选中的 {{ selectedAdmins.length }} 个管理员吗？</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeBatchDeleteAdminDialog" style="margin-left: 80px">取 消</el-button>
      <el-button type="primary" @click="batchDeleteAdmins">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="编辑管理员" v-model="editAdminDialogVisible" width="30%" align-center>
    <el-form label-width="80px" ref="formRef" :model="editAdmin" :rules="rules">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="editAdmin.username" placeholder="请输入用户名" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="用户角色" prop="type">
        <el-select v-model="editAdmin.type" placeholder="请选择用户类型">
          <el-option label="管理员" value="admin"></el-option>
          <el-option label="用户" value="user"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="editAdmin.sex" placeholder="请选择性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="归属地" prop="address">
        <el-input v-model="editAdmin.address" placeholder="请输入归属地"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="editAdmin.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer" >
        <el-button @click="closeEditAdminDialog" style="margin-left: 130px">取 消</el-button>
        <el-button type="primary" @click="saveAdmin">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="删除管理员" v-model="deleteAdminDialogVisible" width="30%" align-center>
    <span>确认删除该用户吗？</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDeleteAdminDialog" style="margin-left: 120px">取 消</el-button>
      <el-button type="primary" @click="deleteAdmin">确 定</el-button>
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
  components: {AdminHeader,AdminMenu},
  computed: {
    rowKey() {
      return rowKey
    }
  },
  data() {
    return {
      input: '',
      currentPage: 1,
      pageSize: 5,
      tableData: [],
      addAdminDialogVisible: false,
      batchDeleteAdminDialogVisible: false,
      editAdminDialogVisible: false,
      deleteAdminDialogVisible: false,
      editAdmin: {
        username: '',
        type: '',
        sex: '',
        email: '',
        address: ''
      },
      newAdmin: {
        username: '',
        password: '',
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
      selectedAdmins: [],
      savedSelection: [],
    }
  },
  methods: {
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },
    getAdminList() {
      request.get("http://localhost:8080/admin/findAllAdmins").then(res => {
        this.tableData = res.data || [];
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      });
    },
    showAddAdminDialog() {
      this.addAdminDialogVisible = true;
    },
    closeAddAdminDialog() {
      this.addAdminDialogVisible = false;
    },
    addAdmin() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          request.post("http://localhost:8080/admin/addAdmin?username="+this.newAdmin.username+"&password="+this.newAdmin.password+"&sex="+this.newAdmin.sex+"&email="+this.newAdmin.email+"&address="+this.newAdmin.address+"&type=admin").then(res => {
            if (res.code === 0) {
              ElMessage({
                message: "添加管理员成功",
                type: 'success',
              });
              this.closeAddAdminDialog();
              this.getAdminList();
              this.newAdmin = {
                username: '',
                password: '',
                sex: '',
                email: '',
                address: ''
              };
            }else {
              ElMessage({
                message:"添加管理员失败",
              })
            }
          })
        }else {
          ElMessage.error('请输入完整的管理员信息！');
        }
      });
    },
    handleSelectionChange(selection) {
      this.selectedAdmins = selection.map(admin => admin.username);
    },
    showBatchDeleteAdminDialog() {
      this.savedSelection = this.selectedAdmins.slice();
      if (this.selectedAdmins.length === 0) {
        ElMessage.warning('请先选择要删除的管理员');
      } else {
        this.batchDeleteAdminDialogVisible = true;
      }
    },
    closeBatchDeleteAdminDialog() {
      this.batchDeleteAdminDialogVisible = false;
    },
    batchDeleteAdmins() {
      const usernames = this.selectedAdmins;
      request.post("http://localhost:8080/admin/batchDeleteAdmins", usernames).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "批量删除管理员成功",
            type: 'success',
          });
          this.closeBatchDeleteAdminDialog();
          this.selectedAdmins= [];
          this.getAdminList();
        }else {
          ElMessage({
            message: "批量删除管理员失败",
            type: 'error',
          })
        }
      })
    },
    showEditAdminDialog(row){
      this.editAdmin={...row};
      this.editAdminDialogVisible=true;
    },
    closeEditAdminDialog(){
      this.editAdminDialogVisible=false;
    },
    saveAdmin(){
      request.post("http://localhost:8080/admin/updateAdmin?username="+this.editAdmin.username+"&type="+this.editAdmin.type+"&sex="+this.editAdmin.sex+"&address="+this.editAdmin.address+"&email="+this.editAdmin.email).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "修改管理员信息成功",
            type: 'success',
          });
          this.closeEditAdminDialog();
          this.getAdminList();
        }else {
          ElMessage({
            message: "修改管理员信息失败",
            type: 'error',
          })
        }
      })
    },
    showDeleteAdminDialog(username) {
      this.usernameToDelete = username;
      this.deleteAdminDialogVisible = true;
    },
    closeDeleteAdminDialog() {
      this.deleteAdminDialogVisible = false;
    },
    deleteAdmin() {
      request.post(`http://localhost:8080/admin/deleteAdmin?username=${this.usernameToDelete}`).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "删除管理员成功",
            type: 'success',
          });
          this.deleteAdminDialogVisible = false;
          this.getAdminList();
        }else {
          ElMessage({
            message: "删除管理员失败",
          })
        }
      })
    },
    searchAdmins() {
      if (this.input.trim() === '') {
        this.getAdminList();
      } else {
        this.tableData = this.tableData.filter(admin =>
            admin.username.toLowerCase().includes(this.input.toLowerCase())
        );
        this.currentPage = 1;
      }
    }
  },
  mounted() {
    this.getAdminList();
  }
}
</script>

<style>
body{
  margin: 0;
}
</style>