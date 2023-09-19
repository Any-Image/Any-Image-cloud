import Vue from 'vue'
import Register from './Register.vue'
import axios from 'axios'

Vue.config.productionTip = false
Vue.prototype.$axios = axios

new Vue({
  axios,
  render: h => h(Register)
}).$mount('#register')
