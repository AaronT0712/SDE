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
     * 用户注册
     * @param user_account 用户账户
     * @param user_password 用户密码
     * @param check_code 校验密码i
     * @return 新用户的id
     */
    long user_register(String user_account, String user_password, String check_code);

    /**
     * 用户登录
     * @param user_account
     * @param user_password
     * @param request   (用于保存用户的登录信息)
     * @return 脱敏后的用户
     */
    User user_login(String user_account, String user_password, HttpServletRequest request);

    User getSafetyUser(User user_found);

    /**
     * 用户退出
     * @param request
     * @return 1:退出成功 0:退出失败
     */
    int user_logout(HttpServletRequest request);
}
