<template>
<div>
  <div style="padding: 10px 0">
    <el-input v-model="inputName" style="width: 200px;" placeholder="请输入用户名称" suffix-icon="el-icon-user"></el-input>
    <el-input v-model="inputAddress" style="width: 200px;margin-left: 5px" placeholder="请输入用户籍贯" suffix-icon="el-icon-shopping-bag-2"></el-input>
    <el-button @click="handleSearch" icon="el-icon-search"  type="primary" style="margin-left: 5px">搜索</el-button>
    <el-button @click="handleClear" icon="el-icon-scissors"  type="warning" style="margin-left: 5px">清空</el-button>
  </div>

  <div style="margin: 10px 0">
    <el-button type="primary" @click="openFrom">员工入职<i class="el-icon-circle-plus-outline"></i></el-button>
    <!--          批量删除确认框-->
    <el-dialog title="批量删除确认" :visible.sync="dialogTableVisible">
      <el-table :data="multipleSelection">
        <el-table-column property="id" label="员工ID" width="150"></el-table-column>
        <el-table-column property="userName" label="姓名" width="200"></el-table-column>
        <el-table-column property="address" label="籍贯"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteManyUser();dialogTableVisible = false">确 定</el-button>
      </div>
    </el-dialog>
    <el-button type="danger" @click="dialogTableVisible = true" STYLE="margin-left: 10px">批量删除<i class="el-icon-remove-outline"></i></el-button>
  </div>

  <!--        人员信息表格-->
  <el-table :data="tableData" border stripe v-loading="loading"  @selection-change="handleSelectionChange">
    <el-table-column
        type="selection"
        width="40">
    </el-table-column>
    <el-table-column prop="id" label="用户ID" width="140" align="center"></el-table-column>
    <el-table-column prop="userName" label="员工名称" width="120" align="center"></el-table-column>
    <el-table-column prop="role" label="员工职责" width="120" align="center" :formatter="formatRole"></el-table-column>
    <el-table-column prop="phone" label="电话" align="center"></el-table-column>
    <el-table-column prop="email" label="邮箱地址" align="center"></el-table-column>
    <el-table-column prop="address" label="籍贯" align="center" width="100"></el-table-column>
    <el-table-column prop="birthday" label="出生日期" align="center"></el-table-column>
    <el-table-column prop="createtime" label="入职日期" :formatter="formatDate" align="center"></el-table-column>
    <el-table-column label="操作" align="center">
      <template slot-scope="scope">
        <el-button @click="handleEdit(scope.row)" type="success">编辑</el-button>
        <el-button type="danger" @click="openDeleteDialog(scope.row)">删除</el-button>
        <!--              删除气泡框-->
        <el-dialog style="text-align: left;"
                   title="警告"
                   :visible.sync="dialogVisible"
                   width="30%"
        >
          <span >确定要注销员工:{{form.id}}  {{form.userName}}</span>
          <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="handleDelete(form.id);dialogVisible = false">确 定</el-button>
                </span>
        </el-dialog>
      </template>
    </el-table-column>
  </el-table>

  <div class="block" style="padding: 2px 0;text-align: right;margin-top: 10px">
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
  <!--        员工信息表单-->
  <el-dialog title="员工信息" :visible.sync="dialogFormVisible">
    <el-form label-width="120px">
      <el-form-item label="员工名称" >
        <el-input v-model="form.userName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="员工性别" >
        <el-input v-model="form.gender" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="设置密码" >
        <el-input v-model="form.userPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="籍贯">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱地址">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="出生日期">
        <el-input v-model="form.birthday" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="员工角色">
        <el-input v-model="form.role" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveRegister()">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "UserListMain",
  data(){
    return{
      inputAddress:'',
      inputName:'',
      loading:false,
      dataNumber:0,
      dialogFormVisible:false,//员工入职注册表单是否打开
      dialogTableVisible:false,
      dialogVisible: false,
      form:{},
      multipleSelection:[],
      pageNum:1,
      pageSize:5,
      tableData: [],
    }
  },
  methods:{
    //向后台请求数据，绑定到前端
    renderData(){
      this.request.get("/user/getPartUser",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          userName:this.inputName,
          address:this.inputAddress
        }}).then(res =>{
        // console.log(res)
        this.tableData = res.records
        this.dataNumber = res.total
      })
    },
    //处理容量变化，换页
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.renderData()
    },
    //处理换页
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.renderData()
    },
    //搜索处理
    handleSearch(){
      this.loading = true
      this.renderData()
      this.loading = false
    },
    //时间格式处理函数
    formatDate(row, column) {
      // 获取单元格数据
      let data = row[column.property];
      if(data == null) {
        return null;
      }
      let dt = new Date(data)
      return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
    },
    //员工角色处理函数
    formatRole(row, column) {
      // 获取单元格数据
      let data = row[column.property];
      let newData = ""
      if(data == null) {
        return null;
      }
      if (data === "1"){
        newData = "经理"
      }else if(data === "2"){
        newData = "员工"
      }
      return newData
    },
    //清除搜索框内容
    handleClear(){
      this.inputAddress = ""
      this.inputName = ""
      this.renderData()
    },
    //打开员工信息form
    openFrom(){
      this.dialogFormVisible = true
      this.form = {}
    },
    //注册人员保存
    saveRegister(){
      this.request.post("/user/insertUser",this.form).then(res=>{
        this.dialogFormVisible = false
        if (res){
          this.$message.success("操作成功！！")
        }else {
          this.$message.error("操作出错......")
        }
      })
    },
    //员工信息删除
    handleDelete(id){
      this.request.delete("/user/deleteUser/"+id).then(res=>{
        if (res){
          this.$message.success("删除成功!!!")
          this.renderData()
        }else {
          this.$message.error("删除失败...")
        }
      })
    },
    //员工信息修改
    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true
    },
    //打开删除气泡框
    openDeleteDialog(row){
      this.form = row
      this.dialogVisible = true
    },
    //勾选多人处理
    handleSelectionChange(val){
      console.log("多选之后的val",val)
      this.multipleSelection = val
      console.log("多选之后的multipleSelection",this.multipleSelection)
    },
    //批量删除
    deleteManyUser(){
      let idList = this.multipleSelection.map(v => v.id)
      console.log(idList)
      this.request.post("/user/deleteManyUser",idList).then(res=>{
        if (res){
          this.renderData()
          this.$message.success("批量删除成功！！！")
        }else {
          this.$message.error("删除失败...")
        }
      })
    },
  },
  created() {
    this.renderData()
  }
}
</script>

<style>

</style>