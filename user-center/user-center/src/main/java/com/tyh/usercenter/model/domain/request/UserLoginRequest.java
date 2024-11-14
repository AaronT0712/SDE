package com.tyh.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 * 有两个参数
 * @author aaron
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 2051676636033431574L;

    private String user_account;

    private String user_password;
}
