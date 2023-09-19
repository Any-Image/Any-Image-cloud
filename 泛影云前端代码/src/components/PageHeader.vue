<template>
  <div class="top_nav">
    <ul>
      <li></li>
      <li><img src="../assets/icons/rlogo.png"/></li>
      <li></li>
      <li></li>
      <li></li>
      <li><a href="./home.html">首&nbsp;&nbsp;&nbsp;&nbsp;页</a></li>
      <li><a href="#">产品简介</a>
        <ul>
          <li><a href="#">泛影云简介</a></li>
          <li><a href="#">泛影云发展</a></li>
        </ul>
      </li>
      <li><a href="#">产品指南</a>
        <ul>
          <li><a href="#">医生指南</a></li>
          <li><a href="#">患者指南</a></li>
          <li><a href="#">科研指南</a></li>
        </ul>
      </li>
      <li><a href="#">资料下载</a>
        <ul>
          <li><a href="#">应用下载</a></li>
          <li><a href="#">文档下载</a></li>
        </ul>
      </li>
      <li><a href="#">关于我们</a>
        <ul>
          <li><a href="#">科研团队</a></li>
          <li><a href="#">合作网站</a></li>
          <li><a href="#">联系我们</a></li>
        </ul>
      </li>
      <li></li>
      <li><a href="./edge.html" >用户登录</a></li>
    </ul>
  </div>
</template>



<script>
  export default {
    name: 'PageHeader'
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  .top_nav img{
    height: 100%;
    float: left;
    margin-top: 10px;
    margin-left: 12%;
    display: block;
  }
  .top_nav {
    height: 121px;
    width: 100%;
    float: right;
    background-color: #4268EB;
  }
/*  .top_nav li{
    float: left;
    height: 121px;
    width: 121px;
    text-align: center;
  } */
 /* .top_nav li a,.main_index a{
    font: Medium ;
    height: 71px;
    font-size:20px;
    color: aliceblue;
    display: block;
    padding-top: 47px;
  }
  .top_nav li a:hover{
    background-color: #4265D5;
    border-bottom: 3px solid #ffffff;
  } */
  ul {
    list-style: none;
    display: inline-block;
}

ul li {
    position: relative;
    float: left;
    height: 115px;
    width:100px;
    text-align: center;
}

a {
    text-decoration: none;
    /* padding: 0 20px; */
    white-space: nowrap;
    font: large;
    height: 71px;
    font-size:22px;
    color: aliceblue;
    display: block;
    padding-top: 47px;
}

a:hover {
    color: #FFF;
    background-color: #4268EB;
    border-bottom: 3px solid #ffffff;
}

ul li ul li {
    float: none;
    height: 50px;
    width: 120px;
    background-color:  #4265D5;
}

ul li ul li a:hover {
    color: #FFF;
    background-color: #4265D5;
    border-bottom: 3px solid #ffffff;
}

ul li ul {
    position: absolute;
    display: none;
    padding-left: 0;
}

ul li ul li a{
  text-decoration: none;
  white-space: nowrap;
  font: Medium ;
  height: 30px;
  font-size:20px;
  display: block;
  padding-top: 10px;
}
ul li:hover ul {
    display: block;
}
</style>
