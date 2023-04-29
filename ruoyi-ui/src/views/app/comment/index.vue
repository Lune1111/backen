<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="评论内容" prop="fatherId">
        <el-input
          v-model="queryParams.content"
          placeholder="请输入评论内容"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['app:comment:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['app:comment:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="用户主键" align="center" prop="userId"/>
      <el-table-column label="评论内容" align="center" prop="content" :show-overflow-tooltip="true"/>
      <el-table-column label="评论类型" align="center" prop="type">
        <template slot-scope="scope">
          <span>{{scope.row.type==="1"?"评论":"回复"}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:comment:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改评论对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父主键" prop="fatherId">
          <el-input v-model="form.fatherId" placeholder="请输入父主键"/>
        </el-form-item>
        <el-form-item label="用户主键" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户主键"/>
        </el-form-item>
        <el-form-item label="评论类型" prop="type">
          <el-input v-model="form.type" placeholder="请输入评论类型"/>
        </el-form-item>
        <el-form-item label="评论内容" prop="content">
          <el-input v-model="form.content" placeholder="请输入评论内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {listComment, getComment, delComment, addComment, updateComment} from "@/api/app/comment";

  export default {
    name: "Comment",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 评论表格数据
        commentList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          fatherId: null,
          userId: null,
          content: null,
          type: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          fatherId: [
            {required: true, message: "父主键不能为空", trigger: "blur"}
          ],
          userId: [
            {required: true, message: "用户主键不能为空", trigger: "blur"}
          ],
          content: [
            {required: true, message: "评论内容不能为空", trigger: "blur"}
          ],
          type: [
            {required: true, message: "评论类型不能为空", trigger: "change"}
          ],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询评论列表 */
      getList() {
        this.loading = true;
        listComment(this.queryParams).then(response => {
          this.commentList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          fatherId: null,
          userId: null,
          content: null,
          type: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null,
          deleted: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加评论";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getComment(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改评论";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateComment(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addComment(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除评论编号为"' + ids + '"的数据项？').then(function () {
          return delComment(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
    }
  };
</script>
