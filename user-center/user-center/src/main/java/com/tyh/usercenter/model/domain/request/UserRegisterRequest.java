package com.tyh.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * 有三个参数
 * @author aaron
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 6359925346537024719L;

    private String user_account;

    private String user_password;

    private String check_code;

}
