package com.example.utils;

import java.util.List;

/**
 * 分页结果工具类
 */
public class PageResult<T> {
    private List<T> list;      // 当前页数据
    private Integer total;     // 总记录数

    public PageResult(List<T> list, Integer total) {
        this.list = list;
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}