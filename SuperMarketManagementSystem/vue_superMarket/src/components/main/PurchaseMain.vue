<template>
<div>
  <div style="padding: 10px 0">
    <el-input v-model="inputOrderID" style="width: 200px;" placeholder="请输入订单编号" suffix-icon="el-icon-user"></el-input>
    <el-select v-model="inputState" placeholder="请选择订单状态">
      <el-option
          v-for="item in stateOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
    <el-button @click="handleSearch" icon="el-icon-search"  type="primary" style="margin-left: 5px">搜索</el-button>
    <el-button @click="handleClear" icon="el-icon-scissors"  type="warning" style="margin-left: 5px">清空</el-button>
  </div>

  <div style="margin: 10px 0">
    <el-button type="primary" @click="openFrom">新建订单<i class="el-icon-circle-plus-outline"></i></el-button>
    <!--          批量删除确认框-->
    <el-dialog title="批量删除确认" :visible.sync="dialogTableVisible">
      <el-table :data="multipleSelection">
        <el-table-column property="id" label="采购订单ID" ></el-table-column>
        <el-table-column property="creator.userName" label="创建人"></el-table-column>
        <el-table-column label="订单状态" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.bottonClass">{{scope.row.status}}</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteManyUser();dialogTableVisible = false">确 定</el-button>
      </div>
    </el-dialog>
    <el-button type="danger" @click="dialogTableVisible = true" STYLE="margin-left: 10px">批量删除<i class="el-icon-remove-outline"></i></el-button>
  </div>

  <!-- 采购订单信息表格-->
  <el-table :data="tableData" border stripe v-loading="loading"  @selection-change="handleSelectionChange">
    <el-table-column
        type="selection"
        width="40">
    </el-table-column>
    <el-table-column prop="orderNumber" label="采购订单号"  align="center"></el-table-column>
    <el-table-column prop="creator.userName" label="创建人" align="center"></el-table-column>
    <el-table-column prop="createTime" label="创建时间" align="center" :formatter="formatDate"></el-table-column>
      <el-table-column label="订单状态" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.bottonClass">{{scope.row.status}}</el-tag>
        </template>
      </el-table-column>
    <el-table-column prop="checker.userName" label="审核人" align="center"></el-table-column>
    <el-table-column prop="warehouseman.userName" label="入库人" align="center"></el-table-column>
    <el-table-column label="操作" align="center">
      <template slot-scope="scope">
        <el-button @click="handleEdit(scope.row)" type="success" >详细信息</el-button>
        <el-button type="danger" @click="openDeleteDialog(scope.row)">删除</el-button>
        <!--删除气泡框-->
        <el-dialog style="text-align: left;"
                   title="警告"
                   :visible.sync="dialogVisible"
                   width="30%"
        >
          <span >确定要删除采购记录:<br>采购订单号：{{form.orderNumber}}</span>
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

  <!--采购订单详细信息form-->
  <el-dialog title="详细信息" :visible.sync="dialogFormVisible">
    <el-form label-width="120px">
      <el-form-item label="采购订单号" >
<!--        <el-input v-model="form.userName" autocomplete="off"></el-input>-->
        <P STYLE="margin: 0">{{form.orderNumber}}</P>
      </el-form-item>
      <el-form-item label="订单状态" >
        <el-tag :type="form.bottonClass">{{form.status}}</el-tag>
      </el-form-item>
      <el-divider content-position="center">详细信息</el-divider>
      <el-form-item label="" >
        <el-collapse v-model="activeName" accordion>
          <el-collapse-item title="创建者信息" name="1">
            <el-table
                :data="createFormData"
                style="width: 100%">
              <el-table-column
                  prop="id"
                  label="员工编号"
                  align="center"
                  width="180">
              </el-table-column>
              <el-table-column
                  prop="userName"
                  label="创建者姓名"
                  align="center"
                  width="180">
              </el-table-column>
              <el-table-column
                  prop="phone"
                  label="创建者电话">
              </el-table-column>
              <el-table-column
                  prop="createTime"
                  :formatter="formatDate"
                  label="创建时间">
              </el-table-column>
            </el-table>
          </el-collapse-item>
          <el-collapse-item title="审核人信息" name="2">
            <el-table
                :data="checkFormData"
                style="width: 100%">
              <el-table-column
                  prop="id"
                  label="员工编号"
                  align="center"
                  width="180">
              </el-table-column>
              <el-table-column
                  prop="userName"
                  label="审核人姓名"
                  align="center"
                  width="180">
              </el-table-column>
              <el-table-column
                  prop="phone"
                  label="审核人联系方式">
              </el-table-column>
              <el-table-column
                  prop="checkTime"
                  :formatter="formatDate"
                  label="审核时间">
              </el-table-column>
            </el-table>
          </el-collapse-item>
          <el-collapse-item title="入库人信息" name="3">
            <el-table
                :data="warehouseManData"
                style="width: 100%">
              <el-table-column
                  prop="id"
                  width="100"
                  label="员工编号"
                  align="center"
              >
              </el-table-column>
              <el-table-column
                  prop="userName"
                  label="入库人姓名"
                  align="center"
              >
              </el-table-column>
              <el-table-column
                  prop="phone"
                  width="100"
                  label="联系方式">
              </el-table-column>
              <el-table-column
                  prop="putTime"
                  :formatter="formatDate"
                  width="130"
                  label="入库时间">
              </el-table-column>
              <el-table-column
                  prop="warehouse.id"
                  width="100"
                  label="仓库编号">
              </el-table-column>
              <el-table-column
                  prop="warehouse.name"
                  label="仓库名称">
              </el-table-column>
              <el-table-column
                  prop="warehouse.address"
                  label="仓库地址">
              </el-table-column>
            </el-table>
          </el-collapse-item>
          <el-collapse-item title="采购货物清单" name="4">
            <el-table
                :data="GoodsFormData"
                style="width: 100%">
              <el-table-column
                  prop="id"
                  label="商品编号"
                  align="center"
              >
              </el-table-column>
              <el-table-column
                  prop="goodsName"
                  label="商品名称"
                  align="center"
              >
              </el-table-column>
              <el-table-column prop="" label="商品图片" align="center">
                <template slot-scope="scope">
                  <el-popover placement="top-start" title="" trigger="hover">
                    <img slot="reference" :src="scope.row.picture" style="width: 50px;height: 50px" alt="图片加载失败">
                    <img :src="scope.row.picture" alt="图片加载失败" style="width: 150px;height: 150px">
                  </el-popover>
                </template>
              </el-table-column>
              <el-table-column
                  prop="innerPrice"
                  align="center"
                  label="商品进价">
              </el-table-column>
              <el-table-column
                  prop="goodNum"
                  align="center"
                  label="商品数量">
              </el-table-column>
            </el-table>
          </el-collapse-item>
        </el-collapse>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" v-show="form.status === '未审核'" @click="open">审 核</el-button>
      <el-button type="primary" v-show="form.status === '未入库'" @click="pushButton(form.orderNumber)">入 库</el-button>
      <el-button @click="dialogFormVisible = false">关 闭</el-button>
    </div>

    <el-dialog title="仓库选择" :visible.sync="warehouseSelectDia"  append-to-body>
      <el-form :model="warehouseSelectForm">
        <el-form-item label="仓库" :label-width="'120px'">
          <el-select v-model="warehouseSelectForm.warehouseId" placeholder="请选择入库的仓库">
            <el-option
                v-for="item in warehouseOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="warehouseSelectDia = false">取 消</el-button>
        <el-button type="primary" @click="handleWarehousing()">确定入库</el-button>
      </div>
    </el-dialog>
  </el-dialog>

  <!--新建订单form-->
  <el-dialog title="新建订单" :visible.sync="registerPurchaseForm">
    <el-form :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="100px" class="demo-dynamic">
      <el-form-item
          prop="type"
          label="货品类型"
          :rules="[
      { required: true, message: '请输入货品名称（可模糊查询）', trigger: 'blur'},
    ]"
      >
        <div style="display: inline-block;width: 90%">
          <el-input v-model="dynamicValidateForm.type"></el-input>
        </div>
        <el-button @click="queryGoodsList(dynamicValidateForm.type)" STYLE="margin-left: 10px">查询</el-button>
      </el-form-item>
      <el-form-item
          v-for="(domain, index) in dynamicValidateForm.domains"
          :label="'采购商品' + (index+1)"
          :key="domain.purchaseOrderId"
          :prop="'domains.' + index + '.goodsId'"
          :rules="{required: true, message: '货物不能为空', trigger: 'blur'}"
      >
        <div style="display: inline-block;">
<!--          <el-input v-model="domain.value"></el-input>-->
          <el-select v-model="domain.goodsId" placeholder="请选择" style="width: 500px">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </div>
        <el-input-number v-model="domain.goodsNumber" controls-position="right" @change="handleChange" :min="1" :max="1000" STYLE="margin-left: 10px"></el-input-number>
        <el-button @click.prevent="removeDomain(domain)" STYLE="margin-left: 10px">删除</el-button>
      </el-form-item>
      <el-form-item>
<!--        <el-button type="primary" @click="submitForm('dynamicValidateForm')">提交</el-button>-->
        <el-button @click="addDomain">增加商品</el-button>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="registerPurchaseForm = false">取 消</el-button>
      <el-button type="primary" @click="submitForm('dynamicValidateForm')">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "PurchaseMain",
  data(){
    return{
      warehouseSelectDia:false,//入库时，弹出面板控制按钮
      warehouseSelectForm: {
        warehouseman:JSON.parse(window.localStorage.getItem("user")).id,//入库人id
        warehouseId: '',//入库：仓库选择器选择的值，仓库id
      },
      warehouseOptions: [//仓库选择器选项
      ],
      dynamicValidateForm: {//添加订单的form的值
        domains: [{
          goodsId: '',
          goodsNumber:1,
          purchaseOrderId:Date.now()
        }],
        type: ''
      },
      options: [],//添加订单的选项
      stateOptions: [{
        value: 0,
        label: '未审核'
      }, {
        value: 1,
        label: '未入库'
      }, {
        value: 2,
        label: '已完成'
      }],
      GoodsFormData:[],
      activeName:"",
      warehouseManData:[],
      createFormData:[],
      checkFormData:[],
      inputState:'',//搜索：输入的状态
      inputOrderID:'',//搜索：输入的订单id
      loading:false,
      dataNumber:0,
      dialogFormVisible:false,//订单详细信息表单是否打开
      dialogTableVisible:false,
      dialogVisible: false,
      registerPurchaseForm:false,//新建表单是否打开
      form:{},
      multipleSelection:[],
      pageNum:1,
      pageSize:5,
      tableData: [],
    }
  },
  methods:{
    handleWarehousing(){//处理入库
      this.request.post("/purchase/putInWarehousing",this.warehouseSelectForm).then(res=>{
        this.warehouseSelectDia = false
        if (res){
          this.renderPurchaseData()
          this.$message.success("入库成功！！")
        }else {
          this.$message.error("系统出错......")
        }
      })
      // console.log("@@@@@@@@@@@@this.warehouseSelectForm is ",this.warehouseSelectForm)
    },
    pushButton(id){//打开入库内部弹出框
      this.request.get("/warehouse/getAllWareHouse").then(res =>{
        for (let wareHouse of res){
          this.warehouseOptions.push({
            value: wareHouse.id,
            label: wareHouse.name,
          });
        }
        this.$set(this.warehouseSelectForm,"orderNumber",id)
        console.log("getAllWareHouse warehouseOptions is ",this.warehouseOptions)
      })
      this.warehouseSelectDia = true
    },
    open() {//审核订单
      let auditRequestParams = {
        orderNumber:this.form.orderNumber,
        checkerId:JSON.parse(window.localStorage.getItem("user")).id
      }
      this.$confirm('是否通过审核?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log("auditRequestParams is ",auditRequestParams)
        this.request.post("/purchase/approveOrder",auditRequestParams).then(res=>{
          if (res){
            this.renderPurchaseData()
            this.dialogFormVisible = false
            this.$message({
              type: 'success',
              message: '确定通过审核!'
            });
          }else {
            this.$message.error("操作出错......")
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '审核已取消'
        });
      });
    },
    //订单创建form：计数器数字改变时
    handleChange(value) {
      console.log(value);
    },
    //向后台请求数据，绑定到前端
    renderPurchaseData(){
      this.request.get("/purchase/getAllPurchas",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          inputState:this.inputState,//搜索：输入的状态
          inputOrderID:this.inputOrderID
        }}).then(res =>{
        // console.log("renderPurchaseData的数据为",res)
        this.tableData = res
        this.dataNumber = res.length
        for (let purchase of this.tableData){
          for (let i=0; i<purchase.goodsList.length;i++){
            this.$set(purchase.goodsList[i],"goodNum",purchase.goodsNumList[i])
          }
        }
        // console.log("tableData = ",this.tableData)
        // console.log("数据数量 ",this.dataNumber)
      })
    },
    //处理容量变化，换页
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.renderPurchaseData()
    },
    //处理换页
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.renderPurchaseData()
    },
    //搜索处理
    handleSearch(){
      console.log("搜索的inputState is ",this.inputState)
      console.log("搜索的inputOrderID is ",this.inputOrderID)
      this.loading = true
      this.renderPurchaseData()
      this.loading = false
    },
    //时间格式处理函数
    formatDate(row, column) {
      // 获取单元格数据
      let data = row[column.property];
      if(data === null) {
        return null;
      }
      let dt = new Date(data)
      return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
    },
    //清除搜索框内容
    handleClear(){
      this.inputState = ""
      this.inputOrderID = ""
      this.renderPurchaseData()
    },
    //打开员工信息form
    openFrom(){
      this.registerPurchaseForm = true
      this.form = {}
    },
    //详细采购订单保存
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
    //采购订单信息删除
    handleDelete(id){
      this.request.delete("/purchase/detelePurchaseOrder/"+id).then(res=>{
        if (res){
          this.$message.success("删除成功!!!")
          this.renderPurchaseData()
        }else {
          this.$message.error("删除失败...")
        }
      })
    },
    //订单信息详情
    handleEdit(row){
      console.log("row is ",row)
      this.form = row
      this.createFormData[0] = row.creator
      this.createFormData[0].createTime = row.createTime
      if (row.checker === null){
        this.checkFormData[0] = {}
      }else {
        this.checkFormData[0] = row.checker
      }
      this.checkFormData[0].checkTime = row.checkTime
      if (row.warehouseman === null){
        this.warehouseManData[0] = {}
      }else {
        this.warehouseManData[0] =row.warehouseman
      }
      this.warehouseManData[0].putTime = row.putTime
      this.warehouseManData[0].warehouse = row.warehouse
      this.GoodsFormData = row.goodsList
      this.dialogFormVisible = true
    },
    //打开删除气泡框
    openDeleteDialog(row){
      this.form = row
      console.log("openDeleteDialog的form is",this.form)
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
          this.renderPurchaseData()
          this.$message.success("批量删除成功！！！")
        }else {
          this.$message.error("删除失败...")
        }
      })
    },
    //创建订单提交
    submitForm(formName) {
      // console.log(this.dynamicValidateForm)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let twoPurchaseOrderId = Date.now()
          for (let good of this.dynamicValidateForm.domains){
            good.purchaseOrderId = twoPurchaseOrderId
          }
          this.dynamicValidateForm.createrId = JSON.parse(window.localStorage.getItem("user")).id
          this.request.post("/purchase/insertPurchaseOrder",this.dynamicValidateForm).then(res=>{
            this.$refs[formName].resetFields()
            this.registerPurchaseForm = false
            if (res){
              this.renderPurchaseData()
              this.$message.success("操作成功！！")
            }else {
              this.$message.error("系统出错......")
            }
          })
        } else {
          this.$message.error("提交数据有误")
          return false;
        }
      });
    },
    //商品删除方法
    removeDomain(item) {
      var index = this.dynamicValidateForm.domains.indexOf(item)
      if (index !== -1) {
        this.dynamicValidateForm.domains.splice(index, 1)
      }
    },
    //商品添加方法
    addDomain() {
      this.dynamicValidateForm.domains.push({
        goodsId: '',
        goodsNumber:1,
        purchaseOrderId: Date.now()
      });
    },
    //查询新建form中的货物选择
    queryGoodsList(type){
      console.log("queryGoodsList方法中的type",type)
      this.request.get("/goods/getGoodsByQueryGoodsName",{
        params:{
          queryName:type,
        }
      }).then(res =>{
        for (let good of res){
          this.options.push({
            value: good.id,
            label: good.goodsName,
          });
        }
        console.log("options",this.options)
      })
    }
  },
  created() {
    this.renderPurchaseData()
  }
}
</script>

<style scoped>
.el-table::before {
  height: 0;
}
.el-table--border::after {
  width: 0;
}
.el-table--border {
  border: none;
}
</style>