<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%;overflow-x: hidden;"
           background-color="rgb(48,65,86)"
           menu-trigger="hover"
           text-color="#fff"
           active-text-color="#ffd04b"
           router
           :collapse-transition="false"
           :collapse="isCollapse"
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="../assets/picture/shop.png" style="width: 20px;position: relative;top: 5px" alt="图片走丢了">
      <b style="color: gold" v-show="!isCollapse">超市后台管理系统</b>
    </div>
<!--    主页-->
    <el-submenu index="6">
      <template slot="title">
        <i class="el-icon-message"></i>
        <span>主页</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/home">主页</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
<!--    第1大分类-->
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-message"></i>
        <span>销售管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/salesRecord">销售记录</el-menu-item>
      </el-menu-item-group>
<!--      <el-menu-item-group>-->
<!--        <el-menu-item index="1-2">数据统计</el-menu-item>-->
<!--      </el-menu-item-group>-->
    </el-submenu>
<!--    第2大分类-->
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-message"></i>
        <span>采购管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/purchase">采购记录</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
<!--    第3大分类-->
    <el-submenu index="3">
      <template slot="title"><i class="el-icon-menu"></i>
        <span>商品管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/goods">商品管理</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
<!--    第4大分类-->
    <el-submenu index="4">
      <template slot="title"><i class="el-icon-setting"></i>
        <span>库存管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/warehouse">仓库管理</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <!--    第5大分类-->
    <el-submenu index="5">
      <template slot="title"><i class="el-icon-user-solid"></i>
        <span>信息管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/PersonalInfor">个人中心</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/user">员工信息管理</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group>
        <el-menu-item index="/supplier">供应商管理</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "AsideMenu",
  props:{
    isCollapse:Boolean,
  },
}
</script>

<style>

</style>