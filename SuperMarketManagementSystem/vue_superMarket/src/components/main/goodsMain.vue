<template>
  <div>
    <div style="padding: 10px 0">
      <el-input v-model="inputName" style="width: 200px;" placeholder="请输入商品名称" suffix-icon="el-icon-user"></el-input>
        <el-cascader
            placeholder="请选择商品类别（可搜索）"
            :options="options"
            v-model="valueOfCascader"
            filterable
            @change="handleCountryChange"
            change-on-select
            STYLE="margin-left: 5px;width: 200px"
        ></el-cascader>
      <el-button @click="handleSearch" icon="el-icon-search"  type="primary" style="margin-left: 5px">搜索</el-button>
      <el-button @click="handleClear" icon="el-icon-scissors"  type="warning" style="margin-left: 5px">清空</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="openFrom">新增商品<i class="el-icon-circle-plus-outline"></i></el-button>
      <!--          批量删除确认框-->
      <el-dialog title="批量删除确认" :visible.sync="dialogTableVisible">
        <el-table :data="multipleSelection">
          <el-table-column property="id" label="商品ID" width="150"></el-table-column>
          <el-table-column property="goodsName" label="商品名称" width="200"></el-table-column>
          <el-table-column property="createTime" label="创建时间"></el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible = false">取 消</el-button>
          <el-button type="primary" @click="deleteManyUser();dialogTableVisible = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-button type="danger" @click="dialogTableVisible = true" STYLE="margin-left: 10px">批量删除<i class="el-icon-remove-outline"></i></el-button>
<!--      <el-button type="primary">导入<i class="el-icon-bottom"></i></el-button>-->
<!--      <el-button type="primary">导出<i class="el-icon-top"></i></el-button>-->
    </div>

    <!-- 商品信息表格-->
    <el-table :data="tableData" border stripe v-loading="loading"  @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="40">
      </el-table-column>
      <el-table-column prop="id" label="商品ID" width="140" align="center"></el-table-column>
      <el-table-column prop="goodsName" label="商品图片" width="200" align="center">
<!--        <template slot-scope="scope">-->
          <template slot-scope="scope">
          <el-popover placement="top-start" title="" trigger="hover">
            <img slot="reference" :src="scope.row.picture" style="width: 50px;height: 50px" alt="图片加载失败">
            <img :src="scope.row.picture" alt="图片加载失败" style="width: 150px;height: 150px">
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="goodsName" label="商品名称" width="200" align="center"></el-table-column>
      <el-table-column prop="innerPrice" label="进货价" width="120" align="center"></el-table-column>
      <el-table-column prop="salePrice" label="售价" width="120" align="center"></el-table-column>
      <el-table-column prop="supperId" label="供应商编号" align="center"></el-table-column>
      <el-table-column prop="thirdClass" label="所属类别" align="center" width="100"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" :formatter="formatDate" align="center"></el-table-column>
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
            <span >确定要删除商品:{{form.id}}  {{form.goodsName}}</span>
            <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="handleDelete(form.id);dialogVisible = false">确 定</el-button>
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
    <!-- 编辑表单-->
    <el-dialog title="商品信息" :visible.sync="dialogFormVisible">
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
          <el-input v-model="form.goodsName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品进价" >
          <el-input v-model="form.innerPrice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="售价">
          <el-input v-model="form.salePrice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品预警数量">
          <el-input v-model="form.alterNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="供应商ID">
          <el-input v-model="form.supperId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品介绍" >
          <el-input v-model="form.information" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品类别" >
          <el-cascader
              placeholder="请选择商品类别（可搜索）"
              :options="options"
              v-model="formOfCascader"
              filterable
              @change="handleCountryChange"
              change-on-select
              STYLE="width: 696px"
          ></el-cascader>
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
  name: "goodsMain",
  data(){
    return{
      fileList: [],
      inputClass:'',
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
      valueOfCascader:[],
      formOfCascader:[],
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
      }, {//第二大类
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
        }],
    }
  },
  methods:{
    //向后台请求数据，绑定到前端
    renderData(){
      this.request.get("/goods/getPartGoods",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          goodsName:this.inputName,
          firstClass:this.inputAddress
        }}).then(res =>{
        // console.log("Goods中的res.records为",res.records)
        this.tableData = res.records
        for (let good of this.tableData){
          if (good.picture===null){
            good.picture = "http://localhost:9090/files/goods/null.png"
          }
        }
        // console.log("修改之后的res.records为",res.records)
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
    //打开员工信息form
    openFrom(){
      this.dialogFormVisible = true
      this.form = {}
    },
    //注册人员保存
    saveRegister(){
      this.form.firstClass = this.formOfCascader[0]
      this.form.secondClass = this.formOfCascader[1]
      this.form.thirdClass = this.formOfCascader[2]
      console.log("goods中的saveRegister",this.form)
      this.request.post("/goods/saveOrInsertGoods",this.form).then(res=>{
        this.dialogFormVisible = false
        if (res){
          this.renderData()
          this.$message.success("操作成功！！")
        }else {
          this.$message.error("操作出错......")
        }
      })
    },
    //员工信息删除
    handleDelete(id){
      this.request.delete("/goods/deleteGoods/"+id).then(res=>{
        if (res){
          this.$message.success("删除成功!!!")
          this.renderData()
        }else {
          this.$message.error("删除失败...")
        }
      })
    },
    //商品信息修改
    handleEdit(row){
      console.log("商品编辑列表中的数据是",row)
      this.form = row
      this.formOfCascader = [row.firstClass,row.secondClass,row.thirdClass]
      this.fileList[0] = {name:"当前图片为",url:row.picture}
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
      this.request.post("/goods/deleteManyGoods",idList).then(res=>{
        if (res){
          this.renderData()
          this.$message.success("批量删除成功！！！")
        }else {
          this.$message.error("删除失败...")
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
      this.form.picture = res
      console.log("修改图片之后的form",this.form.picture)
    }
  },
  created() {
    this.renderData()
  }
}
</script>

<style scoped>

</style>