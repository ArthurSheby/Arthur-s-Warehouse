<template>
  <div style="background-color:#FCFCFC;height:100%;">
    <div>
<!--      <my-bread level1='个人中心' level2='修改密码' :level3="levelName"></my-bread>-->
    </div>
    <div style="margin-top:25px;margin-left:80px;">
      <el-row :gutter="10">
        <el-col :span="2">
          <div style="background-color:#FFEBCD;width:60px;height:60px;display:inline-block;border-radius:50%;overflow:hidden;">
            <el-image :src="src" style="width:60px;height:60px;"></el-image>
          </div>
          <div style="margin-top:5px;margin-left:6px;"><span>{{this.loginPeople.userName}}</span></div>
          <div style="margin-top:50px;margin-left:1px;">
            <el-button type="text" style="font-size:15px;color:#4D4D4D;"  @click="infomationClick()">
              个人信息
              <span style="color:#B0E0E6;" v-show="infomationShow" class="el-icon-s-promotion">
              </span></el-button>
          </div>
          <div style="margin-top:5px;margin-left:1px;"><el-button type="text" style="font-size:15px;color:#4D4D4D;" @click="passwordClick()">
            修改信息
            <span style="color:#B0E0E6;" v-show="passwordShow" class="el-icon-s-promotion">
            </span></el-button>
          </div>
        </el-col>

        <el-col :span="21" style="display: flex;flex-direction: column">
          <el-row :gutter="10" style="margin-top:10px;">
            <el-col :span="1.5"><div style="text-align:left;"><span>员工编号：</span></div></el-col>
            <el-col :span="5">{{this.$store.state.loginUser.id}}</el-col>
          </el-row>
          <!-- 个人信息 -->
          <el-row v-show="infomationShow">
            <el-card style="margin-top:20px;height: 450px">
              <el-row STYLE="margin-top: -25px;margin-left: -10px">
                <el-col :span="10">
                  <el-row :gutter="12" style="margin-top:20px;">
                    <el-col :span="6"><div style="text-align:left;"><span>姓名：</span></div></el-col>
                    <el-col :span="16">{{this.loginPeople.username}}</el-col>
                  </el-row>
                  <el-row :gutter="12" style="margin-top:30px;">
                    <el-col :span="6"><div style="text-align:left;"><span>手机号：</span></div></el-col>
                    <el-col :span="16">{{this.loginPeople.phone}}</el-col>
                  </el-row>
                  <el-row :gutter="12" style="margin-top:30px;">
                    <el-col :span="6"><div style="text-align:left;"><span>邮箱：</span></div></el-col>
                    <el-col :span="16">{{this.loginPeople.email}}</el-col>
                  </el-row>
                  <el-row :gutter="12" style="margin-top:30px;">
                    <el-col :span="6"><div style="text-align:left;"><span>生日：</span></div></el-col>
                    <el-col :span="16">{{this.loginPeople.birthday}}</el-col>
                  </el-row>
                  <el-row :gutter="12" style="margin-top:30px;">
                    <el-col :span="6"><div style="text-align:left;"><span>职位：</span></div></el-col>
                    <el-col :span="16">{{this.loginPeople.role}}</el-col>
                  </el-row>
                  <el-row :gutter="12" style="margin-top:30px;">
                    <el-col :span="6"><div style="text-align:left;"><span>籍贯：</span></div></el-col>
                    <el-col :span="16">{{this.loginPeople.address}}</el-col>
                  </el-row>
                </el-col>
                <el-col :span="14">
                  <el-row>
                    <el-col>
                      <el-collapse v-model="activeNames" @change="handleChange">
                        <el-collapse-item title="通知 Information" name="1">
                          <div>与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；</div>
                          <div>在界面中一致：所有的元素和结构需保持一致，比如：设计样式、图标和文本、元素的位置等。</div>
                        </el-collapse-item>
                        <el-collapse-item title="任务 Order" name="2">
                          <div>控制反馈：通过界面样式和交互动效让用户可以清晰的感知自己的操作；</div>
                          <div>页面反馈：操作后，通过页面元素的变化清晰地展现当前状态。</div>
                        </el-collapse-item>
                        <el-collapse-item title="效率 Efficiency" name="3">
                          <el-table
                              :data="tableData"
                              style="width: 100%">
                            <el-table-column
                                prop="date"
                                label="日期"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="name"
                                label="姓名"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="address"
                                label="地址">
                            </el-table-column>
                          </el-table>
                        </el-collapse-item>
                        <el-collapse-item title="出勤记录 AttendanceRecord" name="4">
                          <div>用户决策：根据场景可给予用户操作建议或安全提示，但不能代替用户进行决策；</div>
                          <div>结果可控：用户可以自由的进行操作，包括撤销、回退和终止当前操作等。</div>
                        </el-collapse-item>
                      </el-collapse>
                    </el-col>
                  </el-row>

                </el-col>
              </el-row>
            </el-card>
          </el-row>
          <!-- 修改密码 -->
          <el-row v-show="passwordShow">
            <el-card style="margin-top:20px;height: 450px">
              <el-form :model="personalForm">
                <el-row :gutter="12" style="margin-top:1px;">
                  <el-col :span="6">
                    <div style="text-align:right;"><span>绑定的手机号：</span></div>
                  </el-col>
                  <el-col :span="5">
                    <el-form-item prop="phone_num">
                      <el-input type="text" v-model="personalForm.phone_num" placeholder="绑定的手机号" ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="12" style="margin-top:1px;">
                  <el-col :span="6">
                    <div style="text-align:right;"><span>密码：</span></div>
                  </el-col>
                  <el-col :span="5">
                    <el-form-item prop="password">
                      <el-input type="text" v-model="personalForm.password1" placeholder="请输入新的密码" ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="12" style="margin-top:1px;">
                  <el-col :span="6">
                    <div style="text-align:right;"><span>确认密码：</span></div>
                  </el-col>
                  <el-col :span="5">
                    <el-form-item prop="password">
                      <el-input type="text" v-model="personalForm.password2" placeholder="请再次输入新的密码" ></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="12" style="margin-top:10px;">
                  <el-col :span="17" style="text-align:center;">
                    <el-button type="primary" plain round size="medium" @click="submit()">修改</el-button>
                  </el-col>
                </el-row>
              </el-form>
            </el-card>
          </el-row>
        </el-col>
      </el-row>
    </div>
    <div>
    </div>
  </div>
</template>

<script>
import picture from '../../assets/picture/shop.png'
export default {
    name: "PersonalInforMain",
    data(){
      return{
        loginPeople:{},//登陆的信息表单
        tableData:[],
        activeNames: ['1'],
        currentDate: this.formatDateTime(new Date()),
        src:picture,
        levelName:'',
        isCollapse:false,
        infomationShow:true,
        passwordShow:false,
        buttonName:"发送短信",
        isDisabled:false,
        time:60,
        personalForm:{
          phone_num:"",
          password1:"",
          password2:"",
        },
      }
    },
  methods:{
    formatDateTime(date) {
      let y = date.getFullYear()
      let m = date.getMonth() + 1
      m = m < 10 ? ('0' + m) : m
      let d = date.getDate()
      d = d < 10 ? ('0' + d) : d
      let h = date.getHours()
      h=h < 10 ? ('0' + h) : h
      let minute = date.getMinutes()
      minute = minute < 10 ? ('0' + minute) : minute
      let second=date.getSeconds()
      second=second < 10 ? ('0' + second) : second
      return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second
    },
    submit(){  //提交
      if(this.personalForm.password1 !== ""){
        if (this.personalForm.password2 === ""){
          this.$message.warning("请再吃输入密码")
        }else {
          if(this.personalForm.password1 === this.personalForm.password2){
            let loginUser = JSON.parse(localStorage.getItem('user'))
            loginUser.userPassword = this.personalForm.password1
            if (this.personalForm.phone_num !== ""){
              loginUser.phone = this.personalForm.phone_num
            }
            this.request.post("/user/insertUser",loginUser).then(res=>{
              if (res){
                this.$message.success("修改成功,请重新登录")
                console.log("修改完密码后的loginUser is ",loginUser)
                setTimeout(function(){
                  window.location.href="http://localhost:8080/login"
                },1000)
              }else {
                this.$message.error("操作出错......")
              }
            })
          }else{
            this.$message.warning("两次输入的密码不一致,请重新输入")
          }
        }
      }else {
        this.$message.warning("修改的数据不能为空")
      }
    },

    infomationClick(){    //个人信息事件
      this.infomationShow = true
      this.passwordShow = false
    },

    passwordClick(){     //密码事件
      this.infomationShow = false
      this.passwordShow = true
    },
    sendMsg() {    //时间按钮
      let me = this;
      me.isDisabled = true;
      let interval = window.setInterval(function() {
        me.buttonName = "(" + me.time + ")秒"
        --me.time;
        if(me.time < 0) {
          me.buttonName = "重新发送"
          me.time = 60
          me.isDisabled = false
          window.clearInterval(interval)
        }
      }, 1000);
      //获取短信验证码
      // this.$http.get('/?phone_num=' + this.personalForm.phone_num).then(res => {
      //     this.$message.success("短信已发送,请查收")
      // })
    },
    handleChange(val) {
      console.log(val);
    }
  },
  mounted() {
    console.log("personInfoMain中的localStorge",JSON.parse(window.localStorage.getItem("user")))
    this.loginPeople = JSON.parse(window.localStorage.getItem("user"))
    console.log(this.loginPeople)
      if (this.loginPeople.role === "2"){
        this.loginPeople.role = "员工"
      }else if (this.loginPeople.role === "1"){
        this.loginPeople.role = "经理"
      }else if(this.loginPeople.role === "3"){
        this.loginPeople.role = "仓库管理员"
      }
  }
}
</script>

<style scoped>
  .name{ text-align: right; }
  .value{ text-align: left; }
</style>