package com.chenbenbuyi.springframework.ioc3.beans.factory.config;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.ioc3.beans.factory.BeanFactory;

/**
 * @author chen
 * @date 2023/3/20 21:48
 * @Description
 */
public interface BeanPostProcessor {

    /**
     * Bean 初始化之前操作
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;


    /**
     * Bean 初始化之后操作
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;


    void setBeanFactory(BeanFactory beanFactory);

}