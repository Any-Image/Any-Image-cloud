<template>
<div class="app-container">
  <div class="box">
    <div >
      <el-input placeholder="专家姓名" v-model="pagination.name" style="width: 200px;" ></el-input>
      <el-input placeholder="性别" v-model="pagination.sex" style="width: 200px;" ></el-input>
      <el-input placeholder="单位" v-model="pagination.unit" style="width: 200px;" ></el-input>
      <el-button class="dalfBut" @click="getBy" v-show="rm==1">查询</el-button>
      <el-button class="dalfBut" @click="back" v-show="rm==0">返回</el-button>
      <el-button type="primary" class="butT" @click="handleCreate()" v-if="User.type==3">新建</el-button>
    </div>
    <el-table size="small" current-row-key="id" :data="dataList" stripe highlight-current-row>
      <el-table-column type="index" align="center" label="序号"></el-table-column>
      <el-table-column prop="name" label="姓名" align="center"></el-table-column>
      <el-table-column prop="sex" label="性别" align="center"></el-table-column>
      <el-table-column prop="unit" label="单位" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="toInfo(scope.row)">详情</el-button>
          <!-- <el-button type="primary" size="mini" v-if="User.type!=3">发消息</el-button> -->
          <el-button type="danger" size="mini" @click="addPhy(scope.row)" v-if="User.type!=3">申请诊断</el-button>
          <el-button type="danger" size="mini" @click="del(scope.row)" v-if="User.type==3">删除</el-button>
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
    <div class="add-form">
            <el-dialog title="新增专家" :visible.sync="dialogFormVisible">
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
                  <el-col :span="12">
                    <el-form-item label="工作单位" prop="unit">
                      <el-input v-model="formData.unit"/>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="个人简介" prop="description1">
                      <el-input v-model="formData.description" type="textarea"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
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
                  </div>
                </el-row>
              </el-form>
              <div slot="footer" class="dialog-footer" >
                <el-button @click="cancel()">取消</el-button>
                <el-button type="primary" @click="handleAdd()">确定</el-button>
              </div>
            </el-dialog>
          </div>
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
      props: { 
        multiple: true,
        expandTrigger: 'hover',
      },
      User:{},
      choice:0,
      rules:{},
      dataList: [],//当前页要展示的列表数据
      dialogFormVisible: false,//添加表单是否可见
      formData: {},//表单数据
      pagination: {//分页相关模型数据
          currentPage: 1,//当前页码
          pageSize:10,//每页显示的记录数
          total:0,//总记录数
          name: null,
          sex: null,
          unit: null
      },
      rm:1
    }
  },

  //钩子函数，VUE对象初始化完成后自动执行
  created() {
    let user = sessionStorage.getItem("userData");
     if (user != null) {
        // 将JSON格式的对象解析为js对象
        this.currentUser= JSON.parse(user);
        this.User = this.currentUser.user;
    }
      //调用查询全部数据的操作
      this.getAll();
      if(this.User.type==1){
        this.$message.info("如需申请专家诊断，请先选患者再选择专家！");
      }
  },

  methods: {
      //列表
      //分页查询
    getAll() {
      //发送异步请求
      this.$axios.get(baseURLStr+backendPort+"/users/experts"+"/"+this.pagination.currentPage+"/"+this.pagination.pageSize).then((res)=>{
          this.pagination.pageSize = res.data.data.size;
          this.pagination.currentPage = res.data.data.current;
          this.pagination.total = res.data.data.total;
          this.dataList = res.data.data.records;
      });
    },
    addPhy(row){
      // let user = sessionStorage.getItem("userData");
      // if (user != null) {
      //   // 将JSON格式的对象解析为js对象
      //   this.currentUser= JSON.parse(user);
      // }
      this.$confirm("请再次确认是否选择此位专家","提示",{type:"info"}).then(()=>{
        if(this.User.type==0){
          this.$axios({
          method:'put',
          url:baseURLStr+backendPort+"/users/mine"+"/"+this.User.phone,//2023年5月19日修改
          data:{
            physician: row.phone
          }
          }).then(res=>{
              if(res.data.flag){
                this.$message.success("申请成功！");
                this.$axios({
                  method:'get',
                  url:baseURLStr+backendPort+"/users"+"/"+this.currentUser.user.phone,
                }).then(res=>{
                   if(res.data.flag){
                    this.$store.commit("setUser", {user: res.data.data});
                  }
                })
              }else{
                this.$message.error("申请失败！")
              }
          });
        }else{
          // console.log(this.$route.query.file);
          if(this.$route.query.file.length!=0){
                this.$axios({
                  method:'put',
                  url:baseURLStr+backendPort+"/remotations/transfer"+"/"+this.$route.query.file,
                  data:{
                    physician: row.phone,
                    phone:this.User.phone
                  }
                }).then(res=>{
                  if(res.data.flag){
                    this.$message.success("申请成功！");
                  }else{
                    this.$message.error("申请失败！");
                  }
                });
          }else{
            this.$message.error("非法操作！如需申请专家诊断，请先选患者再选择专家！");
          }
        }
      })
    },
    getBy(){
      if(this.pagination.name!=null||this.pagination.sex!=null||this.pagination.unit!=null){
        this.$axios({
        method:'post',
        url:baseURLStr+backendPort+"/users/experts"+"/"+this.pagination.currentPage+"/"+this.pagination.pageSize,
        data: { 
          name:this.pagination.name,
          sex: this.pagination.sex,
          unit:this.pagination.unit
        }
        }).then((res)=>{
          if(res.data.flag){
            this.rm = 0;
            this.choice = 1;
            this.pagination.pageSize = res.data.data.size;
            this.pagination.currentPage = res.data.data.current;
            this.pagination.total = res.data.data.total;
            this.dataList = res.data.data.records;
          }else{
            this.$message.error("查无此人！");
          }
        });
      }else{
        this.$message.error("请输入要查询的信息！");
        this.choice = 0;
      }
    },
    //切换页码
    handleCurrentChange(currentPage) {
      //修改页码值为当前选中的页码值
      this.pagination.currentPage = currentPage;
      //执行查询
      if(this.choice){
        this.getBy();
      }else{
        this.getAll();
      }
    },
    toInfo(row){
      this.$router.push({
        path:'/experts/info',query:{email:row.phone}
      })
    },
    del(row) {
        this.$confirm("请再次确认是否删除","提示",{type:"info"}).then(()=>{
          this.$axios({
          method:'delete',
          url:baseURLStr+backendPort+"/admin/"+row.phone,
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
    //弹出添加窗口
    handleCreate() {
        this.dialogFormVisible = true;
        this.resetForm();
        alert("填写信息后请先点击“确定”，提示添加成功后，再上传照片！");
    },
    
    //重置表单
    resetForm() {
        this.formData = {};
    },
    
    //添加
    handleAdd () {
      this.formData.physician = this.User.phone;
        this.$axios.post(baseURLStr+backendPort+"/admin/expertises",this.formData).then((res)=>{
          //判断当前操作是否成功
          if(res.data.flag){
              this.$message.success("添加成功！");
          }else{
              this.$message.error("身份证号或手机号已存在！");
          }
      })
    },
    
    //取消
    cancel(){
        this.dialogFormVisible = false;
        this.dialogFormVisible4Edit = false;
        this.$message.info("当前操作取消");
    },
    upload(file) {
      let fd = new FormData();
      //文件信息中raw才是真的文件
      fd.append("files",file.file)
      this.$axios.post(baseURLStr+backendPort+"/users"+"/"+this.formData.phone,fd,{headers: {'Content-Type': 'multipart/form-data'}}
      ).then(()=>{
        this.dialogFormVisible = false;
        this.getAll();
      }).catch(()=>{
        this.$message.error("照片过大！")
      })
    },
    back(){
      this.rm = 1;
      this.getAll();
    }
  }
}
</script>

<style>
</style>
