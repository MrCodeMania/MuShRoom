import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import MyPage from '../views/MyPage.vue'
import Home from '../views/Home.vue'
import Class from '../views/Class.vue'
import UpdateInfoUser from '../views/User/UpdateInfoUser.vue' 
import UpdateInfoTutor from '../views/User/UpdateInfoTutor.vue' 
import ClassDetail from '../views/ClassDetail.vue'
import Login from '../views/User/login.vue'
import Studentregist from '../views/User/studentRegist.vue'
import TuterRegister from '../views/User/TuterRegister.vue'
import FindUser from '../views/User/FindUserInfo.vue'
import Board from '../views/Board/Board.vue'
import Voc from '../views/Board/Voc.vue'
import Lesson from '../views/Lesson.vue'
import UpdateLecture from '../views/UpdateLecture.vue'

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
        path: "userinfo",
        name: "user-info",
        component: () => import("@/components/mypage/user/Info.vue")
      },
      {
        path: "userlecture",
        name: "user-lecture",
        component: () => import("@/components/mypage/user/Lecture.vue")
      },
      {
        path: "userlike",
        name: "user-like",
        component: () => import("@/components/mypage/user/Like.vue")
      },
      {
        path: "userfeedback",
        name: "user-feedback",
        component: () => import("@/components/mypage/user/Feedback.vue")
      },
      {
        path: "userpractice",
        name: "user-practice",
        component: () => import("@/components/mypage/user/Practice.vue")
      },
      {
        path: "tutorinfo",
        name: "tutor-info",
        component: () => import("@/components/mypage/tutor/Info.vue")
      },
      {
        path: "tutorlecture",
        name: "tutor-lecture",
        component: () => import("@/components/mypage/tutor/Lecture.vue")
      },
      {
        path: "tutorfeedback",
        name: "tutor-feedback",
        component: () => import("@/components/mypage/tutor/Feedback.vue")
      },
      {
        path: "tutorschedule",
        name: "tutor-schedule",
        component: () => import("@/components/mypage/tutor/Schedule.vue")
      },
      {
        path: "tutoraddlecture",
        name: "tutor-addlecture",
        component: () => import("@/components/mypage/tutor/AddLecture.vue")
      },
    ]
  },
  {
    path: '/class/All',
    name: 'Class',
    component: Class
  },
  {
    path: '/user/update',
    name: 'updateuser',
    component: UpdateInfoUser,
  },
  {
    path: '/tutor/update',
    name: 'updatetutor',
    component: UpdateInfoTutor,
  },
  {
    path: '/detail/:cid',
    name: 'Detail',
    component: ClassDetail,
    children: [
      {
        path: "intro",
        name: "Intro",
        component: () => import("@/components/class/detail/Introduction.vue")
      },
      {
        path: "curriculum",
        name: "Curriculum",
        component: () => import("@/components/class/detail/Curriculum.vue")
      },
      {
        path: "review",
        name: "Review",
        component: () => import("@/components/class/detail/Review.vue")
      },
      {
        path: "feedback",
        name: "Feedback",
        component: () => import("@/components/class/detail/Feedback.vue")
      },
      {
        path: "qna",
        name: "QnA",
        component: () => import("@/components/class/detail/QnA.vue")
      },
    ]
  },
  {
    path: '/lesson',
    name: 'Lesson',
    component: Lesson
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
  {
    path: '/board',
    name: 'Board',
    component: Board,
    children: [
      {
        path: "",
        name: "boardlist",
        component: () => import("@/components/Board/Boardlist.vue")
      },
      {
        path: "detail/:articleid",
        name: "boardview",
        component: () => import("@/components/Board/Detail.vue")
      },
      {
        path: "register",
        name: "boardregister",
        component: () => import("@/components/Board/Register.vue")
      }
    ],
    redirect: () => {
      return "/board";
    }
  },
  {
    path: '/voc',
    name: 'voc',
    component: Voc,
    children: [
      {
        path: "/notice",
        name: "notice",
        component: () => import("@/components/Board/QnA/list.vue")
      }, {
        path: "/notice/:articleid",
        name: "noticeview",
        component: () => import("@/components/Board/Detail.vue")
      }
      ,
      {
        path: "/QnA",
        name: "voc-QnA",
        component: () => import("@/components/Board/QnA/QnA.vue")
      }, {
        path: "/Qlist",
        name: "qlist",
        component: () => import("@/components/Board/QnA/Questionlist.vue")
      }

    ],
    redirect: () => {
      return "/voc";
    }
  },
  {
    path: "/updatelecture",
    name: "updatelecture",
    component: UpdateLecture
  }
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
