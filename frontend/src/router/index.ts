import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import MyPage from '../views/MyPage.vue'
import Home from '../views/Home.vue'
import Class from '../views/Class.vue'
import Login from '../views/User/Login.vue'
import Studentregist from '../views/User/StudentRegist.vue'
import TuterRegister from '../views/User/TuterRegister.vue'
import FindUser from '../views/User/FindUserInfo.vue'
Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/mypage',
    name: 'mypage',
    component: MyPage,
    children: [
      {
        path: "info",
        name: "user-info",
        component: () => import("@/components/mypage/user/Info.vue")
      },
      {
        path: "lecture",
        name: "user-lecture",
        component: () => import("@/components/mypage/user/Lecture.vue")
      },
      {
        path: "like",
        name: "user-like",
        component: () => import("@/components/mypage/user/Like.vue")
      },
      {
        path: "feedback",
        name: "user-feedback",
        component: () => import("@/components/mypage/user/Feedback.vue")
      },
      {
        path: "practice",
        name: "user-practice",
        component: () => import("@/components/mypage/user/Practice.vue")
      },
      {
        path: "info",
        name: "tutor-info",
        component: () => import("@/components/mypage/tutor/Info.vue")
      },
      {
        path: "lecture",
        name: "tutor-lecture",
        component: () => import("@/components/mypage/tutor/Lecture.vue")
      },
      {
        path: "feedback",
        name: "tutor-feedback",
        component: () => import("@/components/mypage/tutor/Feedback.vue")
      },
      {
        path: "schedule",
        name: "tutor-schedule",
        component: () => import("@/components/mypage/tutor/Schedule.vue")
      },
      {
        path: "lectureadd",
        name: "tutor-lectureadd",
        component: () => import("@/components/mypage/tutor/LectureAdd.vue")
      },
    ]
  },
  {
    path: '/class',
    name: 'Class',
    component: Class
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register/student',
    name: 'StudentRegist',
    component: Studentregist
  },
  {
    path: '/register/tutor',
    name: 'TutorRegist',
    component: TuterRegister
  },
  {
    path: '/user/find',
    name: 'FindUser',
    component: FindUser,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
