// springboot/src/main/java/com/example/service/DocumentService.java
package com.example.service;

import com.example.entity.Document;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.example.utils.PageResult;

public interface DocumentService {
    PageResult<Document> getDocuments(Integer userId, String type, String status, int page, int size);
    Document uploadDocument(MultipartFile file, String type, String userId) throws Exception;
    void deleteDocument(Integer id) throws Exception;
    String getPreviewUrl(Integer id);
    Document updateDocument(Integer id, MultipartFile file, String type) throws Exception;

    //教师审核
    PageResult<Document> getTeacherAuditList(
        String studentName, 
        String documentType, 
        String status,
        Integer teacherId,
        int page, 
        int size
    );
    
    void auditDocument(Integer id, String status, String teacherOpinion) throws Exception;

    Document findById(Integer id);

    //院长

    PageResult<Document> getDeanAuditList(
            String studentName,
            String documentType,
            String status,
            int page,
            int size
    );

    void deanAuditDocument(Integer id, String status, String deanOpinion) throws Exception;

    
}