package com.tyh.usercenter;

import com.tyh.usercenter.mapper.UserMapper;
import com.tyh.usercenter.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class MyBatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println("----- selectAll method test ------");
        List<User> userList = userMapper.selectList(null);
        Assertions.assertNotNull(userList); // 验证不为空
        userList.forEach(System.out::println);
    }
}