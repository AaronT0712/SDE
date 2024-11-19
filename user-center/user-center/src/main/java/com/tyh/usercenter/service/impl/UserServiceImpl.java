package com.tyh.usercenter.service.impl;
import java.util.Date;

import ch.qos.logback.core.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyh.usercenter.model.domain.User;
import com.tyh.usercenter.service.UserService;
import com.tyh.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tyh.usercenter.constant.UserConstant.NORMAL_ROLE;
import static com.tyh.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author aaron
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-11-12 19:47:36
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final String SALT = "tyh";   // 盐来混淆加密

    @Override
    public long user_register(String user_account, String user_password, String check_code) {
        // 1. 校验
        if (StringUtils.isAnyBlank(user_account, user_password, check_code)) {  // 为空
            return -1;
        }
        if (user_account.length() < 4) {    // 账号小于4位
            return -1;
        }
        if (user_password.length() < 8 || check_code.length() < 8) {    // 密码&校验码小于4位
            return -1;
        }
        // 账号不能包含特殊字符 (正则表达式)
        String validPattern = "^[a-zA-Z0-9]*$"; // 仅允许包含数字和大小写英文
        Matcher matcher = Pattern.compile(validPattern).matcher(user_account);
        if (!matcher.matches()) { // 如果不匹配，说明包含其他字符
            return -1;
        }

        if (!user_password.equals(check_code)) {    // 密码和校验码不同
            return -1;
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(); // 创建查询包装器，用于构建查询条件
        queryWrapper.eq("user_account", user_account); // 设置查询条件：查询 user_account 等于传入的 user_account 的记录
        long count = userMapper.selectCount(queryWrapper); // 统计满足条件的记录数
        if (count > 0) { // 判断是否存在至少一条符合条件的记录
            return -1; // 如果已存在记录，返回 -1 表示该账号已存在
        }

        // 2. 加密 (对密码进行加密)
        String encodedPassword = DigestUtils.md5DigestAsHex((SALT + user_password).getBytes());

        // 3. 插入数据
        User user = new User();
        user.setUser_account(user_account);
        user.setUser_password(encodedPassword);
        boolean saveResult = this.save(user);// 保存到数据库中
        if (!saveResult) {
            return -1;
        }

        // 4. 返回用户id
        return user.getId();
    }

    @Override
    public User user_login(String user_account, String user_password, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(user_account, user_password)) {  // 为空
            return null;
        }
        if (user_account.length() < 4) {    // 账号小于4位
            return null;
        }
        if (user_password.length() < 8 ) {    // 密码小于4位
            return null;
        }
        // 账号不能包含特殊字符 (正则表达式)
        String validPattern = "^[a-zA-Z0-9]*$"; // 仅允许包含数字和大小写英文
        Matcher matcher = Pattern.compile(validPattern).matcher(user_account);
        if (!matcher.matches()) { // 如果不匹配，说明包含其他字符
            return null;
        }

        // 2. 加密 (对密码进行加密)
        String encodedPassword = DigestUtils.md5DigestAsHex((SALT + user_password).getBytes());

        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(); // 创建查询包装器，用于构建查询条件
        queryWrapper.eq("user_account", user_account); // 查询 user_account 等于传入的 user_account 的记录
        queryWrapper.eq("user_password", encodedPassword);  // 同上
        User user_found = userMapper.selectOne(queryWrapper);// 根据条件，返回找到的用户
        if (user_found == null) {
            log.info("User login failed: user_account cannot match user_password"); // 这个只会在我们的后台出现
            return null;                                                            // 不会反映给前端
        }

        // 3. 信息脱敏: 隐藏敏感信息，防止数据库字段泄露 (密码什么的就不返回给前端了)
        User safety_user = getSafetyUser(user_found);

        // 4. 记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safety_user);    // attribute使用 map保存的，键值对

        // 5. 返回用户
        return safety_user;
    }

    /**
     * 脱敏操作
     * @param user_found
     * @return 脱敏后的用户
     */
    public User getSafetyUser(User user_found) {
        // 先做校验
        if (user_found == null) {return null;}

        User safety_user = new User();

        safety_user.setId(user_found.getId());
        safety_user.setUser_name(user_found.getUser_name());
        safety_user.setUser_account(user_found.getUser_account());
        safety_user.setAvatar_url(user_found.getAvatar_url());
        safety_user.setGender(user_found.getGender());
        safety_user.setPhone(user_found.getPhone());
        safety_user.setEmail(user_found.getEmail());
        safety_user.setUser_role(user_found.getUser_role());
        safety_user.setUser_status(user_found.getUser_status());
        safety_user.setCreate_time(user_found.getCreate_time());

        return safety_user;
    }


    @Override
    public int user_logout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE); // session可以看做一个字典，这样移除就好
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User current_user = (User) attribute;
        if (current_user == null) {
            return 1;
        } else {
            return 0;
        }
    }


}




