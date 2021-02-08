import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    url: "",
    fileName: "",
    myName: "이병희",
    status:""
  },
  mutations: {
    pushURL(state, e = "") {
      state.url = e;
    },
    pushName(state, name = "") {
      state.fileName = name
    },
    pushStatus(state, status){
      state.status = status;
      console.log(state.status);
    }
  },
  getters: {
    getURL(state) {
      return [state.url, state.fileName];
    },
    getStatus(state){
      return state.status;
    }
  },
  actions: {
  },
  modules: {
  }
})
