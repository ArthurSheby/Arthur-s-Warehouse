//该文件用于创建整个应用的路由器
import VueRouter from "vue-router";
import GeneralFramework from "@/components/GeneralFramework";
import UserListMain from "@/components/main/UserListMain";
import HomeMain from "@/components/main/HomeMain";
import LoginPage from "@/components/LoginPage";
import PersonalInforMain from "@/components/main/PersonalInforMain";
import goodsMain from "@/components/main/goodsMain";
import supplierMain from "@/components/main/SupplierMain"
import WarehouseMain from "@/components/main/WarehouseMain";
import salesRecord from "@/components/main/SalesRecordMain"
import purchase from "@/components/main/PurchaseMain"
import store from "@/store";


const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes:[
          {
              path:'/',
              name:'GeneralFramework',
              component:GeneralFramework,
              redirect:"/login",
              children:[
                  {path:'home',name: "首页",component: HomeMain,meta:{title:"首页"}},
                  {path:'user',name:'员工列表',component:UserListMain,meta:{title:"员工列表"}},
                  {path:'PersonalInfor',name:"个人信息",component: PersonalInforMain,meta: {title: "个人信息"}},
                  {path: 'goods',name:'商品列表',component: goodsMain,meta: {title: "货品信息"}},
                  {path: 'supplier',name:'供应商',component: supplierMain,meta: {title: "供应商信息"}},
                  {path: 'warehouse',name:'仓库管理',component: WarehouseMain,meta: {title: "仓库信息"}},
                  {path: 'salesRecord',name:'销售记录管理',component: salesRecord,meta: {title: "销售记录管理"}},
                  {path: 'purchase',name:'采购记录管理',component: purchase,meta: {title: "采购记录管理"}}
                  ]
          },
          {
              path: "/login",
              name:"LoginPage",
              component: LoginPage
          }
      ]
})

router.beforeEach((to, from, next) => {
    if(to.path === '/warehouse' || to.path === '/user'){
        let user = localStorage.getItem('user')?JSON.parse(localStorage.getItem('user')) :null
        console.log("身份 is",user.role)
        if (user.role === "1"){
            console.log("判定user.role",user.role)
            next()
        }
    }else {
        next()
    }
})

router.afterEach((to) =>{
    localStorage.setItem("currentPathName",to.name)
    store.commit("setPath")
    // next()
})
export default router