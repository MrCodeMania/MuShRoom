import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueWaveSurfer from 'vue-wave-surfer';
import VueMyToasts from 'vue-my-toasts'
import 'vue-my-toasts/dist/vue-my-toasts.css'
import Notification from "./components/Notification.vue";

Vue.config.productionTip = false

Vue.use(VueWaveSurfer);
Vue.use(VueMyToasts, {
  component: Notification,
  options: {
    width: '400px',
    position: 'bottom-right',
    padding: '1rem'
  }
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')