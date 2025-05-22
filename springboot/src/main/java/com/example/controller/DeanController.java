package com.example.controller;

import com.example.common.Result;
import com.example.entity.Dean;
import com.example.service.DeanService;
import com.example.service.TeacherService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dean")
public class DeanController {

    @Resource
    private DeanService deanService;
//    @Autowired
//    private TeacherService teacherService;

    @PostMapping("/add")
    public Result add(@RequestBody Dean dean) {
       deanService.add(dean);
       return Result.success();
    }
    @PutMapping("/update")
    public Result update(@RequestBody Dean dean) {
        deanService.updateById(dean);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        deanService.deleteById(id);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Dean dean,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo<Dean> pageInfo = deanService.selectPage(dean,pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
