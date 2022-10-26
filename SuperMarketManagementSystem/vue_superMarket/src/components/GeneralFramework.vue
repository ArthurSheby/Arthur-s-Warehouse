<template>
  <el-container style="min-height: 100vh;">
      <el-aside :width="sideWidth+'px'">
        <AsideMenu :isCollapse = "isCollapse"/>
      </el-aside>

    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #cccccc;flex-basis: 7%">
        <ContainerHeader :collapseBtnClass = "collapseBtnClass" :collapse = "collapse"/>
      </el-header>

      <el-main style="flex: 0;flex-basis: 90%; ">
<!--        当前页面的子路由会在此展示-->
        <router-view/>
      </el-main>
      <el-footer style="margin-top: 20px;flex-basis: 3%">天津市兴龙软件开发</el-footer>
    </el-container>
  </el-container>
</template>

<script>
import 'animate.css'
import AsideMenu from "@/components/AsideMenu";
import ContainerHeader from "@/components/ContainerHeader";
export default {
  name: "GeneralFramework",
  components: {AsideMenu,ContainerHeader},
  data() {
    return {
      isCollapse:false,
      collapseBtnClass:'el-icon-s-fold',
      sideWidth:200,
    }
  },
  methods:{
    //导航栏收缩
    collapse(){
      this.isCollapse = !this.isCollapse
      if (this.isCollapse){//收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
      }else {//展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
      }
    },
  },
}
</script>

<style scoped>
.el-header,.el-footer{
  background-color:#2C3E50;
  box-shadow:2px 0 6px rgba(0,21,41,0.35);
  color: #333;
  line-height: 60px;
}

.el-header{
  color: gold;
}

.el-footer{
  text-align: center;
  color: gold;
}
.el-aside {
  color: #333;
  background-color: rgb(238, 241, 246);
  height: 100vh;
  box-shadow:2px 0 6px rgba(0,21,41,0.35);
}
/*滚动条样式*/
.el-aside::-webkit-scrollbar {
  width: 0;
}
.el-main::-webkit-scrollbar {
  width: 0;
}
.el-aside::-webkit-scrollbar-thumb {
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
  background: rgba(0,0,0,0.2);
}
.el-aside::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
  border-radius: 0;
  background: rgba(0,0,0,0.1);
}
</style>