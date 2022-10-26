<template>
  <div>
    <h1 STYLE="text-align: center;color: gold">欢迎使用本管理系统</h1>
    <div class="block">
      <el-carousel height="70vh" STYLE="margin-top: 20px">
        <el-carousel-item v-for="item in urlList" :key="item">
          <img :src="item" alt="图片加载失败" style="width: 100%;height: 100%">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeMain",
  data(){
    return{
      urlList:[
        "http://localhost:9090/files/goods/home1.png",
        "http://localhost:9090/files/goods/home2.png",
        "http://localhost:9090/files/goods/home3.png",
        "http://localhost:9090/files/goods/home4.png",
        "http://localhost:9090/files/goods/home5.png",
      ],
    }
  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>