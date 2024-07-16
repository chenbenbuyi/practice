package com.chenbenbuyi.springframework.ioc2.beans.factory;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.ioc2.beans.factory.config.BeanDefinition;
import com.chenbenbuyi.springframework.ioc2.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @date 2023/3/19 16:23
 * @Description 该迭代中，简单工厂增加对单例的继承，确保通过 SimpleBeanFactory 创建的 Bean 默认就是单例的
 */

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);


    //getBean，容器的核心方法
    public Object getBean(String beanName) throws BeansException {
        //先尝试直接拿bean实例
        Object singleton = this.getSingleton(beanName);
        //如果此时还没有这个bean的实例，则获取它的定义来创建实例
        if (singleton == null) {
            //获取bean的定义
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (beanDefinition == null) {
                throw new BeansException("No bean.");
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                this.registerSingleton(beanName, singleton);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //新注册这个bean实例
        }
        return singleton;
    }

    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }
}
