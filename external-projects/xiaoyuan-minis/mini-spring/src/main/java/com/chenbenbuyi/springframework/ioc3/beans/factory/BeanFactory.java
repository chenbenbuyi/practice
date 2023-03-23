package com.chenbenbuyi.springframework.ioc3.beans.factory;

import com.chenbenbuyi.springframework.common.exception.BeansException;

/**
 * @author chen
 * @date 2023/3/19 15:37
 * @Description 为了和 Spring 框架内的方法名保持一致，把 BeanFactory 接口中定义的 registryBeanDefinition 方法修改为 registryBean，参数修改为 beanName 与 obj。其中，obj 为 Object 类，指代与 beanName 对应的 Bean 的信息
 * 原来的 registerBeanDefinition 方法暂时在实现类中保留保证调试
 */


public interface BeanFactory {
    Object getBean(String name) throws BeansException;

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);
}