package com.chenbenbuyi.springframework.ioc2.plus.context.support;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.ioc2.plus.beans.factory.BeanFactory;
import com.chenbenbuyi.springframework.ioc2.plus.beans.factory.SimpleBeanFactory;
import com.chenbenbuyi.springframework.ioc2.plus.beans.factory.xml.XmlBeanDefinitionReader;
import com.chenbenbuyi.springframework.ioc2.plus.context.ApplicationEvent;
import com.chenbenbuyi.springframework.ioc2.plus.context.ApplicationEventPublisher;
import com.chenbenbuyi.springframework.ioc2.plus.core.ClassPathXmlResource;
import com.chenbenbuyi.springframework.ioc2.plus.core.Resource;

/**
 * @author chen
 * @date 2023/3/19 16:30
 * @Description
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        Resource res = new ClassPathXmlResource(fileName);
        SimpleBeanFactory bf = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(res);
        this.beanFactory = bf;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
    }

    @Override
    public boolean isSingleton(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        // TODO Auto-generated method stub
        return null;
    }

}