<template>
<div>
  <div style="padding: 10px 0;display: inline-block">
<!--    <el-input v-model="inputName" style="width: 200px;" placeholder="请输入销售记录名称" suffix-icon="el-icon-user"></el-input>-->
<!--    时间选择器-->
    <el-date-picker
        v-model="timeValue"
        format="yyyy-MM-dd HH:mm:ss"
        value-format="yyyy-MM-dd"
        type="date"
        placeholder="选择日期">
    </el-date-picker>
<!--    经销人选择器-->
    <el-select v-model="salePerson" placeholder="请选择销售人" style="margin-left: 10px;">
      <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
    <el-button @click="handleSearch" icon="el-icon-search"  type="primary" style="margin-left: 5px">搜索</el-button>
    <el-button @click="handleClear" icon="el-icon-scissors"  type="warning" style="margin-left: 5px">清空</el-button>
  </div>
  <div STYLE="display: inline-block;text-align: right;float: right;">
    <h1 style="height: 28px;line-height: 28px;color: gold">{{this.$route.meta.title}}</h1>
  </div>
  <!-- 批量删除确认框-->
  <div style="margin: 10px 0">
    <el-dialog title="批量删除确认" :visible.sync="dialogTableVisible">
      <el-table :data="multipleSelection">
        <el-table-column property="id" label="销售订单ID" width="150"></el-table-column>
        <el-table-column property="seller" label="经销人" width="200"></el-table-column>
        <el-table-column property="createTime" label="时间"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteManyUser();dialogTableVisible = false">确 定</el-button>
      </div>
    </el-dialog>
    <el-button type="danger" @click="dialogTableVisible = true" STYLE="margin-left: 10px">批量删除<i class="el-icon-remove-outline"></i></el-button>
  </div>

  <!-- 销售记录信息表格-->
  <el-table :data="tableData" border stripe v-loading="loading"  @selection-change="handleSelectionChange">
    <el-table-column
        type="selection"
        width="40">
    </el-table-column>
    <el-table-column prop="id" label="销售记录ID" align="center"></el-table-column>
    <el-table-column prop="orderId" label="销售单号"  align="center"></el-table-column>
    <el-table-column prop="seller" label="经销人"  align="center"></el-table-column>
    <el-table-column prop="paymentAmount" label="支付金额" width="100" align="center">
      <template slot-scope="scope">
        {{scope.row.paymentAmount}}￥
      </template>
    </el-table-column>
    <el-table-column prop="changeAmount" label="找零" align="center" width="100"></el-table-column>
    <el-table-column prop="payMethod" label="支付方式" width="100" align="center">
      <template slot-scope="scope">
        <el-popover placement="top-start" title="" trigger="hover">
          <img slot="reference" :src="scope.row.payMethod" style="width: 70px;height: 50px" alt="图片加载失败">
          <img :src="scope.row.payMethod" alt="图片加载失败" style="width: 150px;height: 150px">
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="订单创建时间" align="center" :formatter="formatDate"></el-table-column>
    <el-table-column label="操作" align="center">
      <template slot-scope="scope">
        <el-button @click="handleEdit(scope.row)" type="success"> 详细情况</el-button>
        <el-button type="danger" @click="openDeleteDialog(scope.row)">删除</el-button>
        <!--              删除气泡框-->
        <el-dialog style="text-align: left;"
                   title="警告"
                   :visible.sync="dialogVisible"
                   width="30%"
        >
          <span >确定要删除销售记录:{{form.id}} &nabla;经销人 {{form.seller}}</span>
          <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="handleDelete(form.id);dialogVisible = false">确 定</el-button>
                </span>
        </el-dialog>
      </template>
    </el-table-column>
  </el-table>

<!--  分页组件-->
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
  <!-- 销售记录信息表单-->
  <el-dialog title="销售记录" :visible.sync="dialogFormVisible">
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
  name: "SalesRecordMain",
  data(){
    return{
      timeValue: '',// 选择器时间值
      salePerson:'',// 选择销售人值
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
      //经销人选项
      options: [],
    }
  },
  methods:{
    //向后台请求数据，绑定到前端
    renderData(){
      this.request.get("/sale/getPartRecord",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          timeValue:this.timeValue,
          salePerson:this.salePerson
        }}).then(res =>{
        this.options = []
        for (let record of res.records){
          let option = {}
          this.$set(option,"value",record.seller)
          this.$set(option,"label",record.seller)
          this.options.push(option)
        }
        console.log("getPartRecord请求回来的数据为",res.records)
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
      console.log("this.timeValue is ",this.timeValue)
      console.log("this.salePerson is ",this.salePerson)
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
    //清除搜索框内容
    handleClear(){
      this.timeValue = ""
      this.salePerson = ""
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
    //销售记录删除
    handleDelete(id){
      this.request.delete("/sale/deleteRecordById/"+id).then(res=>{
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
      this.request.post("/sale/deteleManySaleRecord",idList).then(res=>{
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