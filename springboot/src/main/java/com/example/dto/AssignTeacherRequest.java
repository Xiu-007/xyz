package com.example.dto;

import lombok.Data;

@Data
public class AssignTeacherRequest {
    private Integer studentId;
    private Integer teacherId;
}