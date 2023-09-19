<template>
  <div class="app-container">
    <div class="box">
      <div v-show="User.type!=0">
        <!-- <el-input placeholder="会诊名称（未诊）" v-model="pagination.name" style="width: 200px;" v-show="User.type==1"></el-input> -->
        <el-input placeholder="未诊患者姓名" v-model="pagination.name" style="width: 200px;" v-show="User.type!=1"></el-input>
        <el-button @click="getBy()" class="dalfBut" v-show="rs==1">查询</el-button>
        <el-button class="dalfBut" @click="back2" v-show="rs==0">返回</el-button>
        <!-- <el-button @click="getBy2()" type="primary" size="small" v-if="rm==1&&User.type==2">远程会诊</el-button>
        <el-button @click="back" type="primary" sise="small" v-if="rm==0">返回</el-button> -->
        <!-- <el-button type="primary" class="butT" @click="handleCreate()" v-show="User.type==1">新增会诊</el-button> -->
      </div>
      <el-table size="small" current-row-key="id" :data="dataList" stripe highlight-current-row>
        <el-table-column type="index" align="center" label="序号"></el-table-column>
        <el-table-column prop="name" label="姓名" align="center"></el-table-column>
        <el-table-column prop="age" label="年龄" align="center"></el-table-column>
        <el-table-column prop="sex" label="性别" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="goInfo(scope.row)">详情</el-button>
            <el-button type="danger" size="mini" @click="del(scope.row)" v-if="User.type==1||User.type==2">删除</el-button>
            <el-button type="primary" size="mini" v-if="User.type==1" @click="goE(scope.row)">寻医</el-button>
            <el-button type="danger" size="mini" @click="adel(scope.row)" v-if="User.type==3">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <div class="pagination-container">
        <el-pagination
          class="pagiantion"
          @current-change="handleCurrentChange"
          :current-page="pagination.currentPage"
          :page-size="pagination.pageSize"
          layout="total, prev, pager, next, jumper"
          :total="pagination.total">
        </el-pagination>
      </div>
      <!-- 新增标签弹层 -->
      <!-- <div class="add-form">
        <el-dialog title="新增会诊请求" :visible.sync="dialogFormVisible">
          <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="姓名" prop="name">
                  <el-input v-model="formData.name"/>
                </el-form-item>
              </el-col> 
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="年龄" prop="age">
                  <el-input v-model="formData.age"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="性别" prop="sex">
                  <el-input v-model="formData.sex"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="身份证号" prop="sex">
                  <el-input v-model="formData.idnum"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="手机号" prop="sex">
                  <el-input v-model="formData.phone"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="影像">
                  <el-input v-model="formData.iamge" type="file"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="主述" prop="description">
                  <el-input v-model="formData.description" type="textarea"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="初诊意见" prop="description">
                  <el-input v-model="formData.description" type="textarea"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
              <el-form-item label="影像资料">
                <form @submit.prevent="uploadFile" style="width: 300px;margin-left: 20%;">
			            <input type="file" name="uploadFile" value="请选择文件" @change="onFileChange">
			            <input type="submit" value="上传">
			        </form>
              </el-form-item>
              </el-col>
              <div class="demo-image__preview" style="margin-left: 30%;">
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

			http://localhost:80/upload" method="post" enctype="multipart/form-data"
              </div>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer" >
            <el-button @click="cancel()">取消</el-button>
            <el-button type="primary" @click="handleAdd()">确定</el-button>
          </div> -->
        <!-- </el-dialog> -->
      <!-- </div> -->
    </div>
  </div>
</template>

<script>
  var baseURLStr = window.g.baseURL
  var backendPort = window.g.backendPort

  export default{
  name:'UsersInfo',
  data(){
    return{
      User:{},
      rules:{},
      choice:0,
      dataList: [],//当前页要展示的列表数据
      dialogFormVisible: false,//添加表单是否可见
      formData: {},//表单数据
      pagination: {//分页相关模型数据
          currentPage: 1,//当前页码
          pageSize:2,//每页显示的记录数
          total:0,//总记录数
          name:null
      },
      rm:1,
      rs:1
    }
  },

  //钩子函数，VUE对象初始化完成后自动执行
  mounted() {
      //调用查询全部数据的操作
    let user = sessionStorage.getItem("userData");
    if (user != null) {
    // 将JSON格式的对象解析为js对象
      let currentUser= JSON.parse(user);
      this.User = currentUser.user;
    }
    this.getAll();
  },

  methods: {
    goE(row){
      this.$router.push({path:'/experts',query:{email:row.phone}});
    },
      //分页查询
     getAll() {
      if(this.User.type!=3){
       //发送异步请求
        this.$axios.get(baseURLStr+backendPort+"/users/patients"+"/"+this.pagination.currentPage+"/"+this.pagination.pageSize+"/"+this.User.phone+"/"+this.User.type+"/"+"0").then((res)=>{
            this.pagination.pageSize = res.data.data.size;
            this.pagination.currentPage = res.data.data.current;
            this.pagination.total = res.data.data.total;
            this.dataList = res.data.data.records;
          });
        }else{
          this.$axios.get(baseURLStr+backendPort+"/admin/p0"+"/"+this.pagination.currentPage+"/"+this.pagination.pageSize).then((res)=>{
            this.pagination.pageSize = res.data.data.size;
            this.pagination.currentPage = res.data.data.current;
            this.pagination.total = res.data.data.total;
            this.dataList = res.data.data.records;
          });
        }
        // setTimeout(()=>{
        //   this.getAll();
        // },10000)
    },
     getBy(){
       if(this.pagination.name!=null){
         if(this.pagination.name.length){
           if(this.User.type==3){
             this.$axios({
              method:'get',
              url:baseURLStr+backendPort+"/admin/p0/"+this.pagination.currentPage+"/"+this.pagination.pageSize+"/"+this.pagination.name,
             }).then((res)=>{
                if(res.data.flag){
                  this.rs = 0;
                  this.choice = 1;
                  this.pagination.pageSize = res.data.data.size;
                  this.pagination.currentPage = res.data.data.current;
                  this.pagination.total = res.data.data.total;
                  this.dataList = res.data.data.records;
                }else{
                  this.$message.error("查无此人！")
                }
              });
           }else{
             this.$axios({
              method:'get',
              url:baseURLStr+backendPort+"/users/p0/"+this.pagination.currentPage+"/"+this.pagination.pageSize+"/"+this.pagination.name+"/"+this.User.phone+"/"+this.User.type,
             }).then((res)=>{
                if(res.data.flag){
                  this.rs = 0;
                  this.choice = 1;
                  this.pagination.pageSize = res.data.data.size;
                  this.pagination.currentPage = res.data.data.current;
                  this.pagination.total = res.data.data.total;
                  this.dataList = res.data.data.records;
                }else{
                  this.$message.error("查无此人！")
                }
              });
           }
         }else{
          this.getAll();
          this.$message.error("请输入要查询的姓名！");
          this.choice = 0;
        }
      }else{
          this.getAll();
          this.$message.error("请输入要查询的姓名！");
          this.choice = 0;
      }
     },
    //  getBy2(){
      
    //   if(this.User.type==3){
    //          this.$axios({
    //           method:'get',
    //           url:baseURLStr+backendPort+"/remotes/p0/"+this.pagination.currentPage+"/"+this.pagination.pageSize,
    //          }).then((res)=>{
    //             if(res.data.flag){
    //               this.rm = 0;
    //               this.choice = 1;
    //               this.pagination.pageSize = res.data.data.size;
    //               this.pagination.currentPage = res.data.data.current;
    //               this.pagination.total = res.data.data.total;
    //               this.dataList = res.data.data.records;
    //             }else{
    //               this.$message.error("无远程会诊记录！")
    //             }
    //           })
    //        }else{
    //          this.$axios({
    //           method:'get',
    //           url:baseURLStr+backendPort+"/remotes/p0/"+this.pagination.currentPage+"/"+this.pagination.pageSize+"/"+this.User.phone+"/"+this.User.type,
    //          }).then((res)=>{
    //             if(res.data.flag){
    //               this.rm = 0;
    //               this.choice = 1;
    //               this.pagination.pageSize = res.data.data.size;
    //               this.pagination.currentPage = res.data.data.current;
    //               this.pagination.total = res.data.data.total;
    //               this.dataList = res.data.data.records;
    //             }else{
    //               this.$message.error("无远程会诊记录！")
    //             }
    //           })
    //        }
    //  },
      //切换页码
      handleCurrentChange(currentPage) {
          //修改页码值为当前选中的页码值
          this.pagination.currentPage = currentPage;
          //执行查询
          if(!this.choice){
            this.getAll();
          }else{
            this.getBy();
          }
      },
      goInfo(row){
        this.$router.push({path:'/patients/info',query:{email:row.phone}})
      },
      // //弹出添加窗口
      // handleCreate() {
      //     this.dialogFormVisible = true;
      //     this.resetForm();
      //     alert('填写信息后请先点击“确定”，提示添加成功后，再"上传"影像,上传成功后点击“x”关闭即可！');
      // },

      // //重置表单
      // resetForm() {
      //     this.formData = {};
      // },

      // //添加
      // handleAdd () {
      //   this.formData.physicians = this.User.phone;
      //     this.$axios.post(baseURLStr+backendPort+"/users",this.formData).then((res)=>{
      //       //判断当前操作是否成功
      //       if(res.data.flag){
      //           this.$message.success("添加成功！");
      //       }else{
      //           this.$message.error("添加失败！");
      //       }
      //   })
      // },

      // //取消
      // cancel(){
      //     this.dialogFormVisible = false;
      //     this.dialogFormVisible4Edit = false;
      //     this.$message.info("当前操作取消");
      // },
      // upload(file) {
      //   let fd = new FormData();
      //   //文件信息中raw才是真的文件
      //   fd.append("files",file.file)
      //   this.$axios.post(baseURLStr+backendPort+"/users"+"/"+this.formData.phone,fd,{headers: {'Content-Type': 'multipart/form-data'}}
      //   ).then(()=>{
      //     this.dialogFormVisible = false;
      //     this.getAll();
      //   }).catch(()=>{
      //     this.$message.error("照片过大！")
      //   })
      // },
      // 删除
      del(row) {
        this.$confirm("请确定是否完成诊断再觉得删除","提示",{type:"info"}).then(()=>{
          this.$axios({
          method:'put',
          url:baseURLStr+backendPort+"/users/"+row.phone+"/"+this.User.type+"/"+row.type,
        }).then((res)=>{
              if(res.data.flag){
                  this.$message.success("删除成功！");
                  this.getAll();
              }else{
                  this.$message.error("操作失败！");
              }
          }).catch(()=>{
          this.$message.info("操作取消！");
        });
      })
    },
      adel(row) {
        this.$confirm("请再次确认是否删除","提示",{type:"info"}).then(()=>{
          this.$axios({
          method:'delete',
          url:baseURLStr+backendPort+"/admin/"+row.phone+"/"+row.type,
        }).then((res)=>{
            if(res.data.flag){
                this.$message.success("删除成功！");
                this.getAll();
            }else{
                this.$message.error("操作失败！");
            }
        }).catch(()=>{
          this.$message.info("操作取消！");
        });
      });
    },
    // onFileChange(event)
    //   {
    //     this.selectedFile = event.target.files[0];
    //   },
	    // uploadFile(){
      //   var formData = new FormData();
      //   formData.append("uploadFile",this.selectedFile);
      //   this.$axios.post(baseURLStr+backendPort+"/file/upload/"+this.formData.phone,formData).then(res=>{
      //     if(res.data.flag){
      //     this.$message.success("上传成功！");  
      //     location.reload();
      //     }else{
      //       this.$message.error("上传失败！");
      //     }
      //   })
	    // },
      back(){
        this.rm = 1;
        this.getAll();
    },
    back2(){
      this.rs = 1;
      this.getAll();
    }
  }
}
</script>
<style >
  .app-container{
    width: 95%;
    margin-left: 2%;
    border-radius: 20px;
    border-top: 3px solid #3c8dbc;
    box-shadow: 0 1px 1px rgb(0, 0, 0 / 10%);
    margin-top: 20px;
  }
  .box{
    width: 92%;
    margin-top: 10px;
    margin-left: 3%;
  }
  .pagination-container{
    float: right;
  }
</style>