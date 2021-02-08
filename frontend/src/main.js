import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueWaveSurfer from 'vue-wave-surfer';

Vue.config.productionTip = false

Vue.use(VueWaveSurfer);

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')