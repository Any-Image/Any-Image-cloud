import VueRouter from "vue-router"
import UsersInfo from "../components/UsersInfo.vue"
import UserPatients from "../components/UserPatients.vue"
import UserMine from "../components/UserMine.vue"
import AllNfo from "../components/AllNfo.vue"
import UserDoctors from "../components/UserDoctors.vue"
import UserPatient from "../components/UserPatient.vue"
import aiCheck from "../components/aiCheck.vue"
import UserRemotation0 from "../components/UserRemotation0.vue"
import UserRemotation1 from "../components/UserRemotation1.vue"
import RemotationInfo from "../components/RemotationInfo.vue"
import Radiomics1 from "../components/Radiomics1.vue";
import DP1 from "../components/DP1.vue";

export default new VueRouter({
  routes:[
    {
      path:'/',
      component:UserMine,
      meta:{
        keepAlive: false
      }
    },
    {
      path:'/mine',
      component:UserMine,
      meta:{
        keepAlive: false
      }
    },
    {
      path:'/experts',
      component:UsersInfo,
      meta:{
        keepAlive: true
      }
    },
    {
      path:'/experts/info',
      component:AllNfo,
      meta:{
        keepAlive: false
      }
    },
    {
      path:'/doctors',
      component:UserDoctors,
      meta:{
        keepAlive: true
      }
    },
    {
      path:'/doctors/info',
      component:AllNfo,
      meta:{
        keepAlive: false
      }
    },
    {
      path:'/patients0',
      component:UserPatients,
      meta:{
        keepAlive: true
      }
    },
    {
      path:'/patients1',
      component:UserPatient,
      meta:{
        keepAlive: true
      }
    },
    {
      path:'/patients/info',
      component:AllNfo,
      meta:{
        keepAlive: false
      }
    },
    {
      path:'/remotations/info',
      component:RemotationInfo,
      meta:{
        keepAlive: false
      }
    },
    {
      path:'/aicheck', 
      component:aiCheck,
      meta:{
        keepAlive: true
      }
    },
    {
      path:'/remotation0', 
      component:UserRemotation0,
      meta:{
        keepAlive: true
      }
    },
    {
      path:'/remotation1', 
      component:UserRemotation1,
      meta:{
        keepAlive: true
      }
    },
    // Radiomics1
    {
      path:'/radiomics1',
      component:Radiomics1,
      meta:{
        keepAlive: false
      }
    },
    // DP1
    {
      path:'/dp1',
      component:DP1,
      meta:{
        keepAlive: false
      }
    }
  ]
})