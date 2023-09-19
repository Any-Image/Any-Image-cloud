<template>
  <div class="container">
    <div class="table">
      <el-descriptions class="margin-top" title="个人信息" :column="3" :size="size" border>
        <template slot="extra" v-if="disable">
          <el-button type="primary" size="small" @click="update">修改</el-button>
        </template>
        <template slot="extra" v-if="!disable">
          <el-button type="primary" size="small" @click="update1">保存</el-button>
        </template>
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
            <i class="el-icon-key"></i>
            密码
          </template>
          <el-input
            type="password"
            show-password
            v-model="User.password"
            :disabled="disable">
          </el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-bank-card"></i>
            身份证号
          </template>
          <el-input
            v-model="User.idnum"
            :disabled="disable1">
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
            :disabled="disable"
            @blur="fpForm">
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
        <el-descriptions-item v-if="User.type==0">
          <template slot="label">
            <i class="el-icon-user"></i>
            主治医生
          </template>
          <el-input
            v-model="doctor"
            :disabled="true">
          </el-input>
        </el-descriptions-item>
      </el-descriptions>
      <br />
      <el-descriptions class="margin-top" title="疾病相关照片" :column="1" :size="size" border v-if="User.type==0">
        <template slot="extra">
          <el-button type="primary" size="small" @click="handleCreate" v-show="!dis2">重新上传</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-picture"></i>
            疾病相关照片
          </template>
          <div class="demo-image__preview" v-if="src.length>279">
            <el-image 
            style="width: 100px;margin-left: 50%;"
            :src="src"
            :preview-src-list="srcList">
              <div slot="placeholder" class="image-slot">
                加载中<span class="dot">...</span>
              </div>
            </el-image>
          </div>
          <div class="demo-image__preview" v-else-if="src.length<279" style="margin-left: 30%;">
            <el-upload
              class="upload-demo"
              drag
              action=""
              :http-request="upload"
              multiple>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text" >将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip" >只能上传jpg/png文件,大小5MB以内</div>
            </el-upload>
          </div>
        </el-descriptions-item>
      </el-descriptions>
      <el-descriptions class="margin-top" title="个人照片" :column="1" :size="size" border v-if="User.type!=0">
        <template slot="extra">
          <el-button type="primary" size="small" @click="handleCreate" v-show="!dis2">重新上传</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-picture"></i>
            照片
          </template>
          <div class="demo-image__preview" v-if="src.length>279">
            <el-image 
            style="width: 100px;margin-left: 50%;"
            :src="src"  
            :preview-src-list="srcList">
              <div slot="placeholder" class="image-slot">
                加载中<span class="dot">...</span>
              </div>
            </el-image>
          </div>
          <div class="demo-image__preview" v-else-if="src.length<279" style="margin-left: 30%;">
            <el-upload
              class="upload-demo"
              drag
              action=""
              :http-request="upload"
              multiple>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text" >将照片拖到此处，或<em>点击上传</em></div>
            </el-upload>
          </div>
        </el-descriptions-item>
    </el-descriptions>
	<br />
	<el-descriptions class="margin-top" title="影像文件" :column="1" :size="size" border v-if="User.type == 0">
    <template slot="extra">
          <el-button type="primary" size="small" @click="update2" v-show="!dis">重新上传</el-button>
        </template>
		<el-descriptions-item>
			<template slot="label">
				<i class="el-icon-user-solid">影像文件</i>
			</template>
			<!-- http://localhost:80/upload" method="post" enctype="multipart/form-data" -->
			<div style="width: 300px;margin-left: 30%;">
			    <!-- <input type="file" id="uploadFile" value="请选择文件" @change="onFileChange" v-show="dis">
			    <input type="submit" value="上传" v-show="dis"> -->
          <input type="file" id="file" v-show="dis" @click="uploadFile">
          <button id="upload" v-show="dis" >上传</button>
          <div v-show="!dis">影像文件已上传！</div>
      </div>
      <!-- <input type="file" id="file" value="请选择文件" v-show="dis">
      <button id="upload" v-show="dis">upload</button>
      <div v-show="!dis">影像文件已上传！</div> -->
		</el-descriptions-item>
	</el-descriptions>
    </div>
    <!-- 新增标签弹层 -->
    <!-- <div class="add-form" v-if="User.type==0">
      <el-dialog title="上传影像" :visible.sync="dialogFormVisible">
        <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
          <el-row>
            <el-col :span="24">
              <el-form-item label="影像">
                <el-input v-model="formData.iamge" type="file" accept="image/jpeg,image/jpg,image/png"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel()">取消</el-button>
          <el-button type="primary" @click="handleAdd()">确定</el-button>
        </div>
      </el-dialog>
    </div>
    <div class="add-form" v-if="User.type!=0">
      <el-dialog title="上传照片" :visible.sync="dialogFormVisible">
        <div class="demo-image__preview" style="margin-left: 30%;">
          <el-upload
            class="upload-demo"
            ref="upload"
            drag
            action=""
            :http-request="upload"
            multiple
            :auto-upload="false">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text" >将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip" >只能上传jpg/png文件,大小500kb以内</div>
          </el-upload>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel()">取消</el-button>
          <el-button type="primary" @click="upload()">确定</el-button>
        </div>
      </el-dialog>
    </div> -->
  </div>
</template>

<script>
  import axios from 'axios';
  var baseURLStr = window.g.baseURL
  var backendPort = window.g.backendPort
  var dcm4cheePort = window.g.dcm4cheePort

  export default {
    name:'UserMine',
    data () {
      return {
        doctor:'',
        User:{},
        dis:true,
        rules:{},
        disable:true,
        disable1:true,
        dialogFormVisible:false,//编辑表单是否可见
        size: '',
        src: "data:image/jpeg;base64,",
        srcList:[],
        selectedFile:'',
        pform:2,
        uploadmark:0,
        dis2:false
      };
    },
    mounted() {

      let user = sessionStorage.getItem("userData");
       if (user != null) {
            // 将JSON格式的对象解析为js对象
            this.currentUser= JSON.parse(user);
            this.User = this.currentUser.user;
            this.src += this.currentUser.user.image;
            this.srcList[0] = this.src;
            if(this.User.phone.length==11){
              this.uploadmark = 1
            }
      }
      this.$axios({
        method:'get',
        url:baseURLStr+backendPort+"/users/s/"+this.User.phone,
      }).then((res)=>{
        if(res.data.flag){
          this.User = res.data.data;
          if(res.data.data!=null){
            if(res.data.data.file.length==0){
              this.dis = true
            }else{
              this.dis = false
            }
          }else{
            this.dis = true
          }
          if(res.data.data.image==='a'){
            this.dis2 = true
          }else{
            this.dis2 = false
          }
          if(this.User.physician!=null){
          this.$axios({
            method:'get',
            url:baseURLStr+backendPort+"/users/s/"+this.User.physician,
          }).then((res)=>{
            if(res.data.flag){
              this.doctor = res.data.data.name;
              // console.log(this.doctor)
            }
          });
        }
        }
      });
    },
    methods:{
      update(){
        this.disable = false;
      },
      update1(){
        this.disable = true;
        this.fpForm();
        if(this.User.email!=null&&this.User.phone!=null&&this.pform==1){
          this.$axios({
            method: 'put',
            url: baseURLStr+backendPort+"/users/mine",
            data:{
              email:this.User.email,
              password:this.User.password,
              idnum:this.User.idnum,
              type:this.User.type,
              phone:this.User.phone,
              age:this.User.age,
              sex:this.User.sex,
              unit:this.User.unit,
              description:this.User.description,
              description1:this.User.description1
            } 
          }).then(res =>{
            if(res.data.flag){
              this.uploadmark = 1;
              this.$message.success("修改成功！");
              this.$axios({
                method: 'get',
                url: baseURLStr+backendPort+"/users"+"/"+this.User.email,
              }).then(res =>{
                if(res.data.flag){
                  this.User = res.data.data;
                  this.$store.commit("setUser", {user: res.data.data});
                  
                }
              })
              this.mounted;
              // window.location.reload();
            }
            
          })
        }else{
          this.$message.info("邮箱和手机号不能为空！");
        }
      },
      // //弹出添加窗口
      // handleCreate() {
      //     this.dialogFormVisible = true;
      // },
      
      //重置表单
      // resetForm() {
      //     this.formData = {};
      // },
      handleCreate(){
        this.dis2=true;
        this.src = "data:image/jpeg;base64,";
      },
      
      //取消
      cancel(){
          this.dialogFormVisible = false;
          this.$message.info("当前操作取消");
      },
      upload(file) {
        if(this.uploadmark){
          let fd = new FormData();
          //文件信息中raw才是真的文件
          fd.append("files",file.file)
          this.$axios.post(baseURLStr+backendPort+"/users"+"/"+this.User.email,fd,{headers: {'Content-Type': 'multipart/form-data'}}
          ).then(res=>{
           if(res.data.flag){ 
            this.src += res.data.data;
            this.srcList[0] = this.src;
              this.$axios({
                method: 'get',
                url: baseURLStr+backendPort+"/users"+"/"+this.User.phone,
              }).then(res =>{
                if(res.data.flag){
                  this.User = res.data.data;
                  this.User.image = this.src;
                  this.$store.commit("setUser", {user: res.data.data});
                  // location.reload();
                }
              })
              this.dis2=false;
              // console.log(this.dis2);
              this.mounted;
            }
          }).catch(()=>{
            this.$message.error("照片过大！")
          })
        }else{
          this.$message.error("请先完善个人信息！");
          this.handleCreate();
        }
      },
      onFileChange(event)
      {
        this.selectedFile = event.target.files[0];
      },
	    // uploadFile(){
      //   if(this.uploadmark){
      //     var formData = new FormData();
      //     formData.append("uploadFile",this.selectedFile);
      //     this.$axios.post(baseURLStr+"/file/upload/"+this.User.phone,formData).then(res=>{
      //       if(res.data.flag){
      //       this.$message.success("上传成功！");
      //       this.dis=false;
      //       }else{
      //         this.$message.error("上传失败！");
      //       }
      //     })
      //   }else{
      //     this.$message.error("请先完善个人信息！");
      //   }
	    // },
      uploadFile(){
        let phone = this.User.phone;
        const self = this; 
        if(this.uploadmark){
          var file = document.getElementById("file");
          var trigger = document.getElementById("upload");

          trigger.addEventListener("click", function (e) {
            var reader = new FileReader();
            let s = "--", newline = "\r\n";
            let type = "application/dicom";
            let boundary = Math.random().toString().substr(2);
            let end = newline + s + boundary + s;
            let fileInfo = file.files[0];

            reader.readAsDataURL(file.files[0].slice());
            reader.addEventListener("loadend", function (e) {
              let req = new XMLHttpRequest();
              req.overrideMimeType(type);
              req.addEventListener("load", function (e) {
                let uids = e.target.response; 
                axios({
                method: 'post',
                url: baseURLStr+backendPort+"/file/upload/"+phone,
                data:{
                  file:uids
                }
            }).then(res=>{
              if(res.data.flag){
                var studies = res.data.data.file;
                var series = res.data.data.description
                axios({
                  method: 'GET',
                  url: baseURLStr+dcm4cheePort+'/dcm4chee-arc/aets/DCM4CHEE/rs/studies',
                }).then(res=>{
                  if(res.data!=null){
                    var array = res.data;
                    var len = array.length;
                    var i;
                    for(i=0;i<len;i++){
                      var array0 = array[i];
                      // console.log(array0);
                      var array3 = Object.values(array0);
                      // console.log(array3);
                      var array4 = Object.values(array3[9]);
                      // console.log(array4);
                      var value0 = array4[1][0].slice(65);
                      // console.log(value0);
                      // console.log(value0===studies);
                      if (value0 === studies){
                        // console.log(123);
                        break;
                      }
                    }
                    var patient = array[i];
                    // console.log(patient);
                    var array1 = Object.values(patient);
                    // console.log(array1);
                    var array2 = Object.values(array1[11]);
                    var patientID = array2[1][0];
                    axios({
                      method:'POST',
                      url: baseURLStr+backendPort+"/file/upload/s/"+phone,
                      data:{
                        file:"patientID="+patientID+"&studyUID="+studies+"&seriesUID="+series
                      }
                    }).then(res=>{
                      if(res.data.flag){
                        self.dis = false;
                        self.$message.success("上传成功！")
                        // alert("上传成功！");
                        self.mounted;
                      }else{
                        self.$message.error("上传失败，请重新上传！")
                        // alert("上传失败，请重新上传！");
                        self.mounted;
                      }
                    })
                  }
                })
                
            }
          })
            }, false);
            req.open("post", baseURLStr+dcm4cheePort+"/dcm4chee-arc/aets/DCM4CHEE/rs/studies");
            req.setRequestHeader("Accept", "application/dicom+json");
            req.setRequestHeader("Content-Type", 'multipart/related;type="'+type+'";boundary=' + boundary);// multipart/related;type="application/dicom";boundary=8256559802228709

            const start = s + boundary + newline + 'Content-Disposition: form-data;name="file";filename=""' + encodeURIComponent(fileInfo.name) + '"' + newline + "Content-Type: " + type + newline + newline;
            req.send(new Blob([new Blob([start]), fileInfo, new Blob([end])]));
        });
    }); 
        }else{
          this.$message.error("请先完善个人信息！");
          this.update2();
        }
	    },
        
      update2(){
        this.dis=true;
        // window.location.reload();
      },
      fpForm(){
        let reg = /^1[3-9]\d{9}$/;
        if(!reg.test(this.User.phone)){
          this.pform = 0;
          this.$message.error("手机号格式错误！")
        }else{
          this.pform = 1;
        }
      },
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