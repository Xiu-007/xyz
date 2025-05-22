package com.example.controller;

import com.example.common.Result;
import com.example.entity.Secretary;
import com.example.service.SecretaryService;
import com.example.service.TeacherService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secretary")
public class SecretaryController {

    @Resource
    private SecretaryService secretaryService;
//    @Autowired
//    private TeacherService teacherService;

    @PostMapping("/add")
    public Result add(@RequestBody Secretary secretary) {
       secretaryService.add(secretary);
       return Result.success();
    }
    @PutMapping("/update")
    public Result update(@RequestBody Secretary secretary) {
        secretaryService.updateById(secretary);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        secretaryService.deleteById(id);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Secretary secretary,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo<Secretary> pageInfo = secretaryService.selectPage(secretary,pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
