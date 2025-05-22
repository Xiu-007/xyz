package com.example.dto;

import lombok.Data;

public class AuditRequest {
    private String status;
    private String teacherOpinion;
    private String deanOpinion;

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeacherOpinion() {
        return teacherOpinion;
    }

    public void setTeacherOpinion(String teacherOpinion) {
        this.teacherOpinion = teacherOpinion;
    }

    public String getDeanOpinion() {
        return deanOpinion;
    }

    public void setDeanOpinion(String deanOpinion) {this.deanOpinion = deanOpinion;}
}
