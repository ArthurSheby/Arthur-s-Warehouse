//引入Vue核心库
import Vue from 'vue'
//引入Vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)

//创建并暴露store
export default new Vuex.Store({
  //准备actions对象——响应组件中用户的动作
  actions:{},
  mutations:{
    setPath(state){
      // console.log("state",state)
      state.currentPathName = localStorage.getItem("currentPathName")
    }
  },
  //准备state对象——保存具体的数据
  state:{
    currentPathName:'',
    loginUser:{},
    options: [{
      value: '服装',
      label: '服装',
      children: [{
        value: '男士服装',
        label: '男士服装',
        children: [{
          value: '夹克',
          label: '夹克'
        }, {
          value: '卫衣',
          label: '卫衣'
        }, {
          value: '牛仔裤',
          label: '牛仔裤'
        }, {
          value: '西装外套',
          label: '西装外套'
        }]
      }, {
        value: '女式服装',
        label: '女式服装',
        children: [{
          value: 'T恤',
          label: 'T恤'
        }, {
          value: '衬衣',
          label: '衬衣'
        }]
      }]
    },
      {//第二大类
        value: '食品',
        label: '食品',
        children: [{
          value: '新鲜水果',
          label: '新鲜水果',
          children: [{
            value: '苹果',
            label: '苹果'
          }, {
            value: '橙子',
            label: '橙子'
          }, {
            value: '奇异果',
            label: '奇异果'
          }, {
            value: '火龙果',
            label: '芒果'
          }, {
            value: '椰子',
            label: '椰子'
          }]
        }, {
          value: '肉蛋类',
          label: '肉蛋类',
          children: [{
            value: '猪肉',
            label: '猪肉'
          }, {
            value: '鸡肉',
            label: '鸡肉'
          }, {
            value: '羊肉',
            label: '羊肉'
          }, {
            value: '牛肉',
            label: '牛肉'
          }, {
            value: '鸭肉',
            label: '鸭肉'
          }, {
            value: '鸡肉',
            label: '鸡肉'
          }, {
            value: '鸡蛋',
            label: '鸡蛋'
          }, {
            value: '鹌鹑蛋',
            label: '鹌鹑蛋'
          },
          ]
        }, {
          value: '休闲零食',
          label: '休闲零食',
          children: [{
            value: '果脯',
            label: '果脯'
          }, {
            value: '肉铺',
            label: '肉铺'
          }, {
            value: '饼干',
            label: '饼干'
          }, {
            value: '果蔬干',
            label: '果蔬干'
          }, {
            value: '糖果',
            label: '糖果'
          }, {
            value: '膨化食品',
            label: '膨化食品'
          }]
        }, {
          value: '地方特产',
          label: '地方特产',
          children: [{
            value: '华东特产',
            label: '华东特产'
          }, {
            value: '华北特产',
            label: '华北特产'
          }, {
            value: '华南特产',
            label: '华南特产'
          }, {
            value: '西南特产',
            label: '西南特产'
          }, {
            value: '西北特产',
            label: '西北特产'
          },{
            value: '东北特产',
            label: '东北特产'
          }]
        }, {
          value: '粮油调味',
          label: '粮油调味',
          children: [{
            value: '食用油',
            label: '食用油'
          }, {
            value: '橄榄油',
            label: '橄榄油'
          }, {
            value: '米面',
            label: '米面'
          }, {
            value: '调味品',
            label: '调味品'
          }, {
            value: '南北干货',
            label: '南北干货'
          }]
        }, ]
      }, {
        value: '饮品',
        label: '饮品',
        children: [{
          value: '茶',
          label: '茶',
          children: [{
            value: '绿茶',
            label: '绿茶',
          },{
            value: '红茶',
            label: '红茶',
          },
            {
              value: '黑茶',
              label: '黑茶',
            },
            {
              value: '乌龙茶',
              label: '乌龙茶',
            },
            {
              value: '白茶',
              label: '白茶',
            },
            {
              value: '花茶',
              label: '花茶',
            },
          ]
        }, {
          value: '酒类',
          label: '酒类',
          children: [
            {
              value:'白酒',
              label:'白酒',
            },
            {
              value:'红酒',
              label:'红酒',
            },
            {
              value:'葡萄酒',
              label:'葡萄酒',
            },
            {
              value:'啤酒',
              label:'啤酒',
            },
            {
              value:'洋酒',
              label:'洋酒',
            },
            {
              value:'黄酒',
              label:'黄酒',
            },
          ]
        }, {
          value: '饮料冲调',
          label: '饮料冲调',
          children: [
            { value: '牛奶乳品',
              label: '牛奶乳品',
            },{
              value: '饮料',
              label: '饮料',
            },
            {
              value: '饮用水',
              label: '饮用水',
            },{
              value: '咖啡',
              label: '咖啡',
            },{
              value: '冲饮谷物',
              label: '冲饮谷物',
            },{
              value: '成人奶粉',
              label: '成人奶粉',
            }
          ]
        }]
      }]
  }
})