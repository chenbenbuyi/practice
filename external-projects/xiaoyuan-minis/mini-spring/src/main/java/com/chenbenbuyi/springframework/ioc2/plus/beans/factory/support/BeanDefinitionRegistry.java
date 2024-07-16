package com.chenbenbuyi.springframework.ioc2.plus.beans.factory.support;

import com.chenbenbuyi.springframework.ioc2.plus.beans.factory.config.BeanDefinition;

/**
 * @author chen
 * @date 2023/3/19 19:58
 * @Description 一个存放 BeanDefinition 的仓库，可以存放、移除、获取及判断 BeanDefinition 对象
 */

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);
}