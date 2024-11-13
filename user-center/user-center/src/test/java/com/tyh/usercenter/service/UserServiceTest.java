package com.tyh.usercenter.service;
import java.util.Date;

import com.tyh.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User(); // option+Enter: generate with default value
        user.setId(0L);
        user.setUser_name("TYH");
        user.setUser_account("123");
        user.setAvatar_url("123");
        user.setGender(0);
        user.setUser_password("123");
        user.setPhone("123");
        user.setEmail("123");
        user.setUser_status(0);
        user.setCreate_time(new Date());
        user.setUpdate_time(new Date());
        user.setIs_delete(0);

        boolean result = userService.save(user);
        System.out.println("Test" + user.getId());
        Assertions.assertTrue(result);
    }

    /**
     * 使用前，确保里面有TYH123
     * 没有 NewUser123
     */
    @Test
    void user_register() {
        // Case 1: 用户名、密码或校验码为空
        String user_account = "TYH123";
        String user_password = "";
        String check_code = "123";
        long result = userService.user_register(user_account, user_password, check_code);
        Assertions.assertEquals(-1, result);

        // Case 2: 用户名小于4位
        user_account = "TYH";
        user_password = "password123";
        check_code = "password123";
        result = userService.user_register(user_account, user_password, check_code);
        Assertions.assertEquals(-1, result);

        // Case 3: 密码或校验码小于8位
        user_account = "TYH123";
        user_password = "12345";
        check_code = "12345";
        result = userService.user_register(user_account, user_password, check_code);
        Assertions.assertEquals(-1, result);

        // Case 4: 用户名包含特殊字符
        user_account = "TYH@123";
        user_password = "password123";
        check_code = "password123";
        result = userService.user_register(user_account, user_password, check_code);
        Assertions.assertEquals(-1, result);

        // Case 5: 密码和校验码不匹配
        user_account = "TYH123";
        user_password = "password123";
        check_code = "password456";
        result = userService.user_register(user_account, user_password, check_code);
        Assertions.assertEquals(-1, result);

        // Case 6: 账号已存在（数据库中已有账号 "TYH123"）
        user_account = "TYH123";
        user_password = "newPassword123";
        check_code = "newPassword123";
        result = userService.user_register(user_account, user_password, check_code);
        Assertions.assertEquals(-1, result);

        // Case 7: 成功注册（假设新的账号）
        user_account = "NewUser123";
        user_password = "password123";
        check_code = "password123";
        result = userService.user_register(user_account, user_password, check_code);
        Assertions.assertTrue(result > 0); // 返回新用户ID
    }
}