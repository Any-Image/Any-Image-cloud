<template>
  <div class="container">
    <div class="table">
      <el-descriptions class="margin-top" title="详细信息" :column="2" :size="size" border>
        <template slot="extra" v-if="disable1&&User.type==0" >
          <el-button type="primary" size="small" @click="update">修改</el-button>
          <el-button type="danger" size="small" @click="back">返回</el-button>
        </template>
        <template slot="extra" v-if="!disable1&&User.type==0">
          <el-button type="primary" size="small" @click="update1">保存</el-button>
          <el-button type="danger" size="small" @click="back">返回</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user-solid"></i>
            姓名
          </template>
          <el-input
            v-model="User.name"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            性别
          </template>
          <el-input
            type="tel"
            v-model="User.sex"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-date"></i>
            年龄
          </template>
          <el-input
            type="tel"
            v-model="User.age"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user-solid"></i>
            邮箱
          </template>
          <el-input
            type="email"
            v-model="User.email"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            手机号
          </template>
          <el-input
            type="tel"
            v-model="User.phone"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item v-if="User.type!=0">
          <template slot="label">
            <i class="el-icon-office-building"></i>
            工作单位
          </template>
          <el-input
            v-model="User.unit"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item v-if="User.type==0">
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            居住地
          </template>
          <el-input
            v-model="User.unit"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item v-if="User.type==0">
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
        <el-descriptions-item v-if="User.type==0">
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
        <el-descriptions-item v-if="User.type!=0">
          <template slot="label">
            <i class="el-icon-tickets"></i>
            个人简介
          </template>
          <el-input
            type="textarea"
            autosize
            v-model="User.description"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
      </el-descriptions>
      <br /><br />
      <el-descriptions class="margin-top" title="疾病相关照片" :column="1" :size="size" border v-if="User.type==0">
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-picture"></i>
            疾病相关照片
          </template>
          <div class="demo-image__preview" v-if="src.length>24">
            <el-image 
            style="width: 100px;margin-left: 50%;"
            :src="src"
            :preview-src-list="srcList">
              <div slot="placeholder" class="image-slot">
                加载中<span class="dot">...</span>
              </div>
            </el-image>
          </div>
         <div style="text-align: center;" v-else>暂无照片</div>
        </el-descriptions-item>
      </el-descriptions>
      <el-descriptions class="margin-top" title="个人照片" :column="1" :size="size" border v-if="User.type!=0">
        <template slot="extra">
          <el-button type="primary" size="small" @click="back">返回</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-picture"></i>
            个人照片
          </template>
          <div class="demo-image__preview" v-if="src.length>24">
            <el-image 
            style="width: 100px;margin-left: 50%;"
            :src="src"  
            :preview-src-list="srcList">
              <div slot="placeholder" class="image-slot">
                加载中<span class="dot">...</span>
              </div>
            </el-image>
          </div>
         <div style="text-align: center;" v-else>暂无照片</div>
        </el-descriptions-item>
      </el-descriptions>
      <br />
	<el-descriptions class="margin-top" title="影像文件" :column="1" :size="size" border v-if="User.type == 0">
    <template slot="extra" v-if="disable">
          <el-button type="primary" size="small" @click="download" v-show="dicom">下载</el-button>
        </template>
		<el-descriptions-item>
			<template slot="label">
				<i class="el-icon-user-solid"></i>
					影像文件
			</template>
			<el-button type="primary" size="small" @click="checkImages" style="margin-left: 53%;" v-show="dicom">查看影像</el-button>
      <el-button type="primary" size="small" @click="checkImages" style="margin-left: 53%;" v-show="!dicom">无影像文件</el-button>
		</el-descriptions-item>
	</el-descriptions>
    </div>
  </div>
</template>

<script>  
  var baseURLStr = window.g.baseURL
  var airPort = window.g.airPort
  var backendPort = window.g.backendPort
  
  export default {
    name:'UserMine',
    data () {
      return {
        User:{},
        disable1:true,
        disable:true,
        size: '',
        src: 'data:image/jpeg;base64,',
        srcList:[],
        dicom: false
      };
    },
    mounted() {
      this.User.email = this.$route.query.email;
      this.$axios.get(baseURLStr+backendPort+"/users/"+this.User.email).then(res=>{
        if(res.data.flag){
          this.User = res.data.data;
          this.src += res.data.data.image;
          this.srcList[0] = this.src;
          if(this.User.file.length > 10){
            dicom =true
          }
        }
      });
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
            url: baseURLStr+backendPort+"/users/info"+"/"+this.User.phone,
            data:{
              email:this.User.email,
              password:this.User.password,
              idnum:this.User.idnum,
              phone:this.User.phone,
              age:this.User.age,
              sex:this.User.sex,
              unit:this.User.unit,
              description:this.User.description,
              description1:this.User.description1
            } 
          }).then(res =>{
            if(res.data.flag){
              this.$message.success("修改成功！");
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
              this.$message.error("修改失败！");
            }
          })
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
      },
      checkImages(){
        // window.location.href = "https://www.baidu.com"
        // this.$axios.get(baseURLStr+"/file/download/"+this.User.phone).then(res=>{
          // if(res.data.flag){
            // var iph = baseURLStr.substring(0,16);
            window.location.href = baseURLStr+airPort+"/oviyam2/viewer.html?"+this.User.file+"&serverName=DCM4CHEE";
        //   }else{
        //     this.$message.error("未查询到相关影像！");
        //   }
        // })
      },
      download(){
        var file = this.User.file;
        var array = file.split('&');
        var studyUID = array[1].substr(10);
        axios({
          method: 'GET',
          url: baseURLStr+dcm4cheePort+'/dcm4chee-arc/aets/DCM4CHEE/rs/studies' + studyUID,
        })
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