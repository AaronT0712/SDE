package com.tyh.usercenter.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyh.usercenter.model.domain.User;
import com.tyh.usercenter.service.UserService;
import com.tyh.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author aaron
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-11-12 19:47:36
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    UserMapper userMapper;

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
        final String SALT = "tyh";  // 盐值来混淆
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
}




