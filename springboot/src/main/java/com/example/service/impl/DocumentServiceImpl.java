// springboot/src/main/java/com/example/service/impl/DocumentServiceImpl.java
package com.example.service.impl;

import com.example.entity.Document;
import com.example.mapper.DocumentMapper;
import com.example.service.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import com.example.utils.PageResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;


@Service
public class DocumentServiceImpl implements DocumentService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final DocumentMapper documentMapper;

    public DocumentServiceImpl(DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    @Override
    public PageResult<Document> getDocuments(Integer userId, String type, String status, int page, int size) {
        int offset = (page - 1) * size;
        List<Document> list = documentMapper.findByUserIdWithFilters(userId, type, status, offset, size);
        Integer total = documentMapper.countByUserIdWithFilters(userId, type, status);
        return new PageResult<>(list, total);
    }

    @Override
    public Document uploadDocument(MultipartFile file, String type, String userId) throws Exception {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        Document document = new Document();
        document.setName(file.getOriginalFilename());
        document.setType(type);
        document.setFilePath(filePath.toString());
        document.setFileSize(String.valueOf(file.getSize()));
        document.setFileType(file.getContentType());
        document.setStatus("PENDING");
        document.setUserId(Integer.parseInt(userId));


        documentMapper.insert(document);
        return document;
    }

    @Override
    public void deleteDocument(Integer id) throws Exception {
    System.out.println("准备删除文档，id=" + id);
    Document document = documentMapper.findById(id);
    if (document == null) {
        System.out.println("文档不存在，id=" + id);
        throw new RuntimeException("文档不存在，id=" + id);
    }

    // 删除物理文件
        try {
            String filePath = document.getFilePath();
            System.out.println("准备删除文件路径：" + filePath);

            // 增加 null 或空值判断
            if (filePath != null && !filePath.isEmpty()) {
                Path path = Paths.get(filePath);
                boolean deleted = Files.deleteIfExists(path);
                System.out.println("物理文件删除结果：" + deleted);
            } else {
                System.out.println("文件路径为空，跳过物理文件删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("物理文件删除失败: " + e.getMessage(), e);
        }

    // 删除数据库记录
    int rows = documentMapper.deleteById(id);
    System.out.println("数据库删除结果，受影响行数：" + rows);
    if (rows == 0) {
        throw new RuntimeException("数据库删除失败，id=" + id);
    }
}

    @Override
    public String getPreviewUrl(Integer id) {
        Document document = documentMapper.findById(id);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }
        return document.getFilePath();
    }

    @Override
    public Document updateDocument(Integer id, MultipartFile file, String type) throws Exception {
        Document document = documentMapper.findById(id);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }

        // 删除旧文件
        Path oldFilePath = Paths.get(document.getFilePath());
        Files.deleteIfExists(oldFilePath);

        // 保存新文件
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        document.setName(file.getOriginalFilename());
        document.setType(type);
        document.setFilePath(filePath.toString());
        document.setFileSize(String.valueOf(file.getSize()));
        document.setFileType(file.getContentType());
        document.setStatus("PENDING");

        documentMapper.update(document);
        return document;
    }

    //新增教师审核

    
    @Override
    public PageResult<Document> getTeacherAuditList(
            String studentName, 
            String documentType, 
            String status,
            Integer teacherId,
            int page, 
            int size
    ) {
        int offset = (page - 1) * size;
        List<Document> list = documentMapper.selectTeacherAuditList(
            studentName, documentType, status, teacherId, offset, size);
             Integer total = documentMapper.selectTeacherAuditCount(
            studentName, documentType, status, teacherId);
        return new PageResult<>(list, total);
    }
    
    @Override
    @Transactional
    public void auditDocument(Integer id, String status, String teacherOpinion) throws Exception {
        Document document = documentMapper.findById(id);
        if (document == null) {
            throw new RuntimeException("文档不存在");
        }

        //修改状态检查，允许PENDING和TEACHER_PENDING状态
        if (!"PENDING".equals(document.getStatus()) && !"TEACHER_PENDING".equals(document.getStatus())) {
            throw new RuntimeException("文档状态不正确");
        }
        
        int rows = documentMapper.updateStatus(id, status, teacherOpinion);
        if (rows == 0) {
            throw new RuntimeException("更新文档状态失败");
        }
    }
   
    //编码问题
    public Document findById(Integer id) {
        return documentMapper.findById(id);
    }


    //院长审核页面部分


    @Override
    public PageResult<Document> getDeanAuditList(
            String studentName,
            String documentType,
            String status,
            int page,
            int size) {
        int offset = (page - 1) * size;
        List<Document> list = documentMapper.selectDeanAuditList(studentName, documentType, status, offset, size);
        Integer total = documentMapper.selectDeanAuditCount(studentName, documentType, status);
        return new PageResult<>(list, total);
    }

    @Override
    @Transactional
    public void deanAuditDocument(Integer id, String status, String deanOpinion) throws Exception {
     Document document = documentMapper.findById(id);
     if (document == null) {
        throw new RuntimeException("文档不存在");
    }
    
    // 检查状态是否允许院长审核
    if (!"DEAN_PENDING".equals(document.getStatus())) {
        throw new RuntimeException("当前文档状态不允许院长审核");
    }
    
    // 验证状态转换的合法性
    if (!"APPROVED".equals(status) && !"TEACHER_PENDING".equals(status)) {
        throw new RuntimeException("无效的审核状态");
    }
    
    // 更新状态和院长意见
    // document.setStatus(status);
    // document.setDeanOpinion(deanOpinion);
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // document.setUpdatedAt(LocalDateTime.now().format(formatter));
    int rows = documentMapper.updateDeanAudit(id, status, deanOpinion);
    if (rows == 0) {
        throw new RuntimeException("更新文档状态失败");
    }
}

    

}