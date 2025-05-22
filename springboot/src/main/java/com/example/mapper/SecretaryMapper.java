package com.example.mapper;

import com.example.entity.Secretary;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SecretaryMapper {
//    @Insert("")
    void insert(Secretary secretary);

    @Select("select * from secretary where username = #{username}")
    Secretary selectByUsername(String username);

    @Select("select * from secretary")
    List<Secretary> selectAll();

    @Select("select * from secretary where name like concat('%', #{name},'%')")
    List<Secretary> selectByName(String name);

    void updateById(Secretary secretary);

    @Delete("delete from secretary where id = #{id}")
    void deleteById(Integer id);
}
