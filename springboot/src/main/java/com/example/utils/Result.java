package com.example.utils;


import lombok.Data;

/**
 * 统一返回结果格式
 */
@Data
public class Result {
    private int code;       // 状态码，200表示成功
    private String message; // 描述信息
    private Object data;    // 返回的数据

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(200, "成功", null);
    }

    public static Result success(Object data) {
        return new Result(200, "成功", data);
    }

    public static Result error(String message) {
        return new Result(500, message, null);
    }

    public static Result error(int code, String message) {
        return new Result(code, message, null);
    }

    // Getter and Setter
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }

}
