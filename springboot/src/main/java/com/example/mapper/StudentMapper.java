package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StudentMapper {
    void insert(Student student);

    @Select("select * from student where username = #{username}")
    Student selectByUsername(String username);

    @Select("select student.*, college.name as collegeName from student left join college on student.college_id = college.id")
    List<Student> selectAll();

    @Select("select student.*, college.name as collegeName from student " +
            "left join college on student.college_id = college.id " +
            "where student.name like concat('%', #{name}, '%')")
    List<Student> selectByName(String name);

    void updateById(Student student);

    @Delete("delete from student where id = #{id}")
    void deleteById(Integer id);

    @Select("select student.*, college.name as collegeName from student " +
            "left join college on student.college_id = college.id " +
            "where student.id = #{id}")
    Student selectById(Integer id);

    //秘书页面新增。
    List<Student> selectStudentList(
        @Param("studentName") String studentName,
        @Param("studentId") String studentId,
        @Param("offset") Integer offset,
        @Param("limit") Integer limit
    );

    Integer selectStudentCount(
        @Param("studentName") String studentName,
        @Param("studentId") String studentId
    );

    void updateStudentTeacher(
        @Param("studentId") Integer studentId,
        @Param("teacherId") Integer teacherId
    );

}
