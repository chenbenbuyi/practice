package com.chenbenbuyi.springframework.ioc.plus.beans.factory;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.ioc.beans.factory.config.BeanDefinition;

/**
 * @author chen
 * @date 2023/3/19 14:59
 * @Description
 */

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    /**
     * 注册一个 BeanDefinition
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);

}
