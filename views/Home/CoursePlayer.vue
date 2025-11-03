<template>
  <HomeHeader></HomeHeader>
  <div class="nav" style="height: 30px">
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
  </div>
  <div class="playContainer" style="display: flex;margin-top: 5px">
    <div class="left" style="flex: 2.5;height: 700px">
      <div style="margin-top: 20px;margin-left: 100px">
        <el-button type="success">视频课</el-button>
        <span style="margin-left: 10px;font-weight: bold;font-size: 25px">C语言程序设计基础</span>
      </div>
      <div class="video" style="margin: 30px auto;padding-left: 100px">
        <video src="@/assets/videos/C.mp4" controls="controls" width="88%" height="80%"></video>
      </div>
      <div>
        <div class="title" style="margin-left: 100px">
          <span style="font-size: 20px;font-weight: bold">章节介绍</span>
          <p>此课程讲解了C语言的基础知识，适合初学者学习。</p>
        </div>
      </div>
    </div>
    <el-divider direction="vertical" style="height: 700px"></el-divider>
    <div class="right" style="flex: 1;height: 700px">
      <div>
        <el-menu
            :default-active="activeIndex"
            class="el-menu-vertical-demo"
            mode="horizontal"
            background-color="#e6f1ff"
            text-color="#666666"
            active-text-color="#006eff"
            style="width: 100%"
            @select="handleSelect"
        >
          <el-menu-item index="1">目录</el-menu-item>
          <el-menu-item index="2">评论</el-menu-item>
        </el-menu>
      </div>
      <!-- 课程目录 -->
      <div v-if="activeIndex === '1'" style="height: 400px;margin-top: 20px">
        <div>
          <el-menu :default-active="catalogue">
            <el-sub-menu index="catalogue">
              <template #title>
                <span>课程目录</span>
              </template>
              <el-sub-menu index="1">
                <template #title>
                  <span>第一章</span>
                </template>
                <el-menu-item index="1-1" class="activeCatalogue">1.1 C语言概述</el-menu-item>
                <el-menu-item index="1-2">1.2 C语言环境搭建</el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="2">
                <template #title>
                  <span>第二章</span>
                </template>
                <el-menu-item index="2-1">2.1 数据类型</el-menu-item>
                <el-menu-item index="2-2">2.2 变量</el-menu-item>
                <el-menu-item index="2-3">2.3 常量</el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="3">
                <template #title>
                  <span>第三章</span>
                </template>
                <el-menu-item index="3-1">3.1 运算符</el-menu-item>
                <el-menu-item index="3-2">3.2 流程控制</el-menu-item>
                <el-menu-item index="3-3">3.3 循环语句</el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="4">
                <template #title>
                  <span>第四章</span>
                </template>
                <el-menu-item index="4-1">4.1 函数</el-menu-item>
                <el-menu-item index="4-2">4.2 数组</el-menu-item>
                <el-menu-item index="4-3">4.3 结构体</el-menu-item>
              </el-sub-menu>
            </el-sub-menu>
          </el-menu>
        </div>
      </div>
      <!-- 课程评论 -->
      <div v-if="activeIndex === '2'" style="height: 400px;margin-top: 20px">
        <div style="display: flex;align-items: center">
          <img src="@/assets/images/avatar.jpg" alt="" width="30px" height="30px" style="border-radius: 50px">
          <el-input v-model="content" placeholder="请输入评论内容" style="width: 280px;margin-left: 10px"></el-input>
          <el-button type="primary" style="margin-left: 10px" @click="addComment">发布评论</el-button>
        </div>
<!--        评论区-->
        <div style="margin-top: 15px">

          <div class="everyComment" v-for="(comment,index) in comments" :key="index">
            <div  style="display: flex;margin-bottom: 20px">
              <img src="@/assets/images/avatar.jpg" alt="" width="30px" height="30px" style="border-radius: 50px">
              <div style="margin-left: 10px;flex: 3">
                <span style="font-weight: bold">{{ comment.username}}</span>
  <!--              评论时间-->
                <div style="font-size: 13px;color: #666666">{{comment.time}}</div>
  <!--              评论内容-->
                <div style="font-size: 15px;margin-top: 10px">{{comment.content}}</div>
              </div>
              <div style="flex: 2">
                <i class="iconfont" style="float: right;color: gray;margin-top: 5px;margin-right: 30px;display: flex;align-items: center">
                  &#xe600;
                  <span style="font-size: 14px;color: #666666;margin-left: 5px">{{comment.likes}}</span>
                </i>
                <el-button type="text" style="float: right;margin-right: 20px">
                  <i class="iconfont" style="color: #666666;font-size: 25px">&#xe6df;</i>
                  <span class="comment" style="color: #666666" @click="addReply">评论</span>
                </el-button>
              </div>
            </div>
<!--                          回复区-->
            <div class="replyComment" style="margin-top: 10px;background-color: #e6f1ff;margin-bottom: 10px;margin-left: 30px">
              <div style="display: flex">
                <img src="@/assets/images/avatar.jpg" alt="" width="30px" height="30px" style="border-radius: 50px">
                <div style="margin-left: 10px;flex: 3">
                  <span style="font-weight: bold">张三</span>
                  <div style="font-size: 13px;color: #666666">2024-11-1 19:45:32</div>
                  <div style="font-size: 14px;margin-top: 10px">同意！</div>
                </div>
                <div style="flex: 2">
                  <i class="iconfont" style="float: right;color: gray;margin-top: 5px;margin-right: 35px;display: flex;align-items: center">
                    &#xe600;
                    <span style="font-size: 14px;color: #666666;margin-left: 5px">3</span>
                  </i>
                  <el-button type="text" style="float: right;margin-right: 20px">
                    <i class="iconfont" style="color: #666666;font-size: 25px">&#xe6df;</i>
                    <span class="comment" style="color: #666666" @click="addReply">评论</span>
                  </el-button>
                </div>
              </div>
            </div>
          </div>

        </div>

      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import HomeHeader from "@/components/home/HomeHeader.vue";
import {mapState} from "vuex";

export default {
  components: { HomeHeader },
  data() {
    return {
      activeIndex: '1',
      catalogue: '1-1',
      content: '',
      comments: [
        {
          username: '张三',
          time: '2024-07-01 10:00:58',
          content: '这门课程很好，老师讲的很详细。',
          likes: 5
        },
        {
          username: '李四',
          time: '2021-07-02 11:51:00',
          content: '讲得不错。',
          likes: 3
        },
        {
          username: '王五',
          time: '2021-07-03 08:20:45',
          content: '老师讲得很好，很容易理解。',
          likes: 4
        }
      ],
      likes: 0
    }
  },
  methods: {
    goBack() {
      this.$router.back();
    },
    handleSelect(key) {
      this.activeIndex = key;
    },
    addComment() {
      if (this.content.trim() !== '') {
        const newComment = {
          username: this.username,
          time: new Date().toLocaleString(),
          content: this.content,
          likes: this.likes,
        };
        this.comments.push(newComment);
        this.content = '';
      }
    },
  },
  computed:{
    ...mapState(['username'])
  }
}
</script>

<style>
.playContainer {
  background-color: white;
}
.activeCatalogue {
  background-color: #e6f1ff;
  border-left: 3px solid;
}
.playContainer .iconfont:hover {
  cursor: pointer;
}
.comment:hover {
  color: #409EFF;
}
.replyComment .addReplyComment:hover {
  cursor: pointer;
}
</style>