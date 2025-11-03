import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {path: '/login', name: 'login', component: () => import('../views/Index/Login.vue')},

    {path:'/privacy',name:'privacy',component:()=>import('../views/Privacy.vue')},

    {path: '/home', name: 'home', component: () => import('../views/Home/Home.vue')},

    {path:'/course',name:'course',component:()=>import('../views/Home/Course.vue')},

    {path:'/course/player',name:'player',component:()=>import('../views/Home/CoursePlayer.vue')},

    {path:'/about',name:'about',component:()=>import('../views/Home/About.vue')},

    {path: '/register', name: 'register', component: () => import('../views/Index/Register.vue')},

    {path:'/',redirect:'/home'},

    {path:'/user/center',name:'user',component:()=>import('../views/User/UserCenter.vue')},

    {path:'/forget',name:'forget',component:()=>import('../views/Index/Forget.vue')},

    {path:'/admin',name:'admin',component:()=>import('../views/Admin/AdminHome.vue')},

    {path:'/admin/courseManage',name:'courseManage',component:()=>import('../views/Admin/CourseManage.vue')},

    {path:'/admin/userManage',name:'userManage',component:()=>import('../views/Admin/UserManage.vue')},

    {path:'/admin/adminManage',name:'adminManage',component:()=>import('../views/Admin/AdminManage.vue')},

    {path:'/home/advice',name:'advice',component:()=>import('../views/Home/Advice.vue')},

    {path:'/404',name:'404',component:()=>import('../views/Home/404.vue')},

    {path:'/:pathMatch(.*)*',name:'notFound',component:()=>import('../views/Home/404.vue')},

    {path:'/admin/adviceManage',name:'adviceManage',component:()=>import('../views/Admin/AdviceManage.vue')},

    {path:'/user/course',name:'userCourse',component:()=>import('../views/User/UserCourse.vue')},

    {path:'/course/details/:coursename',name:'details',component:()=>import('../views/Home/CourseDetails.vue')},

    {path:'/user/order',name:'order',component:()=>import('../views/User/UserOrder.vue')},

    {path:'/user/message',name:'message',component:()=>import('../views/User/UserMessage.vue')},

    {path:'/user/collect',name:'collect',component:()=>import('../views/User/UserCollect.vue')},
  ]
})

export default router
