package com.example.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.service.TeacherAssignmentService;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.utils.Result;
import com.example.utils.PageResult;
import java.util.List;
import com.example.dto.AssignTeacherRequest;


@RestController
@RequestMapping("/api")
public class TeacherAssignmentController {
    @Autowired
    private TeacherAssignmentService teacherAssignmentService;
    
    @GetMapping("/students/assignments")
    public Result getStudentList(
        @RequestParam(required = false) String studentName,
        @RequestParam(required = false) String studentId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        PageResult<Student> pageResult = teacherAssignmentService.getStudentList(
            studentName, studentId, page, size);
        return Result.success(pageResult);
    }
    
    @GetMapping("/teachers/list")
    public Result getTeacherList() {
        List<Teacher> teachers = teacherAssignmentService.getTeacherList();
        return Result.success(teachers);
    }
    
    @PostMapping("/students/assign_teacher")
    public Result assignTeacher(@RequestBody AssignTeacherRequest request) {
        teacherAssignmentService.assignTeacher(request.getStudentId(), request.getTeacherId());
        return Result.success();
    }
}