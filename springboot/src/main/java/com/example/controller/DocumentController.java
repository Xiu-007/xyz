// springboot/src/main/java/com/example/controller/DocumentController.java
package com.example.controller;

import com.example.common.Result;
import com.example.entity.Document;
import com.example.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.utils.PageResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.example.dto.AuditRequest;


@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;


    @GetMapping("/list")
    public Result<List<Document>> getDocuments(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        PageResult<Document> result = documentService.getDocuments(userId, type, status, page, size);
        return Result.success(result);
    }

    @PostMapping("/upload")
    public Result<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type,
            @RequestParam("userId") String userId) {
        try {
            Document document = documentService.uploadDocument(file, type, userId);

            return Result.success(document);
        } catch (Exception e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteDocument(@PathVariable Integer id) {
        try {
            documentService.deleteDocument(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("文件删除失败：" + e.getMessage());
        }
    }

    @GetMapping("/{id}/preview")
    public ResponseEntity<?> previewDocument(@PathVariable Integer id) {
        try {
            Document document = documentService.findById(id);
            if (document == null) {
                return ResponseEntity.notFound().build();
            }

            String filePath = document.getFilePath();
            System.out.println(filePath);
            /**
             * 将文件转换为pdf，并返回文件数据。
             */
            if(!filePath.contains(".pdf")){
                int dotIndex = filePath.lastIndexOf('.');
                if(dotIndex > 0) {
                    try {
                        ProcessBuilder builder = new ProcessBuilder();
                        builder.command("soffice", "--headless", "--convert-to", "pdf",
                                filePath, "--outdir", ".\\uploads");

                        Process process = builder.start();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }

                        int exitCode = process.waitFor();
                        System.out.println("转换完成，退出码: " + exitCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String extension = filePath.substring(dotIndex + 1);
                    filePath = filePath.replace("." + extension, ".pdf");
                } else {
                    System.out.println("无有效扩展名");
                }
            }else {
                System.out.println("There");
            }
            Path path = Paths.get(filePath);
            Resource resource = new UrlResource(path.toUri());

            // 对文件名进行 UTF-8 编码
            String encodedFileName = URLEncoder.encode(document.getName(), StandardCharsets.UTF_8.toString())
                    .replace("+", "%20");  // 替换空格的编码
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + encodedFileName)
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("文件预览失败：" + e.getMessage());
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadDocument(@PathVariable Integer id) {
        try {
            Document document = documentService.findById(id);
            if (document == null) {
                return ResponseEntity.notFound().build();
            }

            String filePath = document.getFilePath();
            Path path = Paths.get(filePath);
            Resource resource = new UrlResource(path.toUri());

            // 对文件名进行 UTF-8 编码
            String encodedFileName = URLEncoder.encode(document.getName(), StandardCharsets.UTF_8.toString())
                    .replace("+", "%20");  // 替换空格的编码

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("文件下载失败：" + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<Document> updateDocument(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type) {
        try {
            Document document = documentService.updateDocument(id, file, type);
            return Result.success(document);
        } catch (Exception e) {
            return Result.error("文件更新失败：" + e.getMessage());
        }
    }

    //教师审核部分

    //获取教师审核列表
    @GetMapping("/teacher-audit")
    public Result<PageResult<Document>> getTeacherAuditList(
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) String documentType,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam Integer teacherId) {
        try {
            PageResult<Document> result = documentService.getTeacherAuditList(
                studentName, documentType, status, teacherId, page, size);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取审核列表失败：" + e.getMessage());
        }
    }

    //教师审核
    @PostMapping("/{id}/teacher-audit")
    public Result<Void> auditDocument(
            @PathVariable Integer id,
            @RequestBody AuditRequest request) {
        try {
            documentService.auditDocument(id, request.getStatus(), request.getTeacherOpinion());
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("审核提交失败：" + e.getMessage());
        }
    }



    //院长审核页面部分
    @GetMapping("/dean-audit")
    public Result<PageResult<Document>> getDeanAuditList(
        @RequestParam(required = false) String studentName,
        @RequestParam(required = false) String documentType,
        @RequestParam(required = false) String status,
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        try {
        PageResult<Document> result = documentService.getDeanAuditList(
            studentName, documentType, status, page, size);
        return Result.success(result);
    } catch (Exception e) {
        return Result.error("获取审核列表失败：" + e.getMessage());
    }
}

    @PostMapping("/{id}/dean-audit")
    public Result<Void> deanAuditDocument(
        @PathVariable Integer id,
        @RequestBody AuditRequest request) {
        try {
        // 确保状态值匹配
        String status = request.getStatus();
        if (!"APPROVED".equals(status) && !"TEACHER_PENDING".equals(status)) {
            return Result.error("无效的审核状态");
        }
        documentService.deanAuditDocument(id, status, request.getDeanOpinion());
        return Result.success(null);
    } catch (Exception e) {
        return Result.error("审核提交失败：" + e.getMessage());
    }
}

}