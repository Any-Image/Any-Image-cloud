// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true
// })
module.exports = {
  pages:{
    home:{
      entry:"./src/pages/home/home.js",
      template:"./src/pages/home/home.html",
      filename:"home.html",
      title:"影像云"
    },
    edge:{
      entry:"./src/pages/edge/edge.js",
      template:"./src/pages/edge/edge.html",
      filename:"edge.html",
      title:"选择边缘端-影像云"
    },
    login:{
      entry:"./src/pages/login/login.js",
      template:"./src/pages/login/login.html",
      filename:"login.html",
      title:"登录-影像云"
    },
    register:{
      entry:"./src/pages/register/register.js",
      template:"./src/pages/register/register.html",
      filename:"register.html",
      title:"注册-影像云"
    },
    password:{
      entry:"./src/pages/password/password.js",
      template:"./src/pages/password/password.html",
      filename:"password.html",
      title:"找回密码-影像云"
    },
    users:{
      entry:"./src/pages/users/users.js",
      template:"./src/pages/users/users.html",
      filename:"users.html",
      title:"欢迎"
    }
  },
  lintOnSave:false,
}