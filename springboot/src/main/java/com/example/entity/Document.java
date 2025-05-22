package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Document {

    private Integer id;
    private String name;
    private String type;
    private String filePath;
    private String fileSize;
    private String fileType;
    private String status;
    private String teacherOpinion;
    private String deanOpinion;


    private LocalDateTime uploadTime;

    private Integer userId;
    private String createdAt;
    private String updatedAt;

    //教师审核
    private String studentName;
    private String teacherName;



//    // Getter 方法
//    public Integer getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public String getFile_path() {
//        return file_path;
//    }
//
//    public String getFile_size() {
//        return file_size;
//    }
//
//    public String getFile_type() {
//        return file_type;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public String getTeacher_opinion() {
//        return teacher_opinion;
//    }
//
//    public String getDean_opinion() {
//        return dean_opinion;
//    }
//
//    public LocalDateTime getUploadtime() {
//        return uploadTime;
//    }
//
//    public Integer getUser_id() {
//        return user_id;
//    }
//
//    public String getCreate_at() {
//        return created_at;
//    }
//
//    public String getUpdate_at() {
//        return updated_at;
//    }
//
//    // Setter 方法
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public void setFile_path(String file_path) {
//        this.file_path = file_path;
//    }
//
//    public void setFile_size(String file_size) {
//        this.file_size = file_size;
//    }
//
//    public void setFile_type(String file_type) {
//        this.file_type = file_type;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public void setTeacher_opinion(String teacher_opinion) {
//        this.teacher_opinion = teacher_opinion;
//    }
//
//    public void setDean_opinion(String dean_opinion) {
//        this.dean_opinion = dean_opinion;
//    }
//
//    public void setUploadtime(LocalDateTime uploadtime) {
//        this.uploadTime = uploadTime;
//    }

//    public void setUser_id(Integer user_id) {
//        this.user_id = user_id;
//    }
//
//    public void setCreate_at(String create_at) {
//        this.created_at = create_at;
//    }
//
//    public void setUpdate_at(String update_at) {
//        this.updated_at = update_at;
//    }
}