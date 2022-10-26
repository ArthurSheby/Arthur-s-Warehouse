<template>
  <div id="login">
    <transition appear name="opacitytrans">
      <div class="container" id="container">
        <div class="form-container sign-in-container">
          <el-form>
            <div style=";width: 100px;height: 100px">
              <img src="../assets/picture/loginLogo.png" height="100" width="100" alt="图片加载失败">
            </div>
            <h1>超市后台管理系统</h1>
            <span>Version 1.0.0</span>
            <label>
              <input type="loginText" placeholder="账号" @blur="testPhone" v-model="user.phone" />
            </label>
            <label>
              <input  type="password" placeholder="密码" @blur="testPassword" v-model="user.userPassword" />
            </label>
            <div class="button" @click="handleLogin()" >登录</div>
            <a style="align-self:flex-end;color: tomato">{{msg}}</a>
          </el-form>
        </div>
      </div>
    </transition>
  </div>
</template>


<script>
export default {
  name: "LoginPage",
  data() {
    return {
      msg:"",
      user:{
        phone: "",
        userPassword: "",
      },
    };
  },
  methods:{
    handleLogin(){
      console.log(this.user)
      this.request.post("/user/login",this.user).then(res=>{
        console.log("登录返回结果",res)
        if (!res.isLogin){
          this.$message.error("用户名或密码错误")
        }else {
          window.localStorage.setItem("user",JSON.stringify(res.user))
          console.log("验证是否写入localStorage",window.localStorage.getItem("user"))
          this.$store.state.loginUser = res.user
          console.log("验证是否写入state",this.$store.state.loginUser)
          this.$router.push("/home")
        }
      })
    },
    testPhone(){
      if (this.user.phone === ""){
        this.msg = "账号不能为空"
      }else {
        this.msg = ""
      }
    },
    testPassword(){
      if (this.user.userPassword === ""){
        this.msg = "密码不能为空"
      }else {
        this.msg = ""
      }
    }
  }
};
</script>

<style>
#login {
  font-family: "Montserrat", sans-serif;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url('../assets/picture/backPicture.png');
  background-size: cover;
}
.logo {
  width: 160px;
  height: auto;
}
h1 {
  font-weight: bold;
  margin: 0;
  color: #1BBFB4;
}

p {
  font-size: 14px;
  font-weight: bold;
  line-height: 20px;
  letter-spacing: 0.5px;
  margin: 20px 0 30px;
}

span {
  font-size: 12px;
}

a {
  color: #fff;
  font-size: 14px;
  text-decoration: none;
  margin: 15px 0;
}

.container {
  border-radius: 10px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  position: absolute;
  overflow: hidden;
  width: 768px;
  max-width: 100%;
  min-height: 480px;
  opacity: 0.8;
}

.form-container form {
  background: rgba(45, 52, 54, 1);
  display: flex;
  flex-direction: column;
  padding: 0 50px;
  height: 100%;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.social-container {
  margin: 20px 0;
}

.social-container a {
  border: 1px solid #ddd;
  border-radius: 50%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin: 0 5px;
  height: 40px;
  width: 40px;
}

.form-container input {
  background: #eee;
  border: none;
  padding: 12px 15px;
  margin: 8px 0;
  width: 100%;
}

.button {
  cursor: pointer;
  border-radius: 20px;
  border: 1px solid #1BBFB4;
  background: #1BBFB4;
  color: #fff;
  font-size: 12px;
  font-weight: bold;
  padding: 12px 45px;
  margin-top: 20px;
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
}

input[type="loginText"] {
  width: 240px;
  text-align: center;
  background: transparent;
  border: none;
  border-bottom: 1px solid #fff;
  font-family: "PLay", sans-serif;
  font-size: 16px;
  padding: 10px 0;
  transition: border 0.5s;
  outline: none;
  color: #fff;
  font-weight: bold;
}

input[type="password"] {
  width: 240px;
  text-align: center;
  background: transparent;
  border: none;
  border-bottom: 1px solid #fff;
  font-family: "PLay", sans-serif;
  font-size: 16px;
  font-weight: bold;
  padding: 10px 0;
  transition: border 0.5s;
  outline: none;
  color: #fff;
}

.button:active {
  transform: scale(0.95);
}

.button:focus {
  outline: none;
}

.button.ghost {
  background: transparent;
  border-color: #1BBFB4;
  margin: 0;
}

.form-container {
  position: absolute;
  top: 0;
  height: 100%;
}

.sign-in-container {
  left: 20%;
  width: 50%;
  z-index: 2;
}
</style>