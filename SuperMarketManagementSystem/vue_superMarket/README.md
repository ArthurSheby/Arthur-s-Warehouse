# vue_todo_list
## 总结todo_list案例
    1.组件化编程流程：
        （1）.拆分静态组件：组件要按照功能点拆分，命名不要与html元素冲突
        （2）.实现动态组件：考虑号数据的存放位置，数据是一个组件再用，还是一些组件在用；
            1）.一个组件再用：放在组件自身即可
            2）.一些组件再用，放在他们共同的父组件上
        （3）.实现交互
    2.props适用于：
        （1）.父组件==>子组件 通信
        （2）.子组件==》父组件 通信
    3.使用v-model时要切记：v-model绑定的值不能是props传过来的值，因为props是不可以修改的
    4.props传过来的若是对象类型的值，修改对象中的属性时Vue不会报错，但不推荐这样使用
##组件的自定义事件
    1.一种组件之间通信的方式：适用于：子组件 ==> 父组件
    2.使用场景：A是父组件，B是子组件，B想传递数据给A，那么就要在A中给B绑定自定义事件（事件的回调在A中）
    3.绑定自定义事件：
        1.第一种方式：在父组件中：<Demo @atguigu = "test"> 或<Demo v-on:atguigu = "test">
        2.第二种方法：在父组件种：
            <Demo ref = "demo">
            ..........
            mounted(){
                this.$ref.Demo.$on('atguigu',this.test)
            }
        3.若想自定义事件只触发一次，可以使用once修饰符，或$once方法
    4.触发自定义事件：this.$emit('atguigu',数据)
    5.解绑自定义事件：this.off('atguigu')
    6.组件上也可以绑定原生Dom事件，需要使用native修饰符：例如，@click.native = "函数名"
    7.注意：通过this.$refs.组件名.$on('atguigu',回调)绑定自定义事件时，回调要么配置在methods种，要么用箭头函数，否则this的指向会出问题。
## 全局事件总线
    1.一种组件间通信的方式，适用于任意组件之间通信
    2.安装全局事件总线：
        new Vue(){
            ......
            beforeCreate(){
                Vue.prototype.$bus = this//安装全局事件总线，$bus就是当前应用的vm
            }
        }
    3.使用事件总线
        1.接受数据：A组件想要接受数据，则在A组件里面给bus绑定一个自定义事件，事件的回调留在A组件自身
            methods(){
                demo(data){
                conslo.log(data)
                }
            }
            mounted(){
                this.$bus.$on("xxxx",this.demo)
            }
        2.提供数据：this.$bus.$emit('xxxx',数据)
    4.最好在beforeDestory钩子种，用$off去解绑当前组件所用到的事件
## 消息订阅与发布（pubsub）
    1.一种组件间的通信方式，适用于任意组件之间通信
    2.使用步骤：
        1.安装pubsub：npm i pubsub-js
        2.引入：import pubsub from 'pubsub-js'
        3.接受数据：A组件想要接受数据，则A组件中订阅消息，订阅的回调函数留在A组件自身
            methods(){
                demo(data){....}
            } 
            ....
            mounted(){
                this.pid = pubsub.subscribe("xxxx",this.demo)//订阅消息
            }___********___
        4.提供数据：pubsub.publish('xxx',数据)
        5.最好在beforeDestroy钩子中，用pubSub.unsubscribe(pid)去<span style="clolor:red">取消订阅</span>