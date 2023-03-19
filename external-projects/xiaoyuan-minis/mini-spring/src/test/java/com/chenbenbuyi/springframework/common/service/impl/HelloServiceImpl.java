package com.chenbenbuyi.springframework.common.service.impl;

import com.chenbenbuyi.springframework.common.service.HelloService;
import com.chenbenbuyi.springframework.common.service.UserService;

/**
 * @author chen
 * @date 2023/3/18 12:12
 * @Description
 */

public class HelloServiceImpl implements HelloService {

    private String name;
    private int age;
    private String addr;

    // 对象注入
    private UserService userService;

    // todo 构造器注入

    public HelloServiceImpl(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void sayHello() {
        System.out.println("构造器注入的名称：" + name + ",年龄：" + age + ",属性注入的地址：" + addr);
    }
}
