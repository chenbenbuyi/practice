package com.chenbenbuyi.springframework.common.service.impl;

import com.chenbenbuyi.springframework.common.service.AutowiredService;

/**
 * @author chen
 * @date 2023/3/23 22:55
 * @Description
 */
public class AutowiredServiceImpl implements AutowiredService {
    @Override
    public void helloAutowired() {
        System.out.println("注解的方式注入成功============");
    }
}
