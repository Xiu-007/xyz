// springboot/src/main/java/com/example/mapper/DocumentMapper.java
package com.example.mapper;

import com.example.entity.Document;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DocumentMapper {
    @Select("SELECT * FROM documents WHERE userId = #{userId}")
    List<Document> findByUserId(@Param("userId") Integer userId);

    @Insert("INSERT INTO documents(name, type, filePath, fileSize, fileType, status, userId, createdAt, updatedAt,uploadTime) " +
            "VALUES(#{name}, #{type}, #{filePath}, #{fileSize}, #{fileType}, #{status}, #{userId}, NOW(), NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Document document);

    @Delete("DELETE FROM documents WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    @Update("UPDATE documents SET name = #{name}, type = #{type}, filePath = #{file_path}, " +
            "fileSize = #{file_size}, fileType = #{file_type}, status = #{status}, updateAt = NOW() " +
            "WHERE id = #{id}")
    int update(Document document);

    @Select("SELECT * FROM documents WHERE id = #{id}")
    Document findById(@Param("id") Integer id);

    //学生端搜索功能
    @Select("SELECT * FROM documents WHERE userId = #{userId} " +
            "AND (#{type} IS NULL OR type = #{type}) " +
            "AND (#{status} IS NULL OR status = #{status}) " +
            "ORDER BY uploadTime DESC " +
            "LIMIT #{offset}, #{limit}")
    List<Document> findByUserIdWithFilters(@Param("userId") Integer userId, 
                                         @Param("type") String type, 
                                         @Param("status") String status,
                                         @Param("offset") Integer offset,
                                         @Param("limit") Integer limit);

    @Select("SELECT COUNT(*) FROM documents WHERE userId = #{userId} " +
            "AND (#{type} IS NULL OR type = #{type}) " +
            "AND (#{status} IS NULL OR status = #{status})")
    Integer countByUserIdWithFilters(@Param("userId") Integer userId, 
                                    @Param("type") String type, 
                                    @Param("status") String status);


    //新增教师审核
    List<Document> selectTeacherAuditList(
        @Param("studentName") String studentName,
        @Param("documentType") String documentType,
        @Param("status") String status,
        @Param("teacherId") Integer teacherId,
        @Param("offset") Integer offset,
        @Param("limit") Integer limit
    );
    
    Integer selectTeacherAuditCount(
        @Param("studentName") String studentName,
        @Param("documentType") String documentType,
        @Param("status") String status,
        @Param("teacherId") Integer teacherId
    );
    
    @Update("UPDATE documents SET status = #{status}, teacherOpinion = #{teacherOpinion}, " +
            "updatedAt = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, 
                    @Param("status") String status, 
                    @Param("teacherOpinion") String teacherOpinion);

    //院长

    List<Document> selectDeanAuditList(@Param("studentName") String studentName,
                                       @Param("documentType") String documentType,
                                       @Param("status") String status,
                                       @Param("offset") int offset,
                                       @Param("size") int size);

    Integer selectDeanAuditCount(@Param("studentName") String studentName,
                                 @Param("documentType") String documentType,
                                 @Param("status") String status);

    @Update("UPDATE documents SET status = #{status}, deanOpinion = #{deanOpinion}, " +
            "updatedAt = NOW() WHERE id = #{id} AND status = 'DEAN_PENDING'")
    int updateDeanAudit(@Param("id") Integer id,
                        @Param("status") String status,
                        @Param("deanOpinion") String deanOpinion);

}