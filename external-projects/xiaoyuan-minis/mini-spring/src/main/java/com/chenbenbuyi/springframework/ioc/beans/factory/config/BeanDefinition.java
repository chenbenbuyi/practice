package com.chenbenbuyi.springframework.ioc.beans.factory.config;

import lombok.Data;

/**
 * @author chen
 * @date 2023/3/18 11:52
 * @Description bean的基础定义
 */

@Data
public class BeanDefinition {

    private String id;
    private String className;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

}
