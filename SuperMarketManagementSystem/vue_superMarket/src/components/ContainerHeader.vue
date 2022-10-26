<template>
  <div style="line-height: 60px;display: flex">
    <div STYLE="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px;" @click="collapse"></span>
<!--      路由面包屑-->
      <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px;">
        <el-breadcrumb-item :to="{ path: '/' }"><a style="color: gold">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item ><a style="color: gold">{{currentPathName}}</a></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 110px; cursor: pointer">
      <span STYLE="color: gold">{{user.username}}</span><i class="el-icon-arrow-down" style="margin-left: 5px;color: gold"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item style="text-align: center;color: gold">个人信息</el-dropdown-item>
        <router-link to="/login" style="text-decoration: none">
          <el-dropdown-item style="text-align: center;text-decoration: none;margin-top: 5px;color: gold">退出</el-dropdown-item>
        </router-link>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import { mapState} from 'vuex';
export default {
  name: "ContainerHeader",
  data(){
    return{
      user:JSON.parse(window.localStorage.getItem("user"))
    }
  },
  props:{
    collapseBtnClass:String,
    collapse:Function
  },
  computed:{
      ... mapState({
        currentPathName:'currentPathName',
      }),
  },
  methods:{
  },
  created () {
  },
  watch: {
  },
}
</script>

<style>

</style>