package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Account;
import com.example.entity.Secretary;
import com.example.exception.CustomException;
import com.example.mapper.SecretaryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 主管教学副院长业务处理
 **/
@Service
public class SecretaryService {

    @Resource
    private SecretaryMapper secretaryMapper;


    public void add(Secretary secretary) {
        //  TODO: 添加逻辑处理
        Secretary dbSecretary=secretaryMapper.selectByUsername(secretary.getUsername());
        if (ObjectUtil.isNotEmpty(dbSecretary)){
            throw new CustomException("用户名已存在");
        }
        if (ObjectUtil.isEmpty(secretary.getPassword())){
            secretary.setPassword("123456");
        }
        secretary.setRole("SECRETARY");

        secretaryMapper.insert(secretary);
    }


    public PageInfo<Secretary> selectPage(Secretary secretary, Integer pageNum, Integer pageSize) {
        List<Secretary> list = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        if (ObjectUtil.isNotEmpty(secretary.getName())){
            list = secretaryMapper.selectByName(secretary.getName());
        }else {
            list = secretaryMapper.selectAll();
        }
        return PageInfo.of(list);
    }

    public void updateById(Secretary secretary) {
        secretaryMapper.updateById(secretary);
    }

    public void deleteById(Integer id) {
        secretaryMapper.deleteById(id);
    }

    public Secretary login(Account account) {
        Secretary dbSecretary = secretaryMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbSecretary)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbSecretary.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
        return dbSecretary;
    }

    public void updatePassword(Account account) {
        Secretary dbSecretary= secretaryMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbSecretary)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbSecretary.getPassword())) {
            throw new CustomException("原密码错误");
        }
        dbSecretary.setPassword(account.getNewPassword());
        secretaryMapper.updateById(dbSecretary);
    }
}
