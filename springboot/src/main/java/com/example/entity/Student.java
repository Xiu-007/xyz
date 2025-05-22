package com.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 学生
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends Account {

    private Integer id;
    private String username;
    private String password;
    private String role;
    private String avatar;
    private String name;
    private String sex;
    private String code;
    private Integer teacherId;
    private String assignedTeacher; //用于储存教师姓名
    private Integer college_id;
    private Integer score;



    // @Override
    // public Integer getId() {
    //     return id;
    // }

    // @Override
    // public void setId(Integer id) {
    //     this.id = id;
    // }

    // @Override
    // public String getUsername() {
    //     return username;
    // }

    // @Override
    // public void setUsername(String username) {
    //     this.username = username;
    // }

    // @Override
    // public String getPassword() {
    //     return password;
    // }

    // @Override
    // public void setPassword(String password) {
    //     this.password = password;
    // }

    // @Override
    // public String getRole() {
    //     return role;
    // }

    // @Override
    // public void setRole(String role) {
    //     this.role = role;
    // }

    // @Override
    // public String getAvatar() {
    //     return avatar;
    // }

    // @Override
    // public void setAvatar(String avatar) {
    //     this.avatar = avatar;
    // }

    // @Override
    // public String getName() {
    //     return name;
    // }

    // @Override
    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getSex() {
    //     return sex;
    // }

    // public void setSex(String sex) {
    //     this.sex = sex;
    // }

    // public String getCode() {
    //     return code;
    // }

    // public void setCode(String code) {
    //     this.code = code;
    // }

 

    // public Integer getScore() {
    //     return score;
    // }

    // public void setScore(Integer score) {
    //     this.score = score;
    // }

    // public String getCollegeName() {
    //     return collegeName;
    // }

    // public void setCollegeName(String collegeName) {
    //     this.collegeName = collegeName;
    // }
}