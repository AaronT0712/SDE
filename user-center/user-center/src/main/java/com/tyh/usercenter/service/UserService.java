package com.tyh.usercenter.service;

import com.tyh.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author aaron
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-11-12 19:47:36
*/
public interface UserService extends IService<User> {
    /**
     *
     * @param user_account 用户账户
     * @param user_password 用户密码
     * @param check_code 校验密码i
     * @return 新用户的id
     */
    long user_register(String user_account, String user_password, String check_code);

    /**
     *
     * @param user_account
     * @param user_password
     * @return
     */
    User user_login(String user_account, String user_password, HttpServletRequest request);
}
