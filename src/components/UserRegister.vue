<template>
<div class="container">  
<!--  <form action="#" @submit.prevent="demo"> -->
    <p>注册</p>
    <hr />
    <div class="row">
      <div class="col-25">
        <label for="name">姓名</label>   
      </div>
      <div class="col-75">
        <input type="text" id="name" name="username" placeholder="姓名" v-model="name" @blur="fnameForm"/><i class="fa fa-check" v-if="nform==1" style="color: green;" ></i><i class="fa fa-times" v-else-if="nform==0" style="color: red;" ></i>
      </div>
    </div>
    <div class="row">
      <div class="col-25">
        <label for="uid">身份证号</label>
      </div>
      <div class="col-75">
        <input type="text" id="uid" name="idnum" placeholder="身份证号码" v-model="idnum" @blur="fidForm"/><i class="fa fa-check" v-if="iform==1" style="color: green;" ></i><i class="fa fa-times" v-else-if="iform==0" style="color: red;" ></i>
      </div>
    </div>
    <div class="row">
      <div class="col-25">
        <label for="contact">联系方式</label>
      </div>
      <div class="col-75">
        <input type="text" id="contact" name="phonenum" placeholder="邮箱" v-model="email" @blur="femailForm"/><i class="fa fa-check" v-if="eform==1" style="color: green;" ></i><i class="fa fa-times" v-else-if="eform==0" style="color: red;" ></i>
      </div>
    </div>
    <div class="row">
      <div class="col-25">
        <label for="up">密码</label>
      </div>
      <div class="col-75">
        <input type="password" id="up" name="password" placeholder="设置您的密码(6~16位数字加字母)" v-model="password" @blur="fpForm"/><i class="fa fa-check" v-if="pform==1" style="color: green;" ></i><i class="fa fa-times" v-else-if="pform==0" style="color: red;" ></i>
      </div>
    </div>
    <div class="row">
      <div class="col-25">
        <label for="up1">确认密码</label>
      </div>
      <div class="col-75">
        <input type="password" id="up1" name="password1" placeholder="再次输入密码" v-model="password1" @blur="fp1Form"/><i class="fa fa-check" v-if="p1form==1" style="color: green;" ></i><i class="fa fa-times" v-else-if="p1form==0" style="color: red;" ></i>
      </div>
    </div>
    <input type="radio" name="identity" v-model="type" value="0" />患者
    <input type="radio" name="identity" v-model="type" value="1" />医生
    <input type="radio" name="identity" v-model="type" value="2" />专家
    <div class="row">
      <div class="col-25">
        <label for="safeCode">验证码</label>
      </div>
      <div class="col-75">
        <input type="text" id="safeCode" name="safeCode" placeholder="验证码" v-model.number="code" @blur="fcForm" />
        <button @click="dx_yzm_bt" v-show="is_dx_yz" style="float: right; color: #008000; margin-top: 15px;">点我获取验证码</button>
        <button v-show="!is_dx_yz" style="float: right; color: #008000; margin-top: 15px;">{{time}}秒后重新获取</button>
        <br />
        <i class="fa fa-check" v-if="cform==1" style="color: green;" ></i>
        <i class="fa fa-times" v-else-if="cform==0" style="color: red;" ></i>
      </div>
    </div>
    <input type="checkbox" v-model="agree" @blur="faForm"/>阅读并接受<a href="#">《用户协议》</a>
    <i class="fa fa-check" v-if="aform==1" style="color: green;" ></i>
    <i class="fa fa-times" v-else-if="aform==0" style="color: red;" ></i>
    <div class="row">
      <input type="submit" value="注册" @click="register">
    </div>
<!--  </form> -->
</div>
</template>

<script>
  var baseURLStr = window.g.baseURL
  var backendPort = window.g.backendPort

  export default{
    name:'UserRegister',
    data(){
      return{
        name:'',
        idnum:'',
        email:'',
        type:0,
        password:'',
        password1:'',
        code:'',
        agree:'',
        is_dx_yz:true,
        time:0,
        nform:2,
        pform:2,
        p1form:2,
        iform:2,
        cform:2,
        eform:2,
        aform:2,
        code1:''
      }
    },
    methods:{
      dx_yzm_bt(){
        this.is_dx_yz=false;
        this.$axios({
          method: 'post',
          url: baseURLStr+backendPort+"/register/code",
          data: {
            email : this.email
          }
        }).then(res => {
          if(res.data.flag){
            this.code1 = res.data.data;
          }else{
            alert("该邮箱已被使用！")
            this.eform = 0;
          }
        });
        this.time = 60;
        let dx_djs=setInterval(()=>{
          if ((this.time--) <= 0) {
            this.is_dx_yz = true;
            clearInterval(dx_djs);
          }
        },1000)
      },
      fnameForm(){
        let reg = /^[\u2E80-\u9FFF]+$/;  
        //正则表达式 ，1代表手机号的第一位1 ，[0-9]{10}代表后面10个数字，在0-9里面随机
        if (!reg.test(this.name)) { //！就代表当不符合这个规则
            this.nform = 0;
        }
        else {
            this.nform = 1;
        }
      },
      fidForm(){
        let reg = /^([1-6][1-9]|50)\d{4}(18|19|20)\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
        //正则表达式 ，1代表手机号的第一位1 ，[0-9]{10}代表后面10个数字，在0-9里面随机
        if (!reg.test(this.idnum)) { //！就代表当不符合这个规则
            this.iform = 0;
        }
        else {
            this.iform = 1;
        }
      },
      femailForm(){
        let reg = /^[A-Za-z0-9-._]+@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,6})$/;  
        //正则表达式 ，1代表手机号的第一位1 ，[0-9]{10}代表后面10个数字，在0-9里面随机
        if (!reg.test(this.email)) { //代表当符合这个规则
            this.eform = 0;
        }
        else {
            this.eform = 1;
        }
      },
      fpForm(){
        let reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
        if(!reg.test(this.password)){
          this.pform = 0;
        }else{
          this.pform = 1;
        }
      },
      fp1Form(){
        if(this.password!=this.password1||!this.pform){
            this.p1form = 0;
          }
          else{
            this.p1form = 1;
          }
      },
      fcForm(){
        if(this.code!=0){
          this.cform = 1;
        }
        else{
          this.cform = 0;
        }
      },
      faForm(){
        if(!this.agree){
          this.aform = 0;
        }else{
          this.aform = 1;
        }
      },
      register(){
        // console.log(this.agree);
        // console.log(this.code==this.code1);
        // console.log(this.nform==1);
        // console.log(this.pform==1);
        // console.log(this.p1form==1)
        // console.log(this.iform==2);
        // console.log(this.cform==1);
        // console.log(this.eform==1);
        // console.log(this.aform==1);
        if(this.agree&&this.code==this.code1&&this.nform==1&&this.pform==1&&this.p1form==1&&this.iform==1&&this.cform==1&&this.eform==1&&this.aform==1){
          this.$axios({
            method: 'post',
            url: baseURLStr+backendPort+"/register",
            data: {
              name: this.name,
              password : this.password,
              type: this.type,
              email: this.email,
              idnum: this.idnum
            }
          }).then(res =>{
            if(res.data.flag){
              window.location.href = "./login.html";
            }
          });
        }else if(this.code!=this.code1){
          this.cform = 0;
        }else if(!this.agree){
          this.aform = 0;
        }
      }
    }
  }
</script>

<style>
* {
    box-sizing: border-box;
}
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

#safeCode{
  width: 50%;
}

.container{
  position: absolute;
  top: 50%;
  margin-top: -350px;
  left: 50%;
  margin-left: -250px;
  background-color: aliceblue;
  width: 550px;
/*  height: 500px; */
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

input[type=text],[type=password] {
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
  resize: vertical;
}

input[type=text]:focus{
  border: 3px solid #555;
}

input[type=password]:focus{
  border: 3px solid #555;
}

input[type=radio]{
  width: 17%;
  margin-top: 20px;
  margin-bottom: 30px;
}

input[type=checkbox]{
  margin-top: 20px;
}

label {
  padding: 12px 12px 12px 0;
  display: inline-block;
}

input[type=submit] {
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

.col-25 {
  float: left;
  width: 25%;
  margin-top: 6px;
}

.col-75 {
  float: left;
  width: 75%;
  margin-top: 6px;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}
a:link{text-decoration: none;}
/* 响应式布局 - 当屏幕的宽度小于 600 像素时，使两列相互堆叠而不是彼此并排。 */
/*@media screen and (max-width: 600px) {
  .col-25, .col-75, input[type=submit] {
    width: 100%;
    margin-top: 0;
  }
} */
</style>
