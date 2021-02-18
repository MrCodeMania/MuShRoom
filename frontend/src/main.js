import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueWaveSurfer from 'vue-wave-surfer';
import Toast from "vue-toastification";
// Import the CSS or use your own!
import "vue-toastification/dist/index.css";
import VueCookies from "vue-cookies";


const truncate = function (text, length, clamp) {
  clamp = clamp || '...'
  const node = document.createElement('div')
  node.innerHTML = text
  const content = node.textContent
  return content.length > length ? content.slice(0, length) + clamp : content
}
Vue.filter('truncate', truncate)




Vue.config.productionTip = false
Vue.use(VueCookies);
Vue.use(VueWaveSurfer);
Vue.use(Toast, {
  transition: "Vue-Toastification__fade",
  maxToasts: 20,
  newestOnTop: true
});

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')