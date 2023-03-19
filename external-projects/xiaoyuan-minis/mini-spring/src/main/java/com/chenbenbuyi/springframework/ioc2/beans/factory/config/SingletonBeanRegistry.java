package com.chenbenbuyi.springframework.ioc2.beans.factory.config;

/**
 * @author chen
 * @date 2023/3/19 15:40
 * @Description 单例注册表——管理单例 Bean 的接口
 */

public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}