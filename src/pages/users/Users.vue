<template>
<div id="user">
  <div id="nav">
    <ul>
      <li id="image"><img src="../../assets/icons/AICWORD@4x.png"/><!-- <br /><strong>影像云</strong><br /> --></li>
      <br />
      <li v-show="User.type!=3" @click="checkMp" ><router-link to="/mine" active-class="active"><i class="fa fa-user">&nbsp;&nbsp;我的信息</i></router-link></li>
      <li v-show="User.type!=2" ><router-link to="/experts" active-class="active"><i class="fa fa-id-badge">&nbsp;&nbsp;影像专家</i></router-link></li>
      <li v-show="User.type==3" ><router-link to="/doctors" active-class="active"><i class="fa fa-id-badge">&nbsp;&nbsp;普通医生</i></router-link></li>
      <li v-show="User.type==2||User.type==3" @click="checkMe" ><router-link to="/patients0" active-class="active"><i class="fa fa-address-book-o">&nbsp;&nbsp;未诊患者</i></router-link></li>
      <li v-show="User.type==2||User.type==3" @click="checkMd" ><router-link to="/patients1" active-class="active"><i class="fa fa-check">&nbsp;&nbsp;已诊患者</i></router-link></li>
      <li v-show="User.type!=0" @click="checkMe" ><router-link to="/remotation0" active-class="active"><i class="fa fa-id-badge">&nbsp;&nbsp;远程会诊</i></router-link></li>
      <li v-show="User.type!=0" @click="checkMd"><router-link to="/remotation1" active-class="active"><i class="fa fa-id-badge">&nbsp;&nbsp;会诊结果</i></router-link></li>
      <li v-show="User.type!=3"><router-link to="/aicheck" active-class="active"><i class="fa fa-cloud">&nbsp;&nbsp;智能诊断</i></router-link></li>
      <!-- <li v-show="User.type!=0&&User.type!=3"><a href="http://localhost:8080/dcm4chee-web3/"><i class="fa fa-image">&nbsp;&nbsp;影像工具</i></a></li> -->
    </ul>
  </div>
  <div id="userTop">
    <h1><em>欢迎使用泛影云</em></h1>
    <ul>
      <li id="rightInfo"><i class="fa fa-power-off fa-2x" @click="logout"></i></li>
      <li id="rightInfo"><router-link to="/mine">{{User.name}}</router-link></li>
      <li id="rightInfo"><i class="fa fa-user-o fa-2x" ></i></li>
      <li id="rightInfo"><a href="#">{{work}}</a></li>
      <li id="rightInfo"><img src="../../assets/icons/message.png" v-show="User.isdone==1"/><i class="fa fa-bell fa-2x"></i></li>
      <li id="rightInfo"><a href="#">消息</a></li>
    </ul>
  </div>
  <div id="userBody">
    <keep-alive><router-view v-if="$route.meta.keepAlive"></router-view></keep-alive>
    <router-view v-if="!$route.meta.keepAlive"></router-view>
  </div>
</div>
</template>

<script>
import { Image } from 'element-ui';
var baseURLStr = window.g.baseURL
var backendPort = window.g.backendPort
  var dcm4cheePort = window.g.dcm4cheePort
  var airPort = window.g.airPort

export default {
    name: "Users",
    data() {
        return {
            User: {},
            works: ["患者", "医生", "专家", "admin"],
            work: "",
            color: "black",
            // tip:'0'
        };
    },
    mounted() {
        let user = sessionStorage.getItem("userData");
        if (user != null) {
            // 将JSON格式的对象解析为js对象
            let currentUser = JSON.parse(user);
            this.User = currentUser.user;
        }
        if (!this.User.type) {
            this.work = this.works[0];
        }
        else if (this.User.type == 1) {
            this.work = this.works[1];
        }
        else if (this.User.type == 2) {
            this.work = this.works[2];
        }
        else {
            this.work = this.works[3];
        }
        this.update();
      },
    
      methods: {
      update(){
        this.$axios({
          method: 'get',
          url:baseURLStr+backendPort+"/users/s/"+this.User.phone,
        }).then(res=>{
          if(res.data.flag){
            this.User = res.data.data;
            this.$store.commit("setUser", {user: res.data.data});
          }
        })
        this.mounted;
        setTimeout(()=>{
          this.update();
        },30000)
      },
      // update(){
      //   this.$axios({
      //       method: 'get',
      //       url:baseURLStr+"/users/s/"+this.User.phone,
      //     }).then(res =>{
      //       if(res.data.flag){
      //         if(res.data.data.isdone != this.User.isdone){
      //           this.$store.commit("setUser", {user: res.data.data});
      //           this.reload();
      //           // this.mounted();
      //         }
      //         setTimeout(()=>{
      //             this.update();
      //           },10000)
      //     }})
      // },
      checkMp(){
        if(this.User.type==0&&this.User.isdone==1){
          this.$axios({
            method: 'get',
            url:baseURLStr+backendPort+"/users/m/"+this.User.phone,
          }).then(res =>{
            if(res.data.flag){
              this.User = res.data.data;
              this.$store.commit("setUser", {user: res.data.data});
            }
          })
        }
        // this.mounted;
        setTimeout(()=>{
          window.location.reload();
          },2000)
      },
      checkMe(){
        if(this.User.type==2&&this.User.isdone==1){
          this.$axios({
            method: 'get',
            url:baseURLStr+backendPort+"/users/m/"+this.User.phone,
          }).then(res =>{
            if(res.data.flag){
              if(res.data.flag){
                this.User = res.data.data;
                this.$store.commit("setUser", {user: res.data.data});
              }
            }
          })
        }
      },
      checkMd(){
        if(this.User.type==1&&this.User.isdone==1){
          this.$axios({
            method: 'get',
            url:baseURLStr+backendPort+"/users/m/"+this.User.phone,
          }).then(res =>{
            if(res.data.flag){
              if(res.data.flag){
                this.User = res.data.data;
                this.$store.commit("setUser", {user: res.data.data});
              }
            }
          })
        }
      },
      logout() {
          sessionStorage.clear();
          window.location.href = "/home.html";
      }
    },
    components: { Image }
}
</script>

<style>
  * {
    margin: 0;
    -moz-user-select: none;
    -webkit-user-select: none;
    user-select: none;
    -o-user-select: none;
    -ms-user-select: none;
  }

  #nav ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    width: 15%;
    position: fixed;
    height: 100%;
    overflow: auto;
    font-size: 25px;
    text-align: center;
  }

  #nav img{
    width: 100%;
    margin-top: 10%;
    vertical-align: middle;
  }

  #nav li a{
    height: 35px;
    display: block;
    color: #000000;
    padding: 8px 16px;
    text-decoration: none;
  }

  #nav li a:hover:not(.active) {
    background-color: #000000;
    color: #FFFFFF;
  }

  .active{
    background-color: rgb(0, 153, 255);
    color: #FFFFFF;
  }

  #nav i{
    position: relative;
  }

  #userTop{
    border: 1px;
    height: 100px;
    margin-left: 154px;
  }

  h1{
    font: "宋体";
    font-size: 40px;
    margin-top: 15px;
    text-align: center;
  }

  #userTop ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    margin-left: 300px;
  }

  #rightInfo{
    float: right;
    margin-right: 20px;
    margin-top: -8px;
  }

  #rightInfo i{
    color: black;
    margin-top: 8px;
  }
  #userTop li {
    float: right;
    text-align: center;
  }
  #userTop a{
    display: inline-block;
    color: black;
    text-align: center;
    padding: 12px 6px;
    text-decoration: none;
  }
  #userTop li a:hover {
    background-color: #FFFFFF;
  }

  #userBody{
    height: 100%;
    position: static;
    margin-left: 15%;
  }
</style>