import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    url: "",
    fileName: "",
    myName: "이병희",
  },
  mutations: {
    pushURL(state, e = "") {
      state.url = e;
    },
    pushName(state, name = "") {
      state.fileName = name
    }
  },
  getters: {
    getURL(state) {
      return [state.url, state.fileName];
    }
  },
  actions: {
  },
  modules: {
  }
})
