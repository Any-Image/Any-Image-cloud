<template>
<div class="edge">
    <p>选择边缘端</p>
    <br />
  <div>
    <input id="edge1" type="radio" name="edge" v-model="edge" value="0"/><label for="edge1">边缘端01</label>
  </div>
  <div>
    <input id="edge2" type="radio" name="edge" v-model="edge" value="1"/><label for="edge2">边缘端02</label>
  </div>
  <div>
    <input id="edge3" type="radio" name="edge" v-model="edge" value="2"/><label for="edge3">边缘端03</label>
  </div>
  <div>
    <input id="edge4" type="radio" name="edge" v-model="edge" value="3"/><label for="edge4">边缘端04</label>
  </div>

    <br />
    <input type="submit" value="选择" @click="tologin" />
</div>
</template>

<script>
  // var baseURLStr = window.g.baseURL;
  var edgeUrlStr = window.g.edgeUrlStr;
  var defaultWebPort = window.g.defaultWebPort;
  // console.log(edgeUrlStr)

  export default{
    name:'ChooseEdge',
    data(){
      return{
        edge: 0
      }
    },
    methods:{
      tologin(){
        // console.log(this.edge);
        // console.log(edgeUrlStr[this.edge]);
        window.location.href = "http://"+ edgeUrlStr[this.edge] + defaultWebPort +"/login.html";
      }
    }
}
</script>

<style>
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
.edge{
  position: absolute;
  top: 50%;
  margin-top: -300px;
  left: 50%;
  margin-left: -250px;
  background-color: whitesmoke;
  width: 500px;
/*  height: 450px; */
  border-radius: 25px;
  padding: 5px 40px;
  box-sizing: border-box;
}
.edge > div {
  text-align: center;
  margin: 20px;
}

p{
  font-size: 42px;
  font-weight: 600;
  height: 20px;
  text-align: center;
}

input[type=submit]{
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
</style>
