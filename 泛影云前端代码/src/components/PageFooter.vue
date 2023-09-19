<template>
<footer>
  <div class="main_footer">
  <div class="footer_left">
      <a class="f_left_logo2"  href="#"><img s src="../assets/icons/AICWORD@4x.png" style="width: 60%;"></a>
      <a class="f_left_logo2"  href="#"><img src="../assets/icons/AIRWORD@4x.png" style="width: 60%;"></a>
    </div>
    <div class="footer_right">
      <ul class="ul1">
        <li class="f_list_title"><span>快速链接</span></li>
        <li><a href="./home.html">主页</a></li>
        <li><a href="./register.html">注册</a></li>
        <li><a href="./edge.html">登录</a></li>
      </ul>
      <ul class="ul1 ul2">
        <li class="f_list_title"><span>联系我们&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-qq"></i>&nbsp;&nbsp;&nbsp;&nbsp; <i class="fa fa-weixin"></i></span></li>
        <li><i class="fa fa-location-arrow">&nbsp;&nbsp;地址：河南省郑州市二七区大学北路</i></li>
        <li><i class="fa fa-phone">&nbsp;&nbsp;电话：0371-6776318</i></li>
        <li><i class="fa fa-bandcamp">&nbsp;&nbsp;邮编：454003</i></li>
        <li><i class="fa fa-envelope">&nbsp;&nbsp;电子邮箱：ai@.hpu.edu.cn</i></li>
      </ul>
    </div>
  </div>
  <div style="clear: both;"></div>
  <div class="footer_copyright">
    <p> © 2022- <span id="footer-this-year" style="color: inherit;">2023</span> 版权所有</p>
  </div>
</footer>
</template>

<script>
  export default{
    name: 'PageFooter'
  }
</script>

<style>
  footer{
    margin: 0 auto;
    height: 380px;
    background-color: #455A64;
  }
  .main_footer{
    width: 1220px;
    margin: 0 auto;
    padding-top: 60px;
  }
  .footer_left{
    width: 320px;
    float: left;
    padding-left: 60px;
  }
  .f_left_logo2{
    display: block;
    margin-bottom: 30px;
  }

  .f_notice{
    font-size: 16px;
    color: #FFFFFF;
    height: 40px;
    line-height: 40px;
    margin: 15px 0;
  }
  .f_notice a{
    width: 40px;
    height: 40px;
    line-height: 40px;
    display: inline-block;
    /* float: left; */
    margin-right:4px;
  }

  .f_add{
    line-height: 26px;
    color: #8899a8;
  }
  .footer_right{
    width: auto;
    margin-left: 460px;
    margin-bottom: 25px;
    }
  .footer_right ul{
    margin: 0 25px;
    float: left;
    width: 117px;
  }
  .footer_right .ul2{
    width: 430px;
  }

  .footer_right ul li{
    height: 35px;
  }
  .footer_right .ul1 li{
    height: 30px;
  }
  .footer_right .ul1 li.f_list_title{
    margin-bottom: 20px;
  }
  .footer_right ul li span{
    font: SemiBold;
    font-size: 18px;
    line-height: 34px;
    color: #FFFFFF;
    margin-bottom: 20px;
  }
  .footer_right ul li a{
    font: Regular;
    font-size: 14px;
    color: #8899a8;
    height: 26px;
    line-height: 26px;
    display: block;
    float: none;
    margin: 0px;
  }	
  .footer_right ul li p{
    font: Regular;
    font-size: 14px;
    color: #8899a8;
    height: 26px;
    line-height: 26px;
    display: block;
    float: none;
    margin-left: 25px;
    
  }	
  .footer_right ul li i {
    color: #8899a8;
  }
  .footer_right ul li a:hover{
    text-decoration: underline;
  }

  .footer_right ul li img{
    vertical-align: middle; 
    margin-top: 7px; 
    float: left; 
    width: 15px; 
    height: 14px;
  }
  .footer_copyright{
    margin: 0 auto;
    height: 40px;
    text-align: center;
  }
  .footer_copyright p{
    height: 40px;
    line-height: 40px;
    color: #8899a8;
    border-top: 1px solid #8899a8;
  }
</style>
