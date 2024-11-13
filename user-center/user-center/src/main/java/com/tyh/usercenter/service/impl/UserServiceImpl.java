package com.tyh.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tyh.usercenter.model.domain.User;
import com.tyh.usercenter.service.UserService;
import com.tyh.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author aaron
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-11-12 19:47:36
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




