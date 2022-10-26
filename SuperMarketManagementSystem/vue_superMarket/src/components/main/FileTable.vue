<template>
  <div>
    <el-button type="primary" @click="">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    <el-table :data="tableData" border stripe v-loading="loading"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="id" label="文件ID" width="80" align="center"></el-table-column>
<!--      <el-table-column prop="goodsName" label="商品图片" width="200" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <el-popover placement="top-start" title="" trigger="hover">-->
<!--            <img :src="require('../../assets/goods/'+scope.row.picture)" alt="图片加载失败" style="width: 150px;height: 150px">-->
<!--            <img slot="reference" :src="require('../../assets/goods/'+scope.row.picture)" style="width: 50px;height: 50px" alt="图片加载失败">-->
<!--          </el-popover>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="name" label="文件名称" width="200" align="center"></el-table-column>
      <el-table-column prop="type" label="文件类型" width="120" align="center"></el-table-column>
      <el-table-column prop="size" label="文件大小（kb）" width="120" align="center"></el-table-column>
      <el-table-column label="下载" align="center"></el-table-column>
      <el-table-column prop="thirdClass" label="启用" align="center" width="100"></el-table-column>
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
  </div>
</template>

<script>
export default {
  name: "FileTable",
  data(){
    return{
      tableData:[],
      loading:false,
      multipleSelection:[],
    }
  },
  methods:{
    load(){
      this.request.get("",{

      })
    },
    handleSelectionChange(val){
      console.log("多选之后的val",val)
      this.multipleSelection = val
      console.log("多选之后的multipleSelection",this.multipleSelection)
    },
  }
}
</script>

<style scoped>

</style>