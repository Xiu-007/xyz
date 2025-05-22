<template>
  <div class="document-management">
    <el-card>
      <!-- 上传按钮 -->
      <el-button 
        type="primary" 
        @click="uploadDialogVisible = true" 
        style="margin-bottom: 15px;"
      >
        <el-icon><UploadFilled /></el-icon>
        上传文档
      </el-button>

      <!-- 筛选区 -->
      <div class="filter-bar">
        <el-form :inline="true" :model="filterForm" size="small">
          <el-form-item label="文档类型">
            <el-select v-model="filterForm.type" placeholder="全部" clearable style="width: 140px">
              <el-option
                v-for="item in documentTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="审核状态">
            <el-select v-model="filterForm.status" placeholder="全部" clearable style="width: 120px">
              <el-option
                v-for="item in auditStatus"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch" icon="Search">搜索</el-button>
            <el-button @click="resetFilter">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 文档列表 -->
      <el-table
        :data="documentList"
        style="width: 100%; margin-top: 18px"
        border
        empty-text="暂无数据"
        header-cell-class-name="table-header"
      >
        <el-table-column prop="name" label="文档名称" min-width="120" />
        <el-table-column prop="type" label="文档类型" min-width="110">
          <template #default="scope">
            {{ getTypeLabel(scope.row.type) }}
          </template>
        </el-table-column>
        <el-table-column prop="uploadTime" label="上传时间" min-width="150" />
        <el-table-column prop="status" label="审核状态" min-width="90" align="center">
          <template #default="scope">
            <el-tag :type="getStatusTagType(scope.row.status)">
              {{ getStatusLabel(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="teacherOpinion" label="教师审批意见" min-width="150">
          <template #default="scope">
            <el-tooltip
              v-if="scope.row.teacherOpinion"
              :content="scope.row.teacherOpinion"
              placement="top"
              :show-after="500"
            >
              <span>{{ scope.row.teacherOpinion.length > 20 ? scope.row.teacherOpinion.substring(0, 20) + '...' : scope.row.teacherOpinion }}</span>
            </el-tooltip>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="deanOpinion" label="院长审批意见" min-width="150">
          <template #default="scope">
            <el-tooltip
              v-if="scope.row.deanOpinion"
              :content="scope.row.deanOpinion"
              placement="top"
              :show-after="500"
            >
              <span>{{ scope.row.deanOpinion.length > 20 ? scope.row.deanOpinion.substring(0, 20) + '...' : scope.row.deanOpinion }}</span>
            </el-tooltip>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="180" align="center">
          <template #default="scope">
            <el-button 
              size="small" 
              type="primary" 
              @click="handleEdit(scope.row)"
              :disabled="scope.row.status !== 'PENDING' && scope.row.status !== 'REJECTED' && scope.row.status  === 'APPROVED'"
            >
              修改
            </el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="handleDelete(scope.row)"
              :disabled="scope.row.status !== 'PENDING' && scope.row.status !== 'REJECTED'"
            >
              删除
            </el-button>
            <el-button size="small" @click="handlePreview(scope.row)">预览</el-button>
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

    <!-- 上传对话框 -->
    <el-dialog 
      v-model="uploadDialogVisible" 
      title="上传文档" 
      width="420px" 
      :close-on-click-modal="false"
    >
      <el-form :model="uploadForm" label-width="80px">
        <el-form-item label="文档类型" required>
          <el-select v-model="uploadForm.type" placeholder="请选择文档类型" style="width: 100%">
            <el-option
              v-for="item in documentTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选择文件" required>
          <el-upload
            class="upload-demo"
            drag
            multiple
            :action="uploadUrl"
            :data="{ 
              type: uploadForm.type, 
              userId: userId 
            }"
            :on-success="handleUploadSuccess"
            :on-error="handleUploadError"
            :before-upload="beforeUpload"
            :show-file-list="true"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">支持批量上传，单个文件最大50MB</div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog 
      v-model="editDialogVisible" 
      title="修改文档" 
      width="420px" 
      :close-on-click-modal="false"
    >
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="文档类型" required>
          <el-select v-model="editForm.type" placeholder="请选择文档类型" style="width: 100%">
            <el-option
              v-for="item in documentTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选择文件" required>
          <el-upload
            class="upload-demo"
            drag
            :action="uploadUrl"
            :data="{ 
              type: editForm.type, 
              userId: userId,
              documentId: currentDocument?.id
            }"
            :on-success="handleEditSuccess"
            :on-error="handleEditError"
            :before-upload="beforeUpload"
            :show-file-list="true"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 预览对话框 -->
    <el-dialog
      v-model="previewDialogVisible"
      title="文档预览"
      width="800px"
      :close-on-click-modal="false"
    >
      <div class="preview-container">
        <div class="preview-toolbar">
          <el-button type="success" @click="downloadDocument">
            <el-icon><Download /></el-icon> 下载文档
          </el-button>
        </div>
        <div class="preview-content" v-loading="previewLoading">
          <!-- PDF预览 -->
          <iframe
            v-if="previewUrl && currentDocument?.type !== 'video'"
            :src="previewUrl"
            class="preview-iframe"
          ></iframe>
          <!-- 视频预览 -->
          <video
            v-else-if="previewUrl && currentDocument?.type === 'video'"
            :src="previewUrl"
            controls
            class="preview-video"
          ></video>
          <div v-else class="preview-placeholder">
            <el-icon><Document /></el-icon>
            <span>加载预览中...</span>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UploadFilled, Download, Document } from '@element-plus/icons-vue'
import axios from 'axios'

// 正确获取 userId
const userStr = localStorage.getItem('system-user')
const userId = userStr ? JSON.parse(userStr).id : null

// 数据定义
const documentList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const uploadDialogVisible = ref(false)
const editDialogVisible = ref(false)
const previewDialogVisible = ref(false)
const previewUrl = ref('')
const previewLoading = ref(false)
const currentDocument = ref(null)

const filterForm = ref({
  type: '',
  status: ''
})

const uploadForm = ref({
  type: ''
})

const editForm = ref({
  type: ''
})

const uploadUrl = '/api/documents/upload'

// 文档类型选项
const documentTypes = [
  { value: 'thesis', label: '论文正文' },
  { value: 'task', label: '任务书' },
  { value: 'proposal', label: '开题报告' },
  { value: 'grade1', label: '成绩评定表（一）' },
  { value: 'grade2', label: '成绩评定表（二）' },
  { value: 'grade3', label: '成绩评定表（三）' },
  { value: 'plagiarism', label: '查重报告' },
  { value: 'source', label: '源码' },
  { value: 'video', label: '项目视频' },
  { value: 'deploy', label: '项目部署要求文件' }
]

// 审核状态选项
const auditStatus = [
  { value: 'PENDING', label: '待审核' },
  { value: 'TEACHER_REJECTED', label: '教师审核未通过' },
  { value: 'DEAN_PENDING', label: '院长审核中' },
  { value: 'TEACHER_PENDING', label: '教师重审中' },
  { value: 'APPROVED', label: '审核通过' }
]

// 工具方法
const getTypeLabel = (type) => {
  const found = documentTypes.find(t => t.value === type)
  return found ? found.label : type
}

const getStatusLabel = (status) => {
  const found = auditStatus.find(s => s.value === status)
  return found ? found.label : status
}

const getStatusTagType = (status) => {
  switch (status) {
    case 'PENDING': return 'warning'
    case 'TEACHER_REJECTED': return 'danger'
    case 'DEAN_PENDING': return 'info'
    case 'TEACHER_PENDING': return 'warning'
    case 'APPROVED': return 'success'
    default: return ''
  }
}

// 方法定义
const handleSearch = async () => {
  try {
    const response = await axios.get('/api/documents/list', {
      params: {
        page: currentPage.value,
        size: pageSize.value,
        userId: userId,
        type: filterForm.value.type ||null,
        status: filterForm.value.status ||null,
        ...filterForm.value
      }
    })
    documentList.value = response.data.data.list
    total.value = response.data.data.total
  } catch (error) {
    ElMessage.error('获取文档列表失败')
  }
}

const resetFilter = () => {
  filterForm.value = {
    type: '',
    status: ''
  }
  currentPage.value = 1
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

const handleUploadSuccess = (response) => {
  ElMessage.success('上传成功')
  uploadDialogVisible.value = false
  handleSearch()
}

const handleUploadError = () => {
  ElMessage.error('上传失败')
}

const beforeUpload = (file) => {
  if (!uploadForm.value.type) {
    ElMessage.error('请先选择文档类型')
    return false
  }
  const isTypeExist = documentList.value.some(
      doc => doc.type === uploadForm.value.type
  )
  if (isTypeExist) {
    ElMessage.error('该类型文档已存在,不能重复上传')
    return false
  }
  return true
}

const handleEdit = (row) => {
  currentDocument.value = row
  editForm.value = {
    type: row.type
  }
  editDialogVisible.value = true
}

const handleEditSuccess = (response) => {
  ElMessage.success('修改成功')
  editDialogVisible.value = false
  handleSearch()
}

const handleEditError = () => {
  ElMessage.error('修改失败')
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该文档吗？', '提示', {
      type: 'warning'
    })
    
    await axios.delete(`/api/documents/${row.id}`)
    ElMessage.success('删除成功')
    handleSearch()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handlePreview = async (row) => {
  currentDocument.value = row
  previewDialogVisible.value = true
  previewLoading.value = true
  
  try {
    previewUrl.value = `/api/documents/${row.id}/preview`
  } catch (error) {
    ElMessage.error('获取预览失败')
  } finally {
    previewLoading.value = false
  }
}

const downloadDocument = async () => {
  if (!currentDocument.value) return
  
  try {
    const response = await axios.get(`/api/documents/${currentDocument.value.id}/download`, {
      responseType: 'blob'
    })
    
    const url = window.URL.createObjectURL(new Blob([response.data]))
    const link = document.createElement('a')
    link.href = url
    link.setAttribute('download', currentDocument.value.name)
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
  } catch (error) {
    ElMessage.error('下载文档失败')
  }
}

// 生命周期钩子
onMounted(() => {
  handleSearch()
})
</script>

<style scoped>
.document-management {
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

.preview-container {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
}

.preview-toolbar {
  padding: 10px;
  background: #f5f7fa;
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  gap: 10px;
}

.preview-content {
  height: 500px;
  background: #fff;
  position: relative;
}

.preview-iframe {
  width: 100%;
  height: 100%;
  border: none;
}

.preview-video {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.preview-placeholder {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #909399;
  gap: 10px;
}

.preview-placeholder .el-icon {
  font-size: 48px;
}

.el-upload__tip {
  color: #909399;
  font-size: 12px;
  margin-top: 7px;
}
</style>