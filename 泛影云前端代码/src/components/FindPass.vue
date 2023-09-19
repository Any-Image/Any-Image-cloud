<template>
  <div class="container">  
    <p>找回密码</p>
    <hr />
    <div class="row">
      <div class="col-25">
        <label for="contact">邮箱</label>
      </div>
      <div class="col-75">
        <input type="text" id="contact" name="phonenum" placeholder="请输入邮箱" v-model="email" @blur="femailForm"/><i class="fa fa-check" v-if="eform==1" style="color: green;" ></i><i class="fa fa-times" v-else-if="eform==0" style="color: red;" ></i>
      </div>
    </div>
    <div class="row">
      <div class="col-25">
        <label for="up">新密码</label>
      </div>
      <div class="col-75">
        <input type="password" id="up" name="password" placeholder="设置您的密码" v-model="password" @blur="fpForm"/><i class="fa fa-check" v-if="pform==1" style="color: green;" ></i><i class="fa fa-times" v-else-if="pform==0" style="color: red;" ></i>
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
    <div class="row">
      <div class="col-25">
        <label for="safeCode">验证码</label>
      </div>
      <div class="col-75">
        <input type="text" id="safeCode" name="safeCode" placeholder="邮箱验证码" v-model.number="code" @blur="fcForm" />
        <button @click="dx_yzm_bt" v-show="is_dx_yz" style="float: right; color: #008000; margin-top: 15px;">点我获取验证码</button>
        <button v-show="!is_dx_yz" style="float: right; color: #008000; margin-top: 15px;">{{time}}秒后重新获取</button>
        <br />
        <i class="fa fa-check" v-if="cform==1" style="color: green;" ></i>
        <i class="fa fa-times" v-else-if="cform==0" style="color: red;" ></i>
      </div>
    </div>
    <div class="row">
      <input type="submit" value="确认修改" @click="findpass">
    </div>
  </div>
</template>

<script>
  var baseURLStr = window.g.baseURL
  var backendPort = window.g.backendPort
  
  export default{
    name:'FindPass',
    data(){
      return{
        email:'',
        password:'',
        password1:'',
        code:'',
        is_dx_yz:true,
        time:0,
        pform:2,
        p1form:2,
        cform:2,
        eform:2,
        code1:''
      }
    },
    methods:{
      dx_yzm_bt(){
        this.is_dx_yz=false;
        this.$axios({
          method: 'post',
          url:baseURLStr+backendPort+"/admin/code",
          data: {
            email : this.email
          }
        }).then(res => {
          if(res.data.flag){
            this.code1 = res.data.data;
          }else{
            alert("该邮箱未被注册！")
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
      femailForm(){
        let reg = /^[A-Za-z0-9-._]+@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,6})$/;  
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
        if(this.password!=this.password1){
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
      findpass(){
        if(this.code==this.code1&&this.pform==1&&this.p1form==1&&this.cform==1&&this.eform==1){
          this.$axios({
            method: 'put',
            url:baseURLStr+backendPort+"/admin/findpass/"+this.email,
            data: {
              password : this.password,
            }
          }).then(res =>{
            if(res.data.flag){
              this.$message.success("密码重置成功！即将跳转登录页面！");
              setTimeout(function(){
                window.location.href = "./login.html";
              },3000)
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
  margin-top: -300px;
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
/* 响应式布局 - 当屏幕的宽度小于 600 像素时，使两列相互堆叠而不是彼此并排。 */
/*@media screen and (max-width: 600px) {
  .col-25, .col-75, input[type=submit] {
    width: 100%;
    margin-top: 0;
  }
} */
</style>