<template>
  <div id="pro_form" style="position: relative;width: 100%;height: 638px;">
<!--    标题框-->
    <div STYLE="height: 40px;display: inline-block;text-align: center;line-height: 40px;position: relative;left: 1365px">
      <h1 style="height: 28px;line-height: 28px;color: gold">{{this.$route.meta.title}}</h1>
    </div>
    <div style="position: relative;top: 0;left:0;">
      <!--    搜索查询区-->
      <el-row :gutter="8" style="margin-left: 57px;margin-right: -12px">
        <el-col :span="4" style="width: 260px;">
          <el-card shadow="always" >
            <el-button style="align-items: center;width: 210px;height: 50px" type="primary" @click="createWareHouseDialogForm = true">新建仓库信息</el-button>
          </el-card>
        </el-col>
        <el-col :span="15" style="margin-left: 3%">
          <el-card shadow="always" STYLE="width: 500px" >
            <div style="padding:0;margin-top: 0;">
            <el-input v-model="inputWarehouseName" style="width: 200px;" placeholder="请输入仓库名称" suffix-icon="el-icon-user"></el-input>
            <el-select v-model="value" clearable placeholder="仓库的地址" style="margin-left: 5px">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
            <el-button @click="handleSearch" icon="el-icon-search"  type="primary" style="width: 210px;height: 50px;margin-left: 5px">搜索</el-button>
              <el-button @click="qingkongSearch" icon="el-icon-search"  type="primary" style="width: 210px;height: 50px;margin-left: 5px">清空</el-button>
          </div>
          </el-card>
        </el-col>
      </el-row>
<!--      内容-->
      <el-row>
        <!--就改这里一行-->
        <el-col :span="4" v-for="(project,index) in allprojects" :key="index" :offset="1" style="margin-bottom:40px">
          <el-card :body-style="{ padding: '0px'}" shadow="hover" style="width: 260px;height: 320px;">
            <div style="padding: 6px;height: 310px;">
              <div>
                <div>{{project.name}}</div>
                <div style="position: relative;top: 15px;text-align: center;">{{project.address}}</div>
              </div>
              <div style="position: relative;top: 30px;">
                <el-image :src="project.picture" class="image" alt="图片加载失败" style="height: 164px"></el-image>
                <div style="position: relative;top: 10px;left: 0;">
                  <i class="el-icon-time"></i>
                  {{project.createTime}}
                </div>
              </div>
              <div style="position: relative;top: 45px;">
<!--                <i class="el-icon-view"></i><span>{{project.ppageview}}</span>-->
                <el-switch
                    style="display: inline-block"
                    v-model="project.isUse"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    active-text="启用"
                    inactive-text="禁用">
                </el-switch>
                  <el-button type="text" style="margin-left: 70px" class="el-switch">
                    <span STYLE="font-size: 14px;line-height: 0;display: inline-block">查看详情</span>
                  </el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <!--      分页插件-->
      <div class="block" style="padding: 2px 0;text-align: right;margin-top: 10px;position: relative;top:-5px;right: 205px">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 8, 10]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="dataNumber">
        </el-pagination>
      </div>
<!--      新建仓库表单-->
      <el-dialog title="仓库信息" :visible.sync="createWareHouseDialogForm">
        <el-form label-width="120px">
          <el-form-item label="仓库图片" >
            <el-upload
                class="upload-demo"
                action="http://localhost:9090/files/upload"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="handleUploadSuccess"
                :file-list="pictureList"
                list-type="picture">
              <el-button size="small" type="primary">上传图片</el-button>
              <div slot="tip" class="el-upload__tip" style="display: inline-block;margin-left: 10px">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="仓库名称" >
            <el-input v-model="wareHouseForm.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="负责人ID" >
            <el-input v-model="wareHouseForm.chargePersonId" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="仓库状态">
            <el-switch
                style="display: inline-block"
                v-model="wareHouseForm.isUse"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="启用"
                inactive-text="禁用">
            </el-switch>
          </el-form-item>
          <el-form-item label="仓库地址">
            <el-input v-model="wareHouseForm.address" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="仓库容量" >
            <el-input v-model="wareHouseForm.volume" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="createWareHouseDialogForm = false">取 消</el-button>
          <el-button type="primary" @click="saveRegisterWareHouse()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "WarehouseMain",
  data(){
    return{
      pictureList: [],//图片列表
      wareHouseForm:{
        picture:"",
      },//表单数据
      createWareHouseDialogForm:false,
      dataNumber:0,
      pageNum:1,
      pageSize:4,
      inputWarehouseName:"",
      allprojects:[],
      currentDate: new Date(),
      options: [{
        value: '天津',
        label: '天津'
      }, {
      value: '北京',
          label: '北京'
      }, {
        value: '重庆',
            label: '重庆'
      }, {
        value: '上海',
            label: '上海'
      }, {
        value: '南京',
            label: '南京'
      }],
      value: ''
    }
  },
  computed:{
    //动态加载Main的图片卡
    pages(){
      const pages = []
      this.allprojects.forEach((item, index) => {
        const page = Math.floor(index / 4)//4代表4条为一行，随意更改
        if (!pages[page]) {
          pages[page] = []
        }
        pages[page].push(item)
      })
      return pages
    }
  },
  methods:{
    //添加仓库保存
    saveRegisterWareHouse(){
      console.log("saveRegisterWareHouse方法中 ",this.wareHouseForm)
      this.request.post("/warehouse/insertWareHouse",this.wareHouseForm).then(res=>{
        this.createWareHouseDialogForm = false
        if (res){
          this.renderWareHouseData()
          this.$message.success("操作成功！！")
        }else {
          this.$message.error("操作出错......")
        }
      })
    },
    // 上传图片
    handleRemove(file, fileList) {
      console.log("handleRemove",file, fileList);
    },
    handlePreview(file) {
      console.log("handlePreview",file);
    },
    handleUploadSuccess(res){
      //res就是文件的路径
      this.wareHouseForm.picture = res
      console.log("修改图片之后的form",this.form.picture)
    },
    //向后台请求数据，绑定到前端
    renderWareHouseData(){
      this.request.get("/warehouse/getPartWareHouse",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          wareHouseName:this.inputWarehouseName,
          address:this.value
        }}).then(res =>{
          for (let wareHouse of res.records){
            if (wareHouse.isUse){
              wareHouse.isUse = true
            }else if (wareHouse.isUse === 2){
              wareHouse.isUse = false
            }
            wareHouse.createTime = this.formatDate(wareHouse.createTime)
          }
        this.allprojects = res.records
        console.log(this.allprojects)
        this.dataNumber = res.total
      })
    },
    //搜索处理
    handleSearch(){
      this.loading = true
      this.renderWareHouseData()
      this.loading = false
      console.log("选中的值为    ",this.valueOfCascader)
      console.log("选择表的数据为    ",this.optionValue)
    },
    //清空搜索框
    qingkongSearch(){
      this.inputWarehouseName = ""
      this.value = ""
      this.renderWareHouseData()
    },
    //处理容量变化，换页
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.renderWareHouseData()
    },
    //处理换页
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.renderWareHouseData()
    },
    //时间格式处理函数
    formatDate(time) {
      // 获取单元格数据
      if(time == null) {
        return null;
      }
      let dt = new Date(time)
      return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
    },

  },
  created() {
    this.renderWareHouseData()
  }
}
</script>

<style scoped>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
.el-row {
  margin-bottom: 20px;
  display:flex;
  flex-wrap: wrap;
 }
.el-row  .el-card {
  min-width: 100%;
  height: 100%;
  margin-right: 20px;
  transition: all .5s;
}
</style>