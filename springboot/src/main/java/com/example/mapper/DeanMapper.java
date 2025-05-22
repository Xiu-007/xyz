package com.example.mapper;

import com.example.entity.Dean;
import com.example.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeanMapper {
//    @Insert("")
    void insert(Dean dean);

    @Select("select * from dean where username = #{username}")
    Dean selectByUsername(String username);

    @Select("select * from dean")
    List<Dean> selectAll();

    @Select("select * from dean where name like concat('%', #{name},'%')")
    List<Dean> selectByName(String name);

    void updateById(Dean dean);

    @Delete("delete from dean where id = #{id}")
    void deleteById(Integer id);
}
