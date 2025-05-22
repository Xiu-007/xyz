<template>
  <div class="dean-audit">
    <el-card>
      <!-- 筛选区 -->
      <div class="filter-bar">
        <el-form :inline="true" :model="filterForm" size="small">
          <el-form-item label="学生姓名">
            <el-input v-model="filterForm.studentName" placeholder="请输入学生姓名" clearable style="width: 140px" />
          </el-form-item>
          <el-form-item label="文档类型">
            <el-select v-model="filterForm.documentType" placeholder="全部" clearable style="width: 140px">
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
        <el-table-column prop="studentName" label="学生姓名" min-width="100">
          <template #default="scope">
            {{ scope.row.studentName || '未知' }}
          </template>
        </el-table-column>
        <el-table-column prop="name" label="文档名称" min-width="150"  show-overflow-tooltip/>
        <el-table-column prop="type" label="文档类型" min-width="110">
          <template #default="scope">
            {{ getTypeLabel(scope.row.type) }}
          </template>
        </el-table-column>
        <el-table-column prop="teacherName" label="指导教师" min-width="100" />
        <el-table-column prop="uploadTime" label="上传时间" min-width="150" />
        <el-table-column prop="status" label="审核状态" min-width="90" align="center">
          <template #default="scope">
            <el-tag :type="getStatusTagType(scope.row.status)">
              {{ getStatusLabel(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" min-width="180" align="center">
          <template #default="scope">
            <el-button 
              size="small" 
              type="primary" 
              @click="handleAudit(scope.row)"
              :disabled="scope.row.status !== 'DEAN_PENDING'"
            >
              审核
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

    <!-- 审核对话框 -->
    <el-dialog
      v-model="auditDialogVisible"
      title="文档审核"
      width="800px"
      :close-on-click-modal="false"
    >
      <el-form :model="auditForm" label-width="100px">
        <el-form-item label="学生姓名">
          <span>{{ currentDocument?.studentName }}</span>
        </el-form-item>
        <el-form-item label="文档名称">
          <span>{{ currentDocument?.documentName }}</span>
        </el-form-item>
        <el-form-item label="文档类型">
          <span>{{ getTypeLabel(currentDocument?.type) }}</span>
        </el-form-item>
        <el-form-item label="指导教师">
          <span>{{ currentDocument?.teacherName }}</span>
        </el-form-item>
        <el-form-item label="文档预览">
          <div class="preview-container">
            <div class="preview-toolbar">
              <el-button type="success" @click="downloadDocument">
                <el-icon><Download /></el-icon> 下载文档
              </el-button>
            </div>
            <div class="preview-content" v-loading="previewLoading">
              <!-- PDF预览 -->
              <iframe
                v-if="previewUrl && currentDocument?.documentType !== 'video'"
                :src="previewUrl"
                class="preview-iframe"
              ></iframe>
              <!-- 视频预览 -->
              <video
                v-else-if="previewUrl && currentDocument?.documentType === 'video'"
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
        </el-form-item>
        <el-form-item label="副院长审批意见" required>
          <el-input
            type="textarea"
            v-model="auditForm.deanOpinion"
            :rows="4"
            maxlength="200"
            show-word-limit
            placeholder="请填写审批意见"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitAudit('pass')">通过</el-button>
          <el-button type="danger" @click="submitAudit('reject')">不通过</el-button>
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
            v-if="previewUrl && currentDocument?.documentType !== 'video'"
            :src="previewUrl"
            class="preview-iframe"
          ></iframe>
          <!-- 视频预览 -->
          <video
            v-else-if="previewUrl && currentDocument?.documentType === 'video'"
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
import { ElMessage } from 'element-plus'
import { View, Download, Document } from '@element-plus/icons-vue'
import axios from 'axios'

// 数据定义
const documentList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const auditDialogVisible = ref(false)
const previewDialogVisible = ref(false)
const currentDocument = ref(null)
const previewUrl = ref('')
const previewLoading = ref(false)

const filterForm = ref({
  studentName: '',
  documentType: '',
  status: ''
})

const auditForm = ref({
  deanOpinion: ''
})

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
  { value: 'DEAN_PENDING', label: '待审核' },
  { value: 'APPROVED', label: '已通过' },
  { value: 'TEACHER_PENDING', label: '已打回教师' },
  { value: 'TEACHER_REJECTED', label: '已打回教师' }
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
    case 'DEAN_PENDING': return 'warning'
    case 'APPROVED': return 'success'
    case 'TEACHER_PENDING': return 'info'
    default: return ''
  }
}

// 方法定义
const handleSearch = async () => {
  try {
    const response = await axios.get('/api/documents/dean-audit', {
      params: {
        page: currentPage.value,
        size: pageSize.value,
        ...filterForm.value
      }
    })

    console.log('api返回数据:',response.data.data.list);
    documentList.value = response.data.data.list
    total.value = response.data.data.total
  } catch (error) {
    ElMessage.error('获取文档列表失败')
  }
}

const resetFilter = () => {
  filterForm.value = {
    studentName: '',
    documentType: '',
    status: ''
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

const handleAudit = (row) => {
  currentDocument.value = row
  auditForm.value = {
    deanOpinion: ''
  }
  auditDialogVisible.value = true
  handlePreview(row)
}

const handlePreview = async (row) => {
  currentDocument.value = row
  previewDialogVisible.value = true
  previewLoading.value = true
  try {
    // const response = await axios.get(`/api/documents/${row.id}/preview`)
    // previewUrl.value = response.data.data.url
    previewUrl.value = `/api/documents/${row.id}/preview`;
    console.log(previewUrl.value)
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
    link.setAttribute('download', currentDocument.value.documentName)
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
  } catch (error) {
    ElMessage.error('下载文档失败')
  }
}

const submitAudit = async (result) => {
  if (!auditForm.value.deanOpinion) {
    ElMessage.warning('请填写审批意见')
    return
  }

  const status = result === 'pass' ? 'APPROVED' : 'TEACHER_PENDING'
  const actionText = status === 'APPROVED' ? '通过' : '打回教师审核'

  try {
    await axios.post(`/api/documents/${currentDocument.value.id}/dean-audit`, {
      status: status,
      deanOpinion: auditForm.value.deanOpinion
    })
    ElMessage.success(`审核${actionText}成功`)
    auditDialogVisible.value = false
    handleSearch()
  } catch (error) {
    ElMessage.error(`审核${actionText}失败：${error.message}`)
  }
}

// 生命周期钩子
onMounted(() => {
  handleSearch()
})
</script>

<style scoped>
.dean-audit {
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
</style>