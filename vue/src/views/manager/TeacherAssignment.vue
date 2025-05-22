<template>
    <div class="teacher-assignment">
      <el-card>
        <!-- 筛选区 -->
        <div class="filter-bar">
          <el-form :inline="true" :model="filterForm" size="small">
            <el-form-item label="学生姓名">
              <el-input v-model="filterForm.studentName" placeholder="请输入学生姓名" clearable style="width: 140px" />
            </el-form-item>
            <el-form-item label="学号">
              <el-input v-model="filterForm.studentId" placeholder="请输入学号" clearable style="width: 140px" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSearch" icon="Search">搜索</el-button>
              <el-button @click="resetFilter">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
  
        <!-- 分配列表 -->
        <el-table
          :data="studentList"
          style="width: 100%; margin-top: 18px"
          border
          empty-text="暂无数据"
          header-cell-class-name="table-header"
        >
          <el-table-column prop="name" label="学生姓名" min-width="100" />
          <el-table-column prop="code" label="学号" min-width="120" />
          <el-table-column prop="assignedTeacher" label="指导教师" min-width="120">
            <template #default="scope">
              <span>{{ scope.row.assignedTeacher || '未分配' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" min-width="120" align="center">
            <template #default="scope">
              <el-button 
                size="small" 
                type="primary" 
                @click="handleAssign(scope.row)"
              >
                {{ scope.row.assignedTeacher ? '修改' : '分配' }}
              </el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <!-- 分页 -->
        <div class="pagination-container">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[10, 20, 50, 100]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-card>
  
      <!-- 分配教师对话框 -->
      <el-dialog
        v-model="assignDialogVisible"
        :title="currentStudent?.assignedTeacher ? '修改指导教师' : '分配指导教师'"
        width="400px"
        :close-on-click-modal="false"
      >
        <el-form :model="assignForm" label-width="100px">
          <el-form-item label="选择教师" required>
            <el-select
              v-model="assignForm.teacherId"
              placeholder="请选择教师"
              style="width: 100%"
              filterable
            >
              <el-option
                v-for="teacher in teacherList"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="assignDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitAssign">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { ElMessage } from 'element-plus'
  import axios from 'axios'
  
  // 数据定义
  const studentList = ref([])
  const teacherList = ref([])
  const currentPage = ref(1)
  const pageSize = ref(10)
  const total = ref(0)
  const assignDialogVisible = ref(false)
  const currentStudent = ref(null)
  
  const filterForm = ref({
    studentName: '',
    studentId: ''
  })
  
  const assignForm = ref({
    teacherId: ''
  })
  
  // 方法定义
  const handleSearch = async () => {
  try {
    const response = await axios.get('/api/students/assignments', {
      params: {
        page: currentPage.value,
        size: pageSize.value,
        studentName: filterForm.value.studentName,
        studentId: filterForm.value.studentId
      }
    })
    console.log('查询结果:', response.data)  // 添加日志
    if (response.data.code === 200) {
      studentList.value = response.data.data.list
      total.value = response.data.data.total
    } else {
      ElMessage.error(response.data.message || '查询失败')
    }
  } catch (error) {
    console.error('查询错误:', error)
    ElMessage.error('获取学生列表失败')
  }
}
  
  const resetFilter = () => {
    filterForm.value = {
      studentName: '',
      studentId: ''
    }
    handleSearch()
  }
  
  const handleSizeChange = (val) => {
    pageSize.value = val
    handleSearch()
  }
  
  const handleCurrentChange = (val) => {
    currentPage.value = val
    handleSearch()
  }
  
  const loadTeachers = async () => {
    try {
      const response = await axios.get('/api/teachers/list')
      teacherList.value = response.data.data
    } catch (error) {
      ElMessage.error('获取教师列表失败')
    }
  }
  
  const handleAssign = (row) => {
    currentStudent.value = row
    assignForm.value = {
      teacherId: row.assignedTeacherId || ''
    }
    assignDialogVisible.value = true
  }
  
  const submitAssign = async () => {
    if (!assignForm.value.teacherId) {
      ElMessage.warning('请选择教师')
      return
    }
  
    try {
      await axios.post('/api/students/assign_teacher', {
        studentId: currentStudent.value.id,
        teacherId: assignForm.value.teacherId
      })
      ElMessage.success('分配成功')
      assignDialogVisible.value = false
     await handleSearch()
    } catch (error) {
      console.error('分配失败:', error)
      ElMessage.error('分配失败:')
    }
  }
  
  // 生命周期钩子
  onMounted(() => {
    handleSearch()
    loadTeachers()
  })
  </script>
  
  <style scoped>
  .teacher-assignment {
    padding: 24px 10px 10px 10px;
    background: #f5f7fa;
    min-height: 100vh;
  }
  .filter-bar {
    margin-bottom: 10px;
  }
  .table-header {
    background: #f4f8fb;
    color: #333;
    font-weight: bold;
    font-size: 15px;
  }
  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
  </style>