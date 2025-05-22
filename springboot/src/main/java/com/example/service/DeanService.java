package com.example.service;

import java.util.ArrayList;
import java.util.List;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Account;
import com.example.entity.Dean;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.exception.CustomException;
import com.example.mapper.TeacherMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapper.DeanMapper;
/**
 * 主管教学副院长业务处理
 **/
@Service
public class DeanService {

    @Resource
    private DeanMapper deanMapper;


    public void add(Dean dean) {
        //  TODO: 添加逻辑处理
        Dean dbDean=deanMapper.selectByUsername(dean.getUsername());
        if (ObjectUtil.isNotEmpty(dbDean)){
            throw new CustomException("用户名已存在");
        }
        if (ObjectUtil.isEmpty(dean.getPassword())){
            dean.setPassword("123456");
        }
        dean.setRole("DEAN");

        deanMapper.insert(dean);
    }


    public PageInfo<Dean> selectPage(Dean dean, Integer pageNum, Integer pageSize) {
        List<Dean> list = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        if (ObjectUtil.isNotEmpty(dean.getName())){
            list = deanMapper.selectByName(dean.getName());
        }else {
            list = deanMapper.selectAll();
        }
        return PageInfo.of(list);
    }

    public void updateById(Dean dean) {
        deanMapper.updateById(dean);
    }

    public void deleteById(Integer id) {
        deanMapper.deleteById(id);
    }

    public Dean login(Account account) {
        Dean dbDean = deanMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbDean)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbDean.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
        return dbDean;
    }

    public void updatePassword(Account account) {
        Dean dbDean= deanMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbDean)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbDean.getPassword())) {
            throw new CustomException("原密码错误");
        }
        dbDean.setPassword(account.getNewPassword());
        deanMapper.updateById(dbDean);
    }
}
