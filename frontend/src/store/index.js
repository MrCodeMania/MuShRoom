import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    url:"",
  },
  mutations: {
    pushURL(state,e=""){
      state.url=e;
    }
  },
  getters: {
    getURL(state) {
      return state.url;
    }
  },
  actions: {
  },
  modules: {
  }
})
