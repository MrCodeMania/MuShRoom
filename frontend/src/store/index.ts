import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isStudent: false, // false = student, true = tutor
    isLogin: false,
    userInfo: null
  },
  mutations: {
    setType(state, isStudent) {
      state.isStudent = isStudent;
    },
    setIsLogin(state, isLogin) {
      state.isLogin = isLogin;
    },
    setUserInfo(state, userInfo) {
      state.isLogin = true;
      state.userInfo = userInfo;
    }
  },
  actions: {
  },
  modules: {
  }
})
