import Vue from 'vue'
import Login from './Login.vue'
import axios from 'axios'
import Vuex from 'vuex'
import store from '../../store/store.js'
// import VueRouter from 'vue-router'
// import router from '../../router/routers.js'

Vue.config.productionTip = false
Vue.prototype.$axios = axios
// Vue.use(VueRouter)
Vue.use(Vuex)

new Vue({
  // router: router,
  store: store,
  render: h => h(Login)
}).$mount('#login')
