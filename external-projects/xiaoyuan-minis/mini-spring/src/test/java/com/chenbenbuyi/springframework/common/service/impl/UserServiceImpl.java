package com.chenbenbuyi.springframework.common.service.impl;

import com.chenbenbuyi.springframework.common.service.UserService;

/**
 * @author chen
 * @date 2023/3/19 21:53
 * @Description
 */
public class UserServiceImpl implements UserService {


    @Override
    public void login() {
        System.out.println("用户：登录成功，输入密码==========");
    }
}
