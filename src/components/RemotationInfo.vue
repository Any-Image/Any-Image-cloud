<template>
  <div class="container">
    <div class="table">
      <el-descriptions class="margin-top" title="详细信息" :column="2" :size="size" border>
        <template slot="extra" v-if="disable1&&id==2" >
          <el-button type="primary" size="small" @click="update">修改</el-button>
          <el-button type="danger" size="small" @click="back">返回</el-button>
        </template>
        <template slot="extra" v-if="!disable1&&id==2">
          <el-button type="primary" size="small" @click="update1">保存</el-button>
          <el-button type="danger" size="small" @click="back">返回</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user-solid"></i>
            会诊名称
          </template>
          <el-input
            v-model="User.name"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            申请人
          </template>
          <el-input
            type="tel"
            v-model="User.idnum"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-date"></i>
            申请单位
          </template>
          <el-input
            type="tel"
            v-model="User.sex"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user-solid"></i>
            初诊意见
          </template>
          <el-input
            type="textarea"
            autosize
            v-model="User.email"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            主述
          </template>
          <el-input
            type="textarea"
            autosize
            v-model="User.description"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            病历
          </template>
          <el-input
            type="textarea"
            autosize
            v-model="User.description1"
            :disabled="disable1">
          </el-input>
        </el-descriptions-item>
      </el-descriptions>
      <br /><br />
	<el-descriptions class="margin-top" title="影像文件" :column="1" :size="size" border>
		<template slot="extra">
          <el-button type="primary" size="small" @click="back" v-if="id==1">返回</el-button>
        </template>
    <el-descriptions-item>
			<template slot="label">
				<i class="el-icon-user-solid"></i>
					影像文件
			</template>
			<el-button type="primary" size="small" @click="checkImages" style="margin-left: 53%;">查看影像</el-button>
		</el-descriptions-item>
	</el-descriptions>
    </div>
  </div>
</template>

<script>  
  var baseURLStr = window.g.baseURL
  var backendPort = window.g.backendPort
  var airPort = window.g.airPort
  
  export default {
    name:'UserMine',
    data () {
      return {
        User:{},
        disable1:true,
        disable:true,
        size: '',
        id:0
      };
    },
    mounted() {
      this.$axios.get(baseURLStr+backendPort+"/remotations/"+this.$route.query.file).then(res=>{
        if(res.data.flag){
          this.User = res.data.data;
        }
      });
      let user = sessionStorage.getItem("userData");
        // 将JSON格式的对象解析为js对象
        this.currentUser= JSON.parse(user);
        this.id = this.currentUser.user.type;
    },
    methods:{
      back(){
        this.$router.go(-1);
      },
      update(){
        this.disable1 = false;
      },
      update1(){
        this.disable1 = true;
          this.$axios({
            method: 'put',
            url: baseURLStr+backendPort+"/remotations/info"+"/"+this.User.file,
            data:{
              email:this.User.email,
              description:this.User.description,
              description1:this.User.description1
            } 
          }).then(res =>{
            if(res.data.flag){
              this.$message.success("提交成功！");
              // this.$axios({
              //   method: 'get',
              //   url: "http://localhost:80/users"+"/"+this.User.phone,
              // }).then(res =>{
              //   if(res.data.flag){
              //     this.User = res.data.data;
              //     this.$store.commit("setUser", {user: res.data.data});
              //   }
              // }).catch( e => {
              //   if(e.response){
              //   //请求已发出，服务器返回状态码不是2xx。
              //     console.info(e.response.data)
              //     console.info(e.response.status)
              //     console.info(e.response.headers)
              //   }else if(e.request){
              //     // 请求已发出，但没有收到响应
              //     // e.request 在浏览器里是一个XMLHttpRequest实例，
              //     // 在node中是一个http.ClientRequest实例
              //     console.info(e.request)
              //   }else{
              //     //发送请求时异常，捕捉到错误
              //     console.info('error',e.message)
              //   }
              //   console.info(e.config)
              // });
            }
            else{
              this.$message.error("提交失败！");
            }
          })
          // .catch( e => {
          //   if(e.response){
          //   //请求已发出，服务器返回状态码不是2xx。
          //     console.info(e.response.data)
          //     console.info(e.response.status)
          //     console.info(e.response.headers)
          //   }else if(e.request){
          //     // 请求已发出，但没有收到响应
          //     // e.request 在浏览器里是一个XMLHttpRequest实例，
          //     // 在node中是一个http.ClientRequest实例
          //     console.info(e.request)
          //   }else{
          //     //发送请求时异常，捕捉到错误
          //     console.info('error',e.message)
          //   }
          //   console.info(e.config)
          // });
      },
      checkImages(){
        window.open(baseURLStr+airPort+"/oviyam2/viewer.html?"+this.User.file+"&serverName=DCM4CHEE");
      }
    }
  }
</script>

<style>
  .container{
    width: 95%;
    margin-left: 2%;
    border-radius: 20px;
    border-top: 3px solid #3c8dbc;
    box-shadow: 0 1px 1px rgb(0, 0, 0 / 10%);
    margin-top: 20px;
  }
  .table{
    width: 92%;
    margin-top: 10px;
    margin-left: 3%;
  }
  .block{
    margin-left: 50%;
  }
</style>