<template>
  <AdminHeader></AdminHeader>
  <div class="main" style="display: flex">
    <AdminMenu></AdminMenu>
    <div class="main-right" style="width: 100%;height: 700px;background-color: #f0f0f0">
      <div class="search" style="height: 50px;margin: 10px auto 10px 10px;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .1);background-color: white">
        <el-input v-model="input" style="width: 220px;padding-left: 10px;padding-top: 10px" placeholder="请输入课程名称" @input="searchCourses"></el-input>
        <el-button type="primary" icon="search" style="margin-left: 10px;margin-top: 10px" @click="searchCourses">查询</el-button>
      </div>
      <div class="operation" style="height: 50px;margin: 10px auto 10px 10px;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .1);background-color: white">
        <el-button type="primary" icon="plus" style="margin-left: 10px;margin-top: 10px" @click="showAddCourseDialog()">添加课程</el-button>
        <el-button type="danger" icon="delete" style="margin-left: 10px;margin-top: 10px" @click="showBatchDeleteCourseDialog()">批量删除</el-button>
      </div>
      <div class="table" style="height: 400px;margin: 10px auto 10px 10px;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .1);">
        <el-table
            :data="tableDataWithIndex"
            stripe
            style="width: 100%"
            size="default"
            row-key="coursename"
            @selection-change="handleSelectionChange"
        >
          <el-table-column :reserve-selection="true" type="selection" width="50" />
          <el-table-column type="index" label="序号" width="80">
            <template #default="scope">
              {{ scope.row.index }}
            </template>
          </el-table-column>
          <el-table-column  label="课程名称" prop="coursename" width="200" />
          <el-table-column  label="课程类型" prop="coursetype" width="130" />
          <el-table-column label="课程介绍" prop="introduction" width="180" show-overflow-tooltip/>
          <el-table-column  label="课程价格" sortable prop="courseprice" width="130" />
          <el-table-column  label="课程时间" sortable prop="coursetime" width="150" />
          <el-table-column  label="课程图片" prop="courseimage" width="180">
            <template #default="scope">
              <el-image
                  :src="scope.row.courseimage"
                  style="width: 150px;height: 70px"
                  :preview-src-list="[scope.row.courseimage]"
                  :preview-teleported="true"
                  :hide-on-click-modal="true"
              ></el-image>
            </template>
          </el-table-column>
          <el-table-column  label="操作" prop="operation" align="center" width="200">
            <template #default="scope">
              <el-button type="primary" icon="edit" @click="showEditCourseDialog(scope.row)">编辑</el-button>
              <el-button type="danger" icon="delete" @click="showDeleteCourseDialog(scope.row.coursename)">删除</el-button>
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


  <el-dialog title="添加课程" v-model="addCourseDialogVisible" width="30%" align-center>
    <el-form label-width="80px" ref="formRef" :model="newCourse" :rules="rules">
      <el-form-item label="课程名称" prop="coursename">
        <el-input v-model="newCourse.coursename" placeholder="请输入课程名称"></el-input>
      </el-form-item>
      <el-form-item label="课程类型" prop="coursetype">
        <el-select v-model="newCourse.coursetype" placeholder="请选择课程类型">
          <el-option label="视频课" value="视频课"></el-option>
          <el-option label="图文课" value="图文课"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课程介绍" prop="introduction">
        <el-input v-model="newCourse.introduction" type="textarea" placeholder="请输入课程介绍"></el-input>
      </el-form-item>
      <el-form-item label="课程价格" prop="courseprice">
        <el-input v-model="newCourse.courseprice" placeholder="请输入课程价格"></el-input>
      </el-form-item>
      <el-form-item label="发布时间" prop="coursetime">
        <el-date-picker
            v-model="newCourse.coursetime"
            type="date"
            placeholder="请选择课程发布时间"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        >

        </el-date-picker>
      </el-form-item>
      <el-form-item label="课程图片" prop="courseimage">
        <el-upload
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :on-preview="handlePictureCardPreview"
            :file-list="fileList"
            limit="1"
            :on-exceed="handleExceed"
            :on-change="handleChange"
            v-model="newCourse.courseimage"
        >
          <el-icon><Plus /></el-icon>
        </el-upload>
        <el-dialog v-model="dialogVisible" :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="预览图片" />
        </el-dialog>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer" >
        <el-button @click="closeAddCourseDialog" style="margin-left: 130px">取 消</el-button>
        <el-button type="primary" @click="addCourse">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="批量删除用户" v-model="batchDeleteCourseDialogVisible" width="30%" align-center>
    <span>确认删除选中的 {{ selectedCourses.length }} 个课程吗？</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeBatchDeleteCourseDialog" style="margin-left: 80px">取 消</el-button>
      <el-button type="primary" @click="batchDeleteCourses">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="删除课程" v-model="deleteCourseDialogVisible" width="30%" align-center>
    <span>确认删除该课程吗？</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDeleteCourseDialog" style="margin-left: 120px">取 消</el-button>
      <el-button type="primary" @click="deleteCourse">确 定</el-button>
    </span>
  </el-dialog>
  <el-dialog title="编辑课程" v-model="editCourseDialogVisible" width="30%" align-center>
    <el-form label-width="80px" ref="formRef" :model="editCourse" :rules="rules">
      <el-form-item label="课程名称" prop="coursename">
        <el-input v-model="editCourse.coursename" placeholder="请输入课程名称" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="课程类型" prop="coursetype">
        <el-select v-model="editCourse.coursetype" placeholder="请选择课程类型">
          <el-option label="视频课" value="视频课"></el-option>
          <el-option label="图文课" value="图文课"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课程介绍" prop="introduction">
        <el-input v-model="editCourse.introduction" type="textarea" placeholder="请输入课程介绍"></el-input>
      </el-form-item>
      <el-form-item label="课程价格" prop="courseprice">
        <el-input v-model="editCourse.courseprice" placeholder="请输入课程价格"></el-input>
      </el-form-item>
      <el-form-item label="发布时间" prop="coursetime">
        <el-date-picker
            v-model="editCourse.coursetime"
            type="date"
            placeholder="请选择课程发布时间"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="课程图片" prop="courseimage">
        <el-upload
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :on-preview="handlePictureCardPreview"
            :file-list="fileList"
            limit="1"
            :on-exceed="handleExceed"
            :on-change="handleChange"
            v-model="editCourse.courseimage"
        >
          <el-icon><Plus /></el-icon>
        </el-upload>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeEditCourseDialog" style="margin-left: 130px">取 消</el-button>
      <el-button type="primary" @click="saveCourse">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script lang="ts">
import AdminHeader from "@/components/admin/AdminHeader.vue";
import AdminMenu from "@/components/admin/AdminMenu.vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request.js";
import {ref} from "vue";
export default {
  components: {AdminMenu, AdminHeader},
  data() {
    return {
        currentPage: 1,
        pageSize: 5,
        input: '',
        tableData: [],
        newCourse: {
          coursename: '',
          coursetype: '',
          courseprice: '',
          coursetime: '',
          courseimage: '',
          introduction: ''
        },
        rules: {
          coursename: [
            { required: true, message: '请输入课程名称', trigger: 'blur' },
          ],
          coursetype: [
            { required: true, message: '请选择课程类型', trigger: 'blur' },
          ],
          courseprice: [
            {required: true, message: '请输入课程价格', trigger: 'blur'},
          ],
          coursetime: [
            {required: true, message: '请选择课程发布时间', trigger: 'blur'},
          ],
          courseimage: [
            {required: true, message: '请上传课程图片', trigger: 'blur'},
          ],
          introduction: [
            {required: true, message: '请输入课程介绍', trigger: 'blur'},
          ]
        },
        addCourseDialogVisible: false,//添加课程对话框
        fileList: [],
        dialogImageUrl: '',
        dialogVisible: false,
        selectedCourses: [],
        batchDeleteCourseDialogVisible: false,//批量删除课程对话框
        savedSelection: [],
        deleteCourseDialogVisible: false,//删除课程对话框
        editCourseDialogVisible: false,//编辑课程对话框
        editCourse: {
          coursename: '',
          coursetype: '',
          courseprice: '',
          coursetime: '',
          courseimage: '',
          introduction: ''
        },
        imageUrl: '/src/assets/images/courseImage/',
    }
  },
  methods: {
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },
    getCourseList() {
      request.get("http://localhost:8080/admin/findAllCourses").then(res => {
        this.tableData = res.data || [];
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      });
    },
    showAddCourseDialog() {
      this.addCourseDialogVisible = true;
    },
    closeAddCourseDialog() {
      this.addCourseDialogVisible = false;
    },
    addCourse() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          request.post("http://localhost:8080/admin/addCourse",null,{
            params:{
              coursename:this.newCourse.coursename,
              coursetype:this.newCourse.coursetype,
              introduction:this.newCourse.introduction,
              courseprice:this.newCourse.courseprice,
              coursetime:this.newCourse.coursetime,
              courseimage:this.newCourse.courseimage
            }
          }).then(res => {
            if (res.code === 0) {
              ElMessage({
                message: "添加课程成功",
                type: 'success',
              });
              this.closeAddCourseDialog();
              this.getCourseList();
              this.newCourse = {
                coursename: '',
                coursetype: '',
                courseprice: '',
                coursetime: '',
                courseimage: '',
                introduction: ''
              };
            }else {
              ElMessage({
                message: "添加课程失败",
                type: 'error',
              });
            }
          })
        }else {
          ElMessage.error('请输入完整的课程信息！');
        }
      });
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleExceed() {
      this.$message.warning(`只能上传 1 个文件`);
    },
    handleChange(file, fileList) {
      this.newCourse.courseimage = file.raw.name;
    },
    handleSelectionChange(selection) {
      this.selectedCourses = selection.map(course => course.coursename);
    },
    showBatchDeleteCourseDialog() {
      this.savedSelection = this.selectedCourses.slice();
      if (this.selectedCourses.length === 0) {
        ElMessage.warning('请先选择要删除的课程');
      }else {
        this.batchDeleteCourseDialogVisible = true;
      }
    },
    closeBatchDeleteCourseDialog() {
      this.batchDeleteCourseDialogVisible = false;
    },
    batchDeleteCourses() {
      const coursenames = this.selectedCourses;
      request.post("http://localhost:8080/admin/batchDeleteCourses", coursenames).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "批量删除课程成功",
            type: 'success',
          });
          this.closeBatchDeleteCourseDialog();
          this.selectedCourses = [];
          this.getCourseList();
        } else{}
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      })
    },
    showDeleteCourseDialog(coursename) {
      this.coursenameToDelete = coursename;
      this.deleteCourseDialogVisible = true;
    },
    closeDeleteCourseDialog() {
      this.deleteCourseDialogVisible = false;
    },
    deleteCourse() {
      request.get(`http://localhost:8080/admin/deleteCourse?coursename=${this.coursenameToDelete}`).then(res => {
        if (res.code === 0) {
          ElMessage({
            message: "删除课程成功",
            type: 'success',
          });
          this.deleteCourseDialogVisible = false;
          this.getCourseList();
        }else {
          ElMessage({
            message: "删除课程失败",
            type: 'error',
          })
        }
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      })
    },
    showEditCourseDialog(row){
      this.editCourse={...row};
      this.fileList=[{
        name:row.courseimage,
        url:row.courseimage
      }]
      this.editCourseDialogVisible=true;
    },
    closeEditCourseDialog(){
      this.editCourseDialogVisible=false;
    },
    saveCourse(){
      this.editCourse.courseimage=this.newCourse.courseimage;
      request.post("http://localhost:8080/admin/updateCourse",null,{
        params:{
          coursename:this.editCourse.coursename,
          coursetype:this.editCourse.coursetype,
          introduction:this.editCourse.introduction,
          courseprice:this.editCourse.courseprice,
          coursetime:this.editCourse.coursetime,
          courseimage:this.editCourse.courseimage
        }
      }).then(res => {
        console.log(res);
        if (res.code === 0) {
          ElMessage({
            message: "修改课程信息成功",
            type: 'success',
          });
          this.closeEditCourseDialog();
          this.getCourseList();
        }else {
          ElMessage({
            message: "修改课程信息失败",
            type: 'error',
          })
        }
      }).catch(error => {
        ElMessage.error('请求失败: ' + error.message);
      })
    },
    searchCourses() {
      if (this.input.trim() === '') {
        this.getCourseList();
      } else {
        this.tableData = this.tableData.filter(course =>
            course.coursename.toLowerCase().includes(this.input.toLowerCase())
        );
        this.currentPage = 1;
      }
    }
  },
  computed: {
    formattedTableData() {
      return this.tableData.map(item => ({
        ...item,
        courseimage: this.imageUrl + item.courseimage
      }));
    },
    tableDataWithIndex() {
      return this.formattedTableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize).map((item, index) => {
        return {
          ...item,
          index: (this.currentPage - 1) * this.pageSize + index + 1
        };
      });
    }
  },
  mounted() {
    this.getCourseList();
  }
};
</script>

<style>
body{
  margin: 0;
}
</style>