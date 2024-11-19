package com.tyh.usercenter.common;

/**
 * 返回工具类
 *
 * @author aaron
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, "ok", data );
    }
}
