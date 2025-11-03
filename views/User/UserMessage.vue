<template>
  <div>
    <HomeHeader />
    <div class="user-message-container" style="width: 90%;margin: 20px auto auto;background-color: white;box-shadow: 0 2px 15px 0 rgba(0, 0, 0, .1);">
      <div class="message-header" style="padding: 20px 20px 20px 20px;display: flex;align-items: center;justify-content: space-between">
          <span style="font-size: 20px">消息中心</span>
          <div class="message-settings" style="display: flex;align-items: center">
            <el-button type="text" style="font-size: 17px" >
              <el-icon><check /></el-icon>
              全部标记为已读
            </el-button>
            <el-button type="text" style="font-size: 17px" >
              <el-icon><delete /></el-icon>
              清空消息
            </el-button>
            <el-button type="text" style="font-size: 17px" @click="messageSettings">
              <el-icon><tools /></el-icon>
              消息设置
            </el-button>
          </div>
      </div>
<!--      消息类别-->
      <div class="message-category" style="padding: 20px 20px 20px 20px">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="系统消息" name="system">
            <div class="message-list">
              <el-card v-for="item in systemMessage" :key="item.id" style="margin: 10px 0" shadow="hover" @click="item.isread = true">
                <div style="display: flex;align-items: center">
                  <div class="sender">
                    <el-avatar src="https://zsc-bucket.oss-cn-hangzhou.aliyuncs.com/logo.png" alt="" width="50px" height="50px" style="background-color: white"></el-avatar>
                    <div v-if="!item.isread" class="unread-num">1</div>
                  </div>
                  <div style="margin-left: 20px;">
                    <div style="font-size: 17px">{{ item.title }}</div>
                    <div style="font-size: 14px;color: #666666;margin-top: 10px">{{ item.content }}</div>
                  </div>
                  <div style="margin-left: auto">
                    <span style="font-size: 15px;color: #666666">{{ item.date }}</span>
                  </div>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="课程消息" name="course">
            <div class="message-list">
              <el-card v-for="item in courseMessage" :key="item.id" style="margin: 10px 0" shadow="hover">
                <div style="display: flex;align-items: center;justify-content: space-between">
                  <span style="font-size: 17px">{{ item.title }}</span>
                  <span style="font-size: 14px">{{ item.date }}</span>
                </div>
                <div style="margin-top: 10px">
                  <span>{{ item.content }}</span>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="交易消息" name="trade">
            <div class="message-list">
              <el-card v-for="item in tradeMessage" :key="item.id" style="margin: 10px 0" shadow="hover">
                <div style="display: flex;align-items: center;justify-content: space-between">
                  <span style="font-size: 17px">{{ item.title }}</span>
                  <span style="font-size: 14px">{{ item.date }}</span>
                </div>
                <div style="margin-top: 10px">
                  <span>{{ item.content }}</span>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="评论消息" name="comment">
            <div class="message-list">
              <el-card v-for="item in commentMessage" :key="item.id" style="margin: 10px 0" shadow="hover">
                <div style="display: flex;align-items: center;justify-content: space-between">
                  <span style="font-size: 17px">{{ item.title }}</span>
                  <span style="font-size: 14px">{{ item.date }}</span>
                </div>
                <div style="margin-top: 10px">
                  <span>{{ item.content }}</span>
                </div>
              </el-card>
            </div>
          </el-tab-pane>
          <el-tab-pane label="私信" name="private">
            <div class="message-list">
              <el-card v-for="item in privateMessage" :key="item.id" style="margin: 10px 0" shadow="hover">
                <div style="display: flex;align-items: center">
                  <div>
                    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" alt="" width="50px" height="50px"></el-avatar>
                  </div>
                  <div style="margin-left: 20px">
                    <div style="font-size: 17px">{{ item.title }}</div>
                    <div style="font-size: 14px;margin-top: 10px ">{{ item.content }}</div>
                  </div>
                  <div style="margin-left: auto">
                    <span style="font-size: 15px;color: #666666">{{ item.date }}</span>
                  </div>
                </div>

              </el-card>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <el-dialog title="消息设置" v-model="settingsDialogVisible" align-center width="800px"></el-dialog>
    <HomeFooter />
  </div>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeFooter from "@/components/home/HomeFooter.vue";
import {Tools} from "@element-plus/icons-vue";
export default {
  components: {Tools, HomeHeader, HomeFooter},
  data() {
    return {
      activeName: 'system',
      systemMessage: [
        {
          id: 1,
          title: '系统消息1',
          date: '2024-12-01',
          content: '这是系统消息1的内容',
          isread: false
        },
        {
          id: 2,
          title: '系统消息2',
          date: '2024-12-02',
          content: '这是系统消息2的内容',
          isread: false
        },
        {
          id: 3,
          title: '系统消息3',
          date: '2024-12-03',
          content: '这是系统消息3的内容',
          isread: false
        },
        {
          id: 4,
          title: '系统消息4',
          date: '2024-12-04',
          content: '这是系统消息4的内容',
          isread: false
        },
        {
          id: 5,
          title: '系统消息5',
          date: '2024-12-05',
          content: '这是系统消息5的内容',
          isread: false
        },
      ],
      courseMessage: [
        {
          id: 1,
          title: '课程消息1',
          date: '2024-12-01',
          content: '您购买的课程《Vue3从入门到精通》已经上线',
        },
        {
          id: 2,
          title: '课程消息2',
          date: '2024-12-02',
          content: '您购买的课程《Vue3从入门到精通》已经上线',
        },
        {
          id: 3,
          title: '课程消息3',
          date: '2024-12-03',
          content: '您购买的课程《Vue3从入门到精通》已经上线',
        },
        {
          id: 4,
          title: '课程消息4',
          date: '2024-12-04',
          content: '您购买的课程《Vue3从入门到精通》已经上线',
        },
        {
          id: 5,
          title: '课程消息5',
          date: '2024-12-05',
          content: '您购买的课程《Vue3从入门到精通》已经上线',
        },
      ],
      tradeMessage: [
        {
          id: 1,
          title: '交易消息1',
          date: '2024-12-01',
          content: '您于2024-12-01购买了课程《Vue3从入门到精通》',
        },
        {
          id: 2,
          title: '交易消息2',
          date: '2024-12-02',
          content: '您于2024-12-02购买了课程《Vue3从入门到精通》',
        },
        {
          id: 3,
          title: '交易消息3',
          date: '2024-12-03',
          content: '您于2024-12-03购买了课程《Vue3从入门到精通》',
        },
        {
          id: 4,
          title: '交易消息4',
          date: '2024-12-04',
          content: '您于2024-12-04购买了课程《Vue3从入门到精通》',
        },
        {
          id: 5,
          title: '交易消息5',
          date: '2024-12-05',
          content: '您于2024-12-05购买了课程《Vue3从入门到精通》',
        },
      ],
      commentMessage: [
        {
          id: 1,
          title: '评论消息1',
          date: '2024-12-01',
          content: '这是评论消息1的内容',
        },
        {
          id: 2,
          title: '评论消息2',
          date: '2024-12-02',
          content: '这是评论消息2的内容',
        },
        {
          id: 3,
          title: '评论消息3',
          date: '2024-12-03',
          content: '这是评论消息3的内容',
        },
        {
          id: 4,
          title: '评论消息4',
          date: '2024-12-04',
          content: '这是评论消息4的内容',
        },
        {
          id: 5,
          title: '评论消息5',
          date: '2024-12-05',
          content: '这是评论消息5的内容',
        },
      ],
      privateMessage: [
        {
          id: 1,
          title: '私信1',
          date: '2024-12-01',
          content: '这是私信1的内容',
        },
        {
          id: 2,
          title: '私信2',
          date: '2024-12-02',
          content: '这是私信2的内容',
        },
        {
          id: 3,
          title: '私信3',
          date: '2024-12-03',
          content: '这是私信3的内容',
        },
        {
          id: 4,
          title: '私信4',
          date: '2024-12-04',
          content: '这是私信4的内容',
        },
        {
          id: 5,
          title: '私信5',
          date: '2024-12-05',
          content: '这是私信5的内容',
        },
      ],
      settingsDialogVisible: false,
    };
  },
  methods:{
    messageSettings(){
      this.settingsDialogVisible = true;
    }
  }
};
</script>

<style>
.sender{
  position: relative;
  display: inline-block;
}
.unread-num{
  position: absolute;
  top: -5px;
  right: -5px;
  width: 18px;
  height: 18px;
  background-color: red;
  color: white;
  border-radius: 50%;
  text-align: center;
  line-height: 18px;
  font-size: 12px;
}
</style>