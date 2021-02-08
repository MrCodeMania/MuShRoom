import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    url: "",
    fileName: "",
    myName: "여기에 이름",
    recordStartState: false,
  },
  mutations: {
    pushURL(state, e = "") {
      state.url = e;
    },
    pushName(state, name = "") {
      state.fileName= name
    },
    setRC(state, start="") {
      state.recordStartState = start
    }
  },
  getters: {
    getURL(state) {
      return [state.url, state.fileName];
    },
    getRC(state) {
      return state.recordStartState;
    }
  },
  actions: {
  },
  modules: {
  }
})
