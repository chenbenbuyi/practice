package com.chenbenbuyi.springframework.ioc3.beans.factory.config;

import com.chenbenbuyi.springframework.ioc3.beans.factory.BeanFactory;

/**
 * @author chen
 * @date 2023/3/20 23:09
 * @Description
 */
public interface ConfigurableBeanFactory extends BeanFactory,SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);

}