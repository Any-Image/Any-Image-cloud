<template>
    <div>
      <div id="step1" ref="step1">
        <div class="container">
          <div class="section" style="background-color: lightblue;">
            <div class="section-title">预处理</div>
            <div class="checkbox-group">
              <label class="checkbox-label">
                <input type="checkbox" name="ycl" value="重采样"> 重采样
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="ycl" value="直方图均衡化"> 直方图均衡化
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="ycl" value="图像增强"> 图像增强
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="ycl" value="图像配准"> 图像配准
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="ycl" value="图像去噪"> 图像去噪
              </label>
            </div>
          </div>
  
          <div class="section" style="background-color: orange;">
            <div class="section-title">分割</div>
            <div class="checkbox-group">
              <label class="checkbox-label">
                <input type="checkbox" name="fg" value="模糊KMean聚类割"> 模糊KMean聚类
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="fg" value="KMean聚类"> KMean聚类
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="fg" value="边缘分割"> 边缘分割
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="fg" value="主动轮廓模型"> 主动轮廓模型
              </label>
            </div>
          </div>
  
          <div class="section" style="background-color: lightgreen;">
            <div class="section-title">特征提取</div>
            <table>
              <thead>
              <th>统计特征</th>
              <th>原始</th>
              <th>派生</th>
              </thead>
              <tbody>
              <tr>
                <td>一阶</td>
                <td><input type="checkbox" name="tztq-wl" value="一阶-原始"></td>
                <td><input type="checkbox" name="tztq-wl" value="一阶-派生"></td>
              </tr>
              <tr>
                <td>纹理</td>
                <td><input type="checkbox" name="tztq-wl" value="纹理-原始"></td>
                <td><input type="checkbox" name="tztq-wl" value="纹理-派生"></td>
              </tr>
              <tr>
                <td>形状</td>
                <td><input type="checkbox" name="tztq-wl" value="形状-原始"></td>
                <td><input type="checkbox" name="tztq-wl" value="形状-派生"></td>
              </tr>
              <tr>
                <td>小波</td>
                <td><input type="checkbox" name="tztq-wl" value="小波-原始"></td>
                <td><input type="checkbox" name="tztq-wl" value="小波-派生"></td>
              </tr>
              </tbody>
            </table>
          </div>
  
          <div class="section" style="background-color: pink;">
            <div class="section-title">特征选择</div>
            <div class="checkbox-group">
              <label class="checkbox-label">
                <input type="checkbox" name="tzxz" value="LASSO回归"> LASSO回归
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="tzxz" value="最大最小"> 最大最小
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="tzxz" value="个人相关系数"> 个人相关系数
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="tzxz" value="信息增益"> 信息增益
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="tzxz" value="卡方"> 卡方
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="tzxz" value="距离相关"> 距离相关
              </label>
            </div>
          </div>
  
          <div class="section" style="background-color: green;">
            <div class="section-title">模型构建</div>
            <div class="checkbox-group">
              <label class="checkbox-label">
                <input type="checkbox" name="modelbuild" value="Logistic"> Logistic
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="modelbuild" value="SVM"> SVM
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="modelbuild" value="聚类分析"> 聚类分析
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="modelbuild" value="ANN"> ANN
              </label>
              <label class="checkbox-label">
                <input type="checkbox" name="modelbuild" value="随机森林"> 随机森林
              </label>
            </div>
          </div>
        </div>
        <button id="choose" ref="choose" @click="choose()">确认构建DAG图</button>
      </div>
  
      <div id="step2" ref="step2" class="md:flex flex-col md:flex-row md:min-h-screen w-full max-w-screen-xl mx-auto"
           style="display: none;">
        <!--    <script src="../release/go.js"></script>-->
        <div id="allSampleContent" ref="allSampleContent" class="p-4 w-full">
          <div id="panel" ref="allSampleContent" style="width: 100%;height: 500px;">
            <div id="myDiagramDiv" ref="allSampleContent" style="border: solid 1px black; width:100%; height:100%;"></div>
            <!-- 第一次点击【分配 chooseLocalRemote】按钮后延迟5s，再执行数据更新操作，之后，该按钮禁用 -->
            <!-- 最好有一个进度条，模拟进度。按钮点击后，下方显示两个上传文件框。 -->
            <button id="chooseLocalRemote" ref="chooseLocalRemote" @click="chooseLocalRemote()">分配</button>
  
            <!-- 进度条容器 -->
            <div id="allocateProgress" ref="allocateProgress" class="progressContainer w-full" style="display: none;">
              <div class="progress-bar" id="progressBar0" ref="progressBar0"></div>
            </div>
  
            <!-- 文件选择框 -->
            <div id="fileUploadContainer" ref="fileUploadContainer" class="w-full mt-4" style="display: none;">
              <input type="file" id="file1" ref="file1" class="w-1/2 mx-auto"/>
              <input type="file" id="file2" ref="file2" class="w-1/2 mx-auto mt-2"/>
            </div>
  
            <!-- 文件选择上传按钮 -->
            <button id="startUpload" ref="startUpload" @click="startUpload()" style="display: none;">开始上传</button>
  
            <!-- 进度条容器 -->
            <div id="uploadProgress" ref="uploadProgress" class="progressContainer w-full" style="display: none;">
              <div class="progress-bar" id="progressBar1" ref="progressBar1"></div>
              <div class="progress-bar" id="progressBar2" ref="progressBar2"></div>
            </div>
  
            <!-- 运行按钮 -->
            <button id="runButton" ref="runButton" @click="runOperation()" disabled style="display: none;">运行</button>
  
            <!-- 运行结果 -->
            <p id="result" ref="result"></p>
  
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import * as go from 'gojs'
  
  const $ = go.GraphObject.make;
  const HORIZONTAL = true;
  
  var currentData = {
    "预处理": [],
    "分割": [],
    "特征提取": [],
    "特征选择": [],
    "模型构建": []
  };
  
  export default {
    name: 'Radiomics1',
    components: {},
    props: {},
    data() {
      return {}
    },
    mounted() {
      this.isFirstClick = true;
      this.initGojs();
    },
    methods: {
      choose() {
        const selectedOptions = this.collectSelectedOptions();
        currentData = selectedOptions;
        this.$refs.step1.style.display = "none";
        this.$refs.step2.style.display = "block";
        // 触发更新操作
  
        const nodeLinkArray = this.transDataType(currentData);
  
        let BANDS = [
          { // this is the information needed for the headers of the bands
            key: "_BANDS",
            category: "Bands",
            itemArray: [
              {text: "预处理"},
              {text: "分割"},
              {text: "特征提取"},
              {text: "特征选择"},
              {text: "模型构建"},
            ]
          }];
  
        this.myDiagram.model = new go.GraphLinksModel(BANDS.concat(nodeLinkArray[0]), nodeLinkArray[1]);
      },
      transDataType(initialData) {
        const colors = [
          'lightblue',
          'orange',
          'lightgreen',
          'pink',
          'green'
        ];
  
        const keys = ["预处理", "分割", "特征提取", "特征选择", "模型构建"];
        const nodes = [];
        const links = [];
        // 创建节点
        for (let i = 0; i < keys.length; i++) {
          const section = keys[i];
          const sectionData = initialData[section];
          const color = colors[i];
  
          for (const item of sectionData) {
            nodes.push({key: item, color: color});
          }
  
          // 创建连接
          if (i > 0) {
            const prevSection = keys[i - 1];
            const prevSectionData = initialData[prevSection];
  
            for (const prevItem of prevSectionData) {
              for (const currentItem of sectionData) {
                links.push({from: prevItem, to: currentItem});
              }
            }
          }
        }
  
        // 将节点和连接分成两个数组
        // console.log(nodes);
        // console.log(links);
  
        return [nodes, links];
      },
      chooseLocalRemote() {
        if (this.isFirstClick) {
          this.isFirstClick = false;
          this.$refs.chooseLocalRemote.disabled = true;
          this.$refs.allocateProgress.style.display = 'block';
          setTimeout(this.afterClickBtn, 4000); // 延迟xx秒后执行数据更新
          this.startUploadOne("progressBar0", "99.99%");
        }
      },
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
      collectSelectedOptions() {
        const sections = document.querySelectorAll('.section');
  
        const selectedOptions = {};
  
        sections.forEach(section => {
          const sectionTitle = section.querySelector('.section-title').textContent;
          const checkboxes = section.querySelectorAll('input[type="checkbox"]:checked');
          const sectionOptions = [];
  
          checkboxes.forEach(checkbox => {
            const value = checkbox.value;
            sectionOptions.push(value);
          });
  
          selectedOptions[sectionTitle] = sectionOptions;
        });
  
        return selectedOptions;
      },
      afterClickBtn() {
        this.allocateData();
  
        this.$refs.runButton.style.display = 'inline-block';
        this.$refs.startUpload.style.display = 'inline-block';
        this.$refs.fileUploadContainer.style.display = 'block';
        this.$refs.progressBar0.textContent = '分配完成';
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
      allocateData() {
        const nodeArray = this.myDiagram.model.Tc;
        const linkArray = this.myDiagram.model.md;
        this.myDiagram.model = new go.GraphLinksModel(this.generateRandomBooleanArray(nodeArray), linkArray);
      },
      generateRandomBooleanArray(nodeArray) {
        let n = nodeArray.length;
        for (let i = 0; i < n; i++) {
          nodeArray[i]['color'] = Math.random() < 0.5 ? "red" : "green";// 生成随机的
        }
        return nodeArray;
      },
      initGojs() {
        class SimpleBandedTreeLayout extends go.TreeLayout {
          constructor() {
            super();
            this.layerStyle = go.TreeLayout.LayerUniform;  // needed for straight layers
          }
  
          commitLayers(layerRects, offset) {
            // update the background object holding the visual "bands"
            var bands = this.diagram.findPartForKey("_BANDS");
            if (bands) {
              var model = this.diagram.model;
              bands.location = this.arrangementOrigin.copy().add(offset);
  
              // make each band visible or not, depending on whether there is a layer for it
              for (var it = bands.elements; it.next();) {
                var idx = it.key;
                var elt = it.value;  // the item panel representing a band
                elt.visible = idx < layerRects.length;
              }
  
              // set the bounds of each band via data binding of the "bounds" property
              var arr = bands.data.itemArray;
              for (var i = 0; i < layerRects.length; i++) {
                var itemdata = arr[i];
                if (itemdata) {
                  model.setDataProperty(itemdata, "bounds", layerRects[i]);
                }
              }
            }
          }
        }
  
        this.myDiagram =
            new go.Diagram("myDiagramDiv",  // create a Diagram for the HTML Div element
                {
                  layout: $(SimpleBandedTreeLayout,  // custom layout is defined above
                      {
                        angle: HORIZONTAL ? 0 : 90,
                        arrangement: HORIZONTAL ? go.TreeLayout.ArrangementVertical : go.TreeLayout.ArrangementHorizontal
                      }),
  
                  "undoManager.isEnabled": true
                });  // enable undo & redo
  
        // define a simple Node template
        this.myDiagram.nodeTemplate =
            new go.Node("Auto")  // the Shape will go around the TextBlock
                .add(new go.Shape("RoundedRectangle",
                    {strokeWidth: 0, fill: "white"})  // no border; default fill is white
                    .bind("fill", "color"))  // Shape.fill is bound to Node.data.color
                .add(new go.TextBlock(
                    {margin: 8, font: "bold 14px sans-serif", stroke: '#333'})  // some room around the text
                    .bind("text", "key"));  // TextBlock.text is bound to Node.data.key
  
        this.myDiagram.nodeTemplateMap.add("Bands",
            $(go.Part, "Position",
                new go.Binding("itemArray"),
                {
                  isLayoutPositioned: false,  // but still in document bounds
                  locationSpot: new go.Spot(0, 0, HORIZONTAL ? 0 : 16, HORIZONTAL ? 16 : 0),  // account for header height
                  layerName: "Background",
                  pickable: false,
                  selectable: false,
                  itemTemplate:
                      $(go.Panel, HORIZONTAL ? "Vertical" : "Horizontal",
                          new go.Binding("position", "bounds", b => b.position),
                          $(go.TextBlock,
                              {
                                angle: HORIZONTAL ? 0 : 270,
                                textAlign: "center",
                                wrap: go.TextBlock.None,
                                font: "bold 11pt sans-serif",
                                background: $(go.Brush, "Linear", {0: "aqua", 1: go.Brush.darken("aqua")})
                              },
                              new go.Binding("text"),
                              // always bind "width" because the angle does the rotation
                              new go.Binding("width", "bounds", r => HORIZONTAL ? r.width : r.height)
                          ),
                          // option 1: rectangular bands:
                          $(go.Shape,
                              {stroke: null, strokeWidth: 0},
                              new go.Binding("desiredSize", "bounds", r => r.size),
                              new go.Binding("fill", "itemIndex", i => i % 2 == 0 ? "whitesmoke" : go.Brush.darken("whitesmoke")).ofObject())
                      )
                }
            ));
      }
    },
  }
  </script>
  
  <style scoped src="@/assets/css/style.css"></style>
  
  <style scoped>
  .gojs-warp {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: calc(100vh - 65px - 16px);
    border: 3px solid palevioletred;
  
  }
  
  #myDiagramDiv {
    width: 80vw;
    height: 80vh;
    border: 3px solid palevioletred;
  }
  
  
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
  
  .container {
    display: flex;
    justify-content: space-around;
    align-items: stretch;
    width: 100%;
  }
  
  .section {
    background-color: transparent;
    padding: 10px;
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .section-title {
    background-color: #f0f0f0;
    font-weight: bold;
    padding: 8px;
    text-align: center;
    width: 100%;
  }
  
  .checkbox-label {
    display: block;
  }
  
  .checkbox-group {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  
  .checkbox-group input[type="checkbox"] {
    margin-right: 5px;
  }
  
  
  /*  */
  
  #panel {
    text-align: center;
  }
  
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
  
  #step1 {
    text-align: center;
    width: 100%;
    height: 500px;
  }
  
  #step1 .container {
    text-align: left;
    margin: 0 auto;
  }
  </style>