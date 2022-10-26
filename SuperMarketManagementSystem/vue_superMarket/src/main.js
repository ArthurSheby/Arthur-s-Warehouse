import Vue from 'vue/dist/vue'
import App from './App.vue'
//引入VueRouter
import VueRouter from "vue-router";
//ElementUI组件库
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.config.productionTip = false

import router from "@/router";
import './assets/css/global.css'
import vuex from 'vuex'
import store from './store/index'
import request from "@/utils/request";

Vue.use(VueRouter)
Vue.use(ElementUI,{size:'mini'})
Vue.use(vuex)
Vue.prototype.request = request

ElementUI.Dialog.props.lockScroll.default = false;
new Vue({
  render:h =>h(App),
  router:router,
  store:store,
  beforeCreate() {
    Vue.prototype.$bus = this
  }
}).$mount("#app")
// console.log("vm中的$store",vm.$store.state)