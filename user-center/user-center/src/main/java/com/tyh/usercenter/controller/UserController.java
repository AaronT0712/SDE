package com.tyh.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyh.usercenter.common.BaseResponse;
import com.tyh.usercenter.common.ResultUtils;
import com.tyh.usercenter.model.domain.User;
import com.tyh.usercenter.model.domain.request.UserLoginRequest;
import com.tyh.usercenter.model.domain.request.UserRegisterRequest;
import com.tyh.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.tyh.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.tyh.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author aaron
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest 请求体
     * @return user_id
     */
    @PostMapping("/register")
    public BaseResponse<Long> user_register(@RequestBody UserRegisterRequest userRegisterRequest) {   // 和前端传来的参数对应
        if (userRegisterRequest == null) {
            return null;
        }

        String userAccount = userRegisterRequest.getUser_account();
        String userPassword = userRegisterRequest.getUser_password();
        String checkCode = userRegisterRequest.getCheck_code();

        if (StringUtils.isBlank(userAccount) || StringUtils.isBlank(userPassword) || StringUtils.isBlank(checkCode)) {
            return null;
        }

        long result = userService.user_register(userAccount, userPassword, checkCode);

        // return new BaseResponse<>(0, "ok", result); // 封账返回的结果
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return 脱敏后的用户
     */
    @PostMapping("/login")
    public BaseResponse<User> user_login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUser_account();
        String userPassword = userLoginRequest.getUser_password();

        if (StringUtils.isBlank(userAccount) || StringUtils.isBlank(userPassword)) {
            return null;
        }

        User result = userService.user_login(userAccount, userPassword, request);

        // return new BaseResponse<>(0, "ok", result);
        return ResultUtils.success(result);
    }

    /**
     * 用户查询 (仅管理员)
     *
     * @param user_name
     * @param request   用户的登录态
     * @return 模糊查询到的List
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> search_user(String user_name, HttpServletRequest request) {
        // 仅管理员可查询
        if (!isAdmin(request)) {
            return new ArrayList<>();
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(user_name)) {
            queryWrapper.like("user_name", user_name);  // like表示模糊查询
        }

        // 获得的用户列表，脱敏
        List<User> userList = userService.list(queryWrapper);
        userList = userList.stream().map(user ->
            userService.getSafetyUser(user)
        ).collect(Collectors.toList());

        return ResultUtils.success(userList);
    }

    /**
     * 根据id删除用户 (仅管理员)
     *
     * @param id
     * @param request
     * @return 删除结果
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> delete_user(@RequestBody long id, HttpServletRequest request) {
        // 仅管理员可删除
        if (!isAdmin(request)) {
            return false;
        }

        if (id <= 0) {
            return false;
        }
        boolean result = userService.removeById(id);

        return ResultUtils.success(result);
    }

    /**
     * 判断是否为管理员
     * @param request
     * @return 判断结果
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObject;
        if (user == null || user.getUser_role() != ADMIN_ROLE) {
            return false;
        }
        return true;
    }

    /**
     * 获取当前用户
     * @param request
     * @return 数据库中的用户 + 脱敏处理
     */
    @GetMapping("/current")
    public BaseResponse<User> get_current_user(HttpServletRequest request) {
        // 从session中，就可以获取登录信息 (和检查管理员相似)
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObject;
        if (currentUser == null) {
            return null;
        }
        // 可能数据库的信息容易改动，和本地存储的不同
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        currentUser = userService.getById(userId); // 数据库重新获取
        // return userService.getSafetyUser(currentUser);  // 脱敏
        return ResultUtils.success(userService.getSafetyUser(currentUser));
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> user_logout(HttpServletRequest request) {
        if (request == null) {return null;}

        int result = userService.user_logout(request);

        return ResultUtils.success(result);
    }
}
