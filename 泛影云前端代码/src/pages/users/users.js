import Vue from 'vue'
import Users from './Users.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueRouter from 'vue-router'
import router from '../../router/routers.js'
import Vuex from 'vuex'
import store from '../../store/store.js'
import axios from 'axios'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)
Vue.prototype.$axios = axios

new Vue({
  router: router,
  store: store,
  render: h => h(Users)
}).$mount('#users')
