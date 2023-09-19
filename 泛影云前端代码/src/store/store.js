import Vue from 'vue'
import Vuex from 'vuex'
// import print from './print.js'
import createLogger from 'vuex/dist/logger'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
  // modules: {
  //   print
  // },
  state: {
    user: JSON.parse(sessionStorage.getItem('userData')) || {
      email:'',
      password:'',
      type:3,
      name:'',
      idnum:'',
      physician:'',
      physicians:'',
      phone:'',
      age:'',
      sex:'',
      unit:'',
      description:'',
      description1:'',
      image:null,
      isdone:2,
      file:''
    }// 用户信息
  },
  mutations: {
      // 设置用户信息
    setUser(state, val) {
      sessionStorage.setItem('userData', JSON.stringify(val));
      state.user = val;
    }
  },
  actions: {
      // 更新用户信息
    updateUser({ commit }, val) {
      commit('setUser', val);
    }
  },
  strict: debug,
    plugins: debug ? [createLogger()] : []
})