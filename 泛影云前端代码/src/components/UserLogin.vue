<template>
<div class="login">
<!--  <form> -->
    <p>登录</p>
    <hr />
    <input type="text" name="username" placeholder="邮箱或手机号" v-model="name" @blur="recover"/><br /><!-- <i class="fa fa-check" v-if="nform==1" style="color: green;" ></i> --><i class="fa fa-times" v-show="nform==0" style="color: red;" ></i>
    <input type="password" name="password" placeholder="密码" v-model="password" showpassword @blur="recover"/><!-- <i class="fa fa-check" v-if="pform==1" style="color: green;" ></i> --><i class="fa fa-times" v-show="pform==0" style="color: red;" ></i>
    <br />
    <a href="./register.html">用户注册</a>
    <a href="./password.html" id="top">忘记密码</a>
    <br />
    <br />
    <input type="radio" name="identity" v-model="type" value="0" @mousedown="recover()"/>患者
    <input type="radio" name="identity" v-model="type" value="1" @mousedown="recover()"/>医生
    <input type="radio" name="identity" v-model="type" value="2" @mousedown="recover()"/>专家
    <i class="fa fa-times" v-show="tform==0" style="color: red;" ></i>
    <br />
    <!-- <i class="fa fa-check" v-if="tform==1" style="color: green;" ></i> -->
    <br />
    <input type="text" name="safeCode" placeholder="验证码(点击更换)" id="safeCode" v-model="code" @blur="recover"/><i class="fa fa-check" v-if="cform==1" style="color: green;" ></i><i class="fa fa-times" v-show="cform==0" style="color: red;" ></i>
    <ValidCode ref="childComponent"></ValidCode><br />
   <!-- <input type="checkbox" v-model="agree"/>阅读并接受<a href="#">《用户协议》</a> -->
    <input type="submit" value="登录" @click="login" />
<!--  </form> -->
</div>
</template>

<script>
  import ValidCode from './ValidCode.vue'
  var baseURLStr = window.g.baseURL
  var backendPort = window.g.backendPort

  export default{
    name:'UserLogin',
    components:{
      ValidCode
    },
    data(){
      return{
          name:'',
          password:'',
          type:0,
          code:'',
          nform:'2',
          pform:'2',
          tform:'2',
          cform:'2'
      }
    },
    methods:{
      recover(){
        this.nform = 2;
        this.pform = 2;
        this.cform = 2;
        this.tform = 2;
      },
      login(){
        if(this.name=="admin"&&this.password=="admin"){
          this.type=3;
        }
        if(this.code.toLowerCase()==this.$refs.childComponent.identifyCode.toLowerCase())
        {
          this.$axios({
            method: 'post',
            url:baseURLStr+backendPort+"/login",
            data: {
              phone : this.name,
              password : this.password,
              type: this.type
            }
          }).then(res =>{
            if(res.data.flag){
              this.$store.commit("setUser", {user: res.data.data});
              window.location.href = "./users.html";
            }
            else {
              if(res.data.data=="0"){
                this.nform = 0;
              }else if(res.data.data=="1"){
                this.pform = 0;
              }else if(res.data.data=="2"){
                this.tform = 0;
              }else {
                this.pform = 0;
                this.tform = 0;
              }
            }
          });
        }
        else{
          this.cform = 0;
        }  
      }
    }
}
</script>

<style>
  body{
    background-image: url(../assets/10.jpg);
    background-repeat: no-repeat ;
    height: 100%;
    width: 100%;
    overflow: hidden;
    background-size: cover;
    background-attachment: fixed;
    overflow-y:auto;
    overflow-x:auto
  }
.login{
  position: absolute;
  top: 50%;
  margin-top: -300px;
  left: 50%;
  margin-left: -250px;
  background-color: whitesmoke;
  width: 500px;
/*  height: 450px; */
  border-radius: 25px;
  padding: 5px 40px;
  box-sizing: border-box;
}
p{
  font-size: 42px;
  font-weight: 600;
  height: 20px;
  text-align: center;
}
input[type=checkbox]{
  margin-top: 20px;
}
#top{
  float: right;
  margin-top: 5px;
  margin-left: 5px;
}
a:link{text-decoration: none;color: black;}
a:hover{color: pink;}
a:active{color: skyblue;}
input[type=text],input[type=password]{
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  -webkit-transition: 0.5s;
  transition: 0.5s;
  outline: none;
}
#safeCode{
  width: 50%;
}

input[type=radio]{
  width: 20%;
  text-align: left;
}

input[type=text]:focus,[type=password]:focus{
  border: 3px solid #555;
}

input[type=submit]{
  width: 100%;
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
  margin-bottom: 15px;
}

input[type=submit]:hover {
  background-color: #45a049;
}
</style>
