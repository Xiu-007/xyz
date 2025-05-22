package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.mapper.StudentMapper;
import com.example.mapper.TeacherMapper;
import com.example.entity.Student;
import com.example.entity.Teacher;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageInfo;
import com.example.utils.PageResult;


@Service
@Slf4j
public class TeacherAssignmentService {
    @Autowired
    private StudentMapper studentMapper;
    
    @Autowired
    private TeacherMapper teacherMapper;
    
    public PageResult<Student> getStudentList(String studentName, String studentId, int page, int size) {
        try {
            int offset = (page - 1) * size;
            // 添加日志
            log.info("查询参数: studentName={}, studentId={}, page={}, size={}", 
                    studentName, studentId, page, size);
            
            List<Student> list = studentMapper.selectStudentList(studentName, studentId, offset, size);
            Integer total = studentMapper.selectStudentCount(studentName, studentId);
            
            // 添加日志
            log.info("查询结果: list.size={}, total={}", list.size(), total);
            
            return new PageResult<>(list, total);
        } catch (Exception e) {
            log.error("获取学生列表失败", e);
            throw new RuntimeException("获取学生列表失败: " + e.getMessage());
        }
    }
    
    public List<Teacher> getTeacherList() {
        return teacherMapper.selectAllTeachers();
    }
    
    @Transactional
    public void assignTeacher(Integer studentId, Integer teacherId) {
        if (teacherId != null) {
            Teacher teacher = teacherMapper.selectTeacherById(teacherId);
            if (teacher == null) {
                throw new RuntimeException("教师不存在");
            }
        }
        studentMapper.updateStudentTeacher(studentId, teacherId);
    }
}
