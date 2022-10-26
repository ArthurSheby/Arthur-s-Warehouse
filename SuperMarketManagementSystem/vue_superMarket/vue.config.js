const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  lintOnSave:false,//关闭语法检查
  // devServer:{
  //   proxy:{
  //     // 请求前缀/api，只有加了/api前缀的请求才会走代理(前端自定义)
  //     '/api':{
  //       target:'http://localhost:9090',
  //       // pathReweite:{'^/api':''},//重写
  //       //ws:true,//用于支持websocket
  //       // changeOrigin:true,//用于控制请求头中的host值，默认true，react中默认false
  //     }
  //   }
  // }
})
