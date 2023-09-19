<template>
<div>
  <div class="banner">
    <div class="banner_bg">
      <div class="banner_left">
        <div class="banner_left1">
          <p>影像云是为了解决我国医学影像诊断过程中存在的基层医院误诊率高、偏远地区居民就诊不便、三甲医院影像专家工作量大等问题实现的一个医学影像诊断云平台。 </p>
          <p>影像云在患者、基层医院医生和三甲医院专家间建立直接的沟通渠道，既可以使患者在当地就能享受到三甲医院的专家诊疗，从而大大节省了患者就医成本和就医时间，又可以使基层医院医生快速积累专业经验，从而加快基层医院的专业能力建设。同时，该系统对推动远程医疗、分级诊疗、优质医疗资源下沉、服务可及性和区域整体医疗水平的提升有着长足的影响，为我国医院信息化建设带来了空前机遇，大大提高人民获得感和幸福感。</p>
          <p>在影像云，你可以享受到各大医院影像科专家的一对一服务，便捷的享受到优质的医疗资源！</p>
        </div>
        <!-- <div class="banner_left2">
          <a href="download.html">下载</a>
          <a class="btn2" href="news.html">查看最新动态</a>
        </div> -->
      </div>
    </div>
  </div>
  <!--Resources-->
  <div class="resources" >
    <div class="res_left">
      <h2>登录</h2>
      <hr color="#3861EA"/>
      <p>登录即可享受高水平的影像诊断服务！</p>
      <a href="./home.html">用户登录</a>
    </div>
  </div>
  <!--Journals & Conferences-->
  <div class="jc">
    <div class="jc_left">
      <h2>注册</h2>
      <hr color="#ffffff"/>
      <p>快快成为影像云的一员，体验高水平医疗服务的同时，为中国医学影像诊断的发展贡献自己的力量。</p>
      <a href="./register.html">用户注册</a>
    </div>
  </div>
</div>
</template>

<script>
  export default{
    name:'PageBody'
  }
</script>

<style>
  /*banner*/
  .banner{
    margin: 0 auto;
    height: 730px;
    width: 100%;
    background-color: #3C64EB;
  }
  .banner_bg{
    background:url(../assets/pic11.png) 730px 160px no-repeat;
    width:1280px;
    height:633px;
    margin: 0 auto;
  }
  .banner_left{
    height: 600px;
    float:left;
  }
  .banner_left1{
    height: 309px; 
    width: 600px;  
    margin-left: 80px; 
    margin-top: 89px; 
  }
  .banner_bg .banner_left1 p{
    font: Medium;
    font-size: 16px;
    text-align: left;
    letter-spacing: 0.22px;
    color: #FFFFFF;
    line-height: 30px;
    margin-top: 16px;
    text-indent: 2em;
  }
  .banner_left2{
    height: 200px; 
    width: 816px;  
    margin-left: 100px;  
  }
  .banner_left2 a{
    margin-top: 77px;
    width: 136px;
    height: 28px;
    float: left;
    padding: 15px 0 3px 0px; 
    text-align: center;
    border: 1px #00000000 solid;
    border-radius: 30px; 
    display: block; 
    color: #FFFFFF;
    font: Medium;
    font-size: 16px;
    line-height: 14px;
    background-color: #1ECFC3;
  }
  .banner_left2 a:hover{
    background-color: #455A64;
  }
  .banner_left2 a.btn2{
    margin-left: 50px;
  }

  .banner_right{
    width: 616px;
    height: 610px;
    margin-left: 200px;
    margin-top: 138px;
    
    position: absolute;
    z-index: 999;
  }
  .banner_right img{
    width: 616px;
    height: 610px;
  }

  /*resources*/
  .resources{
    margin: 0 auto;
    height: 532px;
    background-color: #fafcfe;
  }
  .res_left{
    width: 1180px; 
    height:470px;
    padding-top: 60px;
    padding-left: 100px;
    background:url(../assets/bgcir2.png) center center no-repeat;
    margin: 0 auto;
  }
  .res_left h2{
    font: Medium;
    font-size: 36px;
    line-height: 52px;
    color: #1D396B;
    letter-spacing: 1.91px;
    font-weight: normal;
    font-family: source-han-sans-simplified-c, sans-serif;
  font-weight: 500;
  font-style: normal;
  }
  .res_left hr{
    width: 150px; 
    margin-top: 17px;
  }
  .res_left p{
    font: Medium;
    font-size: 20px;
    text-align: left;
    line-height: 32px;
    margin-top: 48px;
    color: #1D396B;
  }
  .res_left a{
    margin-top: 200px;
    margin-right: 100px;
    width: 136px;
    height: 28px;
    float: right;
    padding: 13px 0 3px 0px; 
    text-align: center;
    border-radius: 30px; 
    display: block; 
    color: #FFFFFF;
    font: Medium;
    font-size: 16px;
    line-height: 14px;
    background-color: #1ECFC3;
  }
  .res_left a:hover{
    background-color: #455A64;
  }

  /*Journals & Conferences*/
  .jc{
    margin: 0 auto;
    height: 532px;
    background-color: #5e8afc;
    
  }

  .jc_left{
    width: 1180px; 
    height:470px;
    padding-top: 60px;
    padding-left: 100px;
    background:url(../assets/bgcir3.png) center center no-repeat;
    margin: 0 auto;
    
  }
  .jc_left h2{
    font: Medium;
    font-size: 36px;
    line-height: 52px;
    color: #ffffff;
    letter-spacing: 1.91px;
    font-weight: normal;
    font-family: source-han-sans-simplified-c, sans-serif;
  font-weight: 500;
  font-style: normal;;
  }
  .jc_left hr{
    width: 180px; 
    margin-top: 17px;
    color: #FFFFFF;
  }
  .jc_left p{
    font: Medium;
    font-size: 20px;
    text-align: left;
    line-height: 32px;
    margin-top: 48px;
    color: #FFFFFF;
  }
  .jc_left a{
    margin-top: 106px;
    width: 136px;
    height: 27px;
    float: left;
    padding: 14px 0 3px 0px; 
    text-align: center;
    border-radius: 30px; 
    display: block; 
    color: #3861EA;
    font: Medium;
    font-size: 16px;
    line-height: 14px;
    background: #FFFFFF;
  }
  .jc_left a:hover{
    background: none;
    color: #FFFFFF;
    border: 1px #FFFFFF solid;
  }

  .jc_right{
    float: right;
    width: 718px;
    height: 532px;
  }
  .jc_right img{
    margin-left: 100px;
    margin-top: 196px;
  }
  hr{
    size:"4px";
  }
</style>
