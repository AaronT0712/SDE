package com.tyh.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {  // 泛型
    private int code;   // 错误代码

    private String msg; // 错误信息

    private T data; // 可以接受任意类型的返回值

    private String description;

    public BaseResponse(int code, String msg, T data, String description) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.description = description;
    }

    public BaseResponse(int code, String message, T data) {
        // BaseResponse baseResponse = new BaseResponse(code, "", data);
        this(code, message, data, "");
    }

    public BaseResponse(int code, T data) {
        this(code, "", data, "");
    }
}
