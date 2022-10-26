<template>
  <div>
    <div style="padding: 10px 0">
      <el-input v-model="inputName" style="width: 200px;" placeholder="请输入供货商名称" suffix-icon="el-icon-user"></el-input>
        <el-cascader
            placeholder="请选择供货商品类别（可搜索）"
            :options="options"
            v-model="valueOfCascader"
            filterable
            @change="handleCountryChange"
            change-on-select
            STYLE="margin-left: 5px;width: 200px"
        ></el-cascader>
      <el-button @click="handleSearch" icon="el-icon-search"  type="primary" style="margin-left: 5px">搜索</el-button>
      <el-button @click="handleClear" icon="el-icon-scissors"  type="warning" style="margin-left: 5px">清空</el-button>
      <div STYLE="height: 28px;width: 200px;display: inline-block;text-align: right;margin-left: 685px">
        <h1 style="height: 28px;line-height: 28px;color: gold">{{this.$route.meta.title}}</h1>
      </div>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="openFrom">新建供货商<i class="el-icon-circle-plus-outline"></i></el-button>
      <!-- 批量删除确认框-->
      <el-dialog title="批量删除确认" :visible.sync="dialogTableVisible">
        <el-table :data="multipleSelection">
          <el-table-column property="id" label="供货商ID" width="150"></el-table-column>
          <el-table-column property="name" label="供货商名称" width="200"></el-table-column>
          <el-table-column property="createTime" label="加入时间"></el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible = false">取 消</el-button>
          <el-button type="primary" @click="deleteManyUser();dialogTableVisible = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-button type="danger" @click="dialogTableVisible = true" STYLE="margin-left: 10px">批量移除<i class="el-icon-remove-outline"></i></el-button>
    </div>

    <!--供应商信息表格-->
    <el-table :data="tableData" border stripe v-loading="loading"  @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="40">
      </el-table-column>
      <el-table-column prop="id" label="供应商ID" width="140" align="center"></el-table-column>
      <el-table-column prop="name" label="供应商名称" width="200" align="center"></el-table-column>
      <el-table-column prop="manager" label="负责人" width="120" align="center"></el-table-column>
      <el-table-column prop="phone" label="电话" align="center"></el-table-column>
      <el-table-column prop="address" label="供应商地址" align="center" width="100"></el-table-column>
      <el-table-column prop="createTime" label="建档时间" :formatter="formatDate" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="success">编辑</el-button>
          <el-button type="danger" @click="supplierDelete(scope.row)">删除</el-button>
          <!--              删除气泡框-->
          <el-dialog style="text-align: left;"
                     title="警告"
                     :visible.sync="supplieDetDia"
                     width="30%"
                     append-to-body>
            <span >确定要删除供应商:{{form.id}}  {{form.name}}</span>
            <span slot="footer" class="dialog-footer">
                  <el-button @click="supplieDetDia = false">取 消</el-button>
                  <el-button type="primary" @click="handlerSupplierDelete(form.id);supplieDetDia = false">确 定</el-button>
            </span>
          </el-dialog>
        </template>
      </el-table-column>
    </el-table>

<!--    分页部分-->
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
    <!-- 供应商：编辑表单-->
    <el-dialog title="供应商信息" :visible.sync="dialogFormVisible">
      <el-form label-width="120px">
        <el-form-item label="供应商名称" >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="form.manager" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="供应商地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-show="isShowAddBut">
          <el-button type="primary" @click="openGoodsFromInTable(form.id)">添加商品<i class="el-icon-circle-plus-outline"></i></el-button>
        </el-form-item>
        <el-form-item v-show="isShowGoodList" label="所提供的商品" >
          <el-table
              :data="formTableData"
              style="width: 100%">
            <el-table-column
                prop="goodsName"
                label="商品名称"
                width="100">
            </el-table-column>
            <el-table-column
                prop="innerPrice"
                label="价格"
                width="50">
            </el-table-column>
            <el-table-column label="商品图片" width="150" align="center">
              <!--        <template slot-scope="scope">-->
              <template slot-scope="scope">
                <el-popover placement="top-start" title="" trigger="hover">
                  <img slot="reference" :src="scope.row.picture" style="width: 50px;height: 50px" alt="图片加载失败">
                  <img :src="scope.row.picture" alt="图片加载失败" style="width: 150px;height: 150px">
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column
                prop="address"
                width="150"
                label="商品种类">
              <template slot-scope="scope">
                <el-tag type="success">{{scope.row.firstClass}}/{{scope.row.secondClass}}/{{scope.row.thirdClass}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column
                width="250"
                label="操作">
              <template slot-scope="scope">
                <el-button @click="handleEditGoods(scope.row)" type="success" STYLE="display: inline-block">编辑</el-button>
                <el-button type="danger" @click="openDeleteDialog(scope.row)" STYLE="display: inline-block">删除</el-button>
                <!--              删除气泡框-->
                <el-dialog style="text-align: left;"
                           title="警告"
                           :visible.sync="goodDeteleDialogVisible"
                           width="30%"
                >
                  <span >确定要删除商品:{{deleteGoodsForm.id}}  {{deleteGoodsForm.goodsName}}</span>
                  <span slot="footer" class="dialog-footer">
                  <el-button @click="goodDeteleDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="handleDelete(deleteGoodsForm.id);goodDeteleDialogVisible = false">确 定</el-button>
                </span>
                </el-dialog>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveSupplier()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 供货商弹出框中的货品修改添加表单-->
    <el-dialog title="商品信息" :visible.sync="formInSupplierTable">
      <el-form label-width="120px">
        <el-form-item label="商品图片" >
          <el-upload
              class="upload-demo"
              action="http://localhost:9090/files/upload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :on-success="handleUploadSuccess"
              :file-list="fileList"
              list-type="picture">
            <el-button size="small" type="primary">上传新图片</el-button>
            <div slot="tip" class="el-upload__tip" style="display: inline-block;margin-left: 10px">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品名称" >
          <el-input v-model="goodsFormList.goodsName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品售价" >
          <el-input v-model="goodsFormList.innerPrice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="供应商ID">
          <el-input v-model="goodsFormList.supperId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品介绍" >
          <el-input v-model="goodsFormList.information" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品类别" >
          <el-cascader
              placeholder="请选择商品类别（可搜索）"
              :options="options"
              v-model="goodsOfCascader"
              filterable
              @change="handleCountryChange"
              change-on-select
              STYLE="width: 696px"
          ></el-cascader>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formInSupplierTable = false">取 消</el-button>
        <el-button type="primary" @click="saveRegister()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "SupplierMain",
  data(){
    return{
      isShowGoodList:true,//是否显示供货商新增form中的商品面板
      isShowAddBut:true,//是否显示供货商新增form中的按钮
      supplieDetDia:false,//删除供应商确认框：开关
      goodDeteleDialogVisible:false,//删除商品气泡框：开关
      fileList: [],
      goodsFormList:[],
      inputClass:'',
      inputName:'',
      loading:false,
      dataNumber:0,
      formInSupplierTable:false,//供货商表单中的货品详细信息表单
      dialogFormVisible:false,//供货商详细表单是否打开
      dialogTableVisible:false,
      dialogVisible: false,
      form:{},
      deleteGoodsForm:{},//删除货物的信息
      multipleSelection:[],
      pageNum:1,
      pageSize:5,
      tableData: [],
      formTableData: [],
      valueOfCascader:[],
      goodsOfCascader:[],
      // formOfCascader:[],
      options: this.$store.state.options,
    }
  },
  methods:{
    //向后台请求数据，绑定到前端
    renderData(){
      this.request.get("/supplier/getPartSupplier",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.inputName,
          address:this.inputAddress
        }}).then(res =>{
        console.log("supplier中的records为",res.records)
        this.tableData = res.records
        // console.log("输出supplier的router",this.$route)
        this.dataNumber = res.total
      })
    },
    handleClick(row) {
      console.log(row);
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
      console.log("选中的值为    ",this.valueOfCascader)
      console.log("选择表的数据为    ",this.optionValue)
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
      this.valueOfCascader = []
      this.inputName = ""
      this.renderData()
    },
    //打开供应商信息form
    openFrom(){
      this.isShowAddBut = false
      this.isShowGoodList = false
      this.form = {}
      this.dialogFormVisible = true
    },
    //注册货品保存
    saveRegister(){
      this.goodsFormList.firstClass = this.goodsOfCascader[0]
      this.goodsFormList.secondClass = this.goodsOfCascader[1]
      this.goodsFormList.thirdClass = this.goodsOfCascader[2]
      this.request.post("/goods/saveOrInsertGoods",this.goodsFormList).then(res=>{
        this.formInSupplierTable = false
        if (res){
          this.renderData()
          this.$message.success("操作成功！！")
        }else {
          this.$message.error("操作出错......")
        }
      })
    },
    //注册供货商
    saveSupplier(){
      console.log("注册供应商方法中的form是",this.form)
      this.request.post("/supplier/insterOrUpdataSupplier",this.form).then(res=>{
        if (res){
          this.renderData()
          this.dialogFormVisible = false
          this.$message.success("操作成功！！")
        }else {
          this.$message.error("操作出错......")
        }
      })
    },
    //商品信息删除
    handleDelete(id){
      this.request.delete("/goods/deleteGoods/"+id).then(res=>{
        if (res){
          this.dialogFormVisible = false
          this.$message.success("删除成功!!!")
          this.renderData()
        }else {
          this.$message.error("删除失败...")
        }
      })
    },
    //供应商信息删除
    handlerSupplierDelete(id){
      this.request.delete("/supplier/deleteSupplier/"+id).then(res=>{
        if (res){
          this.$message.success("删除成功!!!")
          this.renderData()
        }else {
          this.$message.error("删除失败...")
        }
      })
    },
    //供应商信息修改
    handleEdit(row){
      console.log("供货商编辑列表中的数据是",row)
      this.isShowGoodList = true
      this.isShowAddBut = true
      this.form = row
      // this.goodsOfCascader = [row.firstClass,row.secondClass,row.thirdClass]
      this.formTableData = row.listOfGoods
      // this.fileList[0] = {name:"当前图片为",url:row.picture}
      this.dialogFormVisible = true
    },
    //打开删除商品气泡框
    openDeleteDialog(row){
      this.deleteGoodsForm = row
      console.log("openDeleteDialog方法中的 row is",this.deleteGoodsForm)
      this.goodDeteleDialogVisible = true
    },
    //打开删除供应商气泡框
    supplierDelete(row){
      this.form = row
      this.supplieDetDia = true
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
      this.request.post("/supplier/deleteManySupplier",idList).then(res=>{
        if (res){
          this.renderData()
          this.$message.success("批量撤销成功！！！")
        }else {
          this.$message.error("撤销失败...")
        }
      })
    },
    //处理联机选择框改变
    handleCountryChange(){
      console.log("机选择框改变了")
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
      this.goodsFormList.picture = res
      console.log("修改图片之后的form",this.goodsFormList.picture)
    },
    //新建供应商的商品
    openGoodsFromInTable(id){
      this.goodsFormList = {supperId:id}
      this.formTableData =[]
      this.fileList = []
      console.log("打开商品表单")
      this.formInSupplierTable = true;
    },
    //供货商详细信息中商品表格的修改商品信息处理
    handleEditGoods(row){
      console.log("handleEditGoods中的数据是",row)
      this.goodsFormList = row
      this.goodsOfCascader = [row.firstClass,row.secondClass,row.thirdClass]
      // this.formTableData = row.listOfGoods
      this.fileList[0] = {name:"当前图片为",url:row.picture}
      this.formInSupplierTable = true
    },
  },
  created() {
    this.renderData()
  }
}
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
/*滚动条样式*/
.supplierTable::-webkit-scrollbar {
  width: 0;
}
.el-main::-webkit-scrollbar {
  width: 0;
}
.supplierTable::-webkit-scrollbar-thumb {
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
  background: rgba(0,0,0,0.2);
}
.supplierTable::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
  border-radius: 0;
  background: rgba(0,0,0,0.1);
}
</style>