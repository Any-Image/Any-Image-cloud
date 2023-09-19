<template>
    <div>
      <div id="step2" ref="step2" class="w-full">
        <!-- 文件选择框 -->
        <div id="fileUploadContainer" ref="fileUploadContainer" class="w-full mt-4">
          <input type="file" id="file1" ref="file1" class="w-1/2 mx-auto"/>
          <input type="file" id="file2" ref="file2" class="w-1/2 mx-auto mt-2"/>
        </div>
  
        <!-- 文件选择上传按钮 -->
        <button id="startUpload" ref="startUpload" @click="startUpload()">开始上传</button>
  
        <!-- 进度条容器 -->
        <div id="uploadProgress" ref="uploadProgress" class="progressContainer w-full" style="display: none;">
          <div class="progress-bar" id="progressBar1" ref="progressBar1"></div>
          <div class="progress-bar" id="progressBar2" ref="progressBar2"></div>
        </div>
  
        <!-- 运行按钮 -->
        <button id="runButton" ref="runButton" @click="runOperation()" disabled>运行</button>
  
        <!-- 运行结果 -->
        <p id="result" ref="result"></p>
  
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'DP1',
    components: {},
    props: {},
    data() {
      return {}
    },
    mounted() {
    },
    methods: {
      startUpload() {
        const fileInput1 = this.$refs.file1;
        const fileInput2 = this.$refs.file2;
  
        if (fileInput1.files.length > 0 && fileInput2.files.length > 0) {
          // 两个文件选择框都选中了文件
          this.$refs.uploadProgress.style.display = 'block';
          this.startUploadOne("progressBar1", "上传完成");
          this.startUploadOne("progressBar2", "上传完成");
          this.$refs.runButton.disabled = false;
        } else {
          // 提示用户选择文件
          alert('请确保两个文件选择框都选中了文件。');
        }
      },
      startUploadOne(id, msg) {
        const progressBar = document.getElementById(id);
        progressBar.style.width = '0%';
  
        this.simulateFileUpload((percentage) => {
          progressBar.style.width = percentage + '%';
          progressBar.textContent = percentage.toFixed(2) + '%';
  
          if (percentage === 100) {
            progressBar.textContent = msg;
          }
        });
      },
      runOperation() {
        this.$refs.runButton.disabled = true;
        setTimeout(this.displayResult, 5000); // 模拟运行耗时xx秒后显示结果
      },
      displayResult() {
        // 显示运行结果
        const resultText = this.$refs.result;
        const elapsedTime = (Math.random() + 5).toFixed(2); // 模拟的运行时间，单位秒   5-6s之间
        // const HGG = '<span style="color: red;">HGG</span>';
        // const LGG = '<span>LGG</span>';
        const modelResult= Math.random() < 0.5 ? "HGG" : "LGG";
        // console.log(modelResult);
        resultText.innerText = `共耗时 ${elapsedTime} 秒！分级结果：${modelResult}`;
      },
      simulateFileUpload(progressCallback) {
        const MAXSIZE = 3;// 最大3倍
        const minFileSize = 1024 * 1024 / 10; // 模拟文件最小大小
        const maxFileSize = minFileSize * MAXSIZE; // 模拟文件最大大小
        const totalSize = Math.floor(Math.random() * (maxFileSize - minFileSize + 1)) + minFileSize;
        const chunkSize = 1024 * 10; // 分块大小为10KB
        let uploadedSize = 0;
  
        function uploadChunk() {
          if (uploadedSize >= totalSize) {
            progressCallback(100);
            return;
          }
  
          const percentage = (uploadedSize / totalSize) * 100;
          progressCallback(percentage);
  
          setTimeout(() => {
            uploadedSize += chunkSize;
            uploadChunk();
          }, 100); // 模拟每100ms上传一个分块
        }
  
        uploadChunk();
      },
    },
  }
  </script>
  
  <style scoped src="@/assets/css/style.css"></style>
  
  <style scoped>
  body {
    font-family: Arial, sans-serif;
    background-color: #f0f0f0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    margin: 0;
  }
  
  .checkbox-group input[type="checkbox"] {
    margin-right: 5px;
  }
  
  
  /*  */
  .progressContainer {
    width: 50%;
    margin: 20px auto;
  }
  
  .progress-bar {
    width: 0;
    height: 30px;
    background-color: #007bff;
    text-align: center;
    line-height: 30px;
    color: white;
  }
  
  #step2 {
    text-align: center;
  }
  </style>