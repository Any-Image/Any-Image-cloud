<template>
  <div class="aicontainer">
    <div id="table">
      <el-descriptions class="margin-top" title="影像组学" :column="3" :size="size" border>
        <template slot="extra">
          <el-button type="danger" size="small" @click="goRadiomics1()">选择方案</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-star-on"></i>
            方案名称
          </template>
          脑胶质瘤分级
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-order"></i>
            方案类型
          </template>
          分类模型
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-warning"></i>
            输入要求
          </template>
          原始图像和分割图像
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-info"></i>
            方案描述
          </template>
          返回脑胶质瘤分级结果（HGG、LGG）
        </el-descriptions-item>

      </el-descriptions>

      <br/>
      <br/>

      <el-descriptions class="margin-top" title="深度学习" :column="3" :size="size" border>
        <template slot="extra">
          <el-button type="danger" size="small" @click="goDP1()">选择方案</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-star-on"></i>
            方案名称
          </template>
          脑胶质瘤分级
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-order"></i>
            方案类型
          </template>
          分类模型
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-warning"></i>
            输入要求
          </template>
          原始图像和分割图像
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-info"></i>
            方案描述
          </template>
          返回脑胶质瘤分级结果（HGG、LGG）
        </el-descriptions-item>
      </el-descriptions>

    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
      size: ''
    };
  },
  methods: {
    goRadiomics1() {
      this.$router.push({path: '/radiomics1', query: {modelType: 1}})
    },
    goDP1() {
      this.$router.push({path: '/dp1', query: {modelType: 1}})
    },
  }
}
</script>

<style>
.aicontainer {
  width: 95%;
  margin-left: 2%;
  border-radius: 20px;
  border-top: 3px solid #3c8dbc;
  box-shadow: 0 1px 1px rgb(0, 0, 0 / 10%);
  margin-top: 20px;
}

#table {
  width: 92%;
  margin-top: 3%;
  margin-left: 3%;
}
</style>