package com.chenbenbuyi.springframework.ioc2.context.support;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.ioc2.beans.factory.BeanFactory;
import com.chenbenbuyi.springframework.ioc2.beans.factory.SimpleBeanFactory;
import com.chenbenbuyi.springframework.ioc2.beans.factory.xml.XmlBeanDefinitionReader;
import com.chenbenbuyi.springframework.ioc2.core.ClassPathXmlResource;
import com.chenbenbuyi.springframework.ioc2.core.Resource;

/**
 * @author chen
 * @date 2023/3/19 16:30
 * @Description
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    private BeanFactory beanFactory;


    //context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
    public ClassPathXmlApplicationContext(String fileName) {
        // 从外部xml中加载成Resource
        Resource resource = new ClassPathXmlResource(fileName);
        // 解析转换为BeanDefinition，然后注册到BeanFactory中，因为要通过BeanFactory注册BeanDefinition，所以其构造参数中有BeanFactory
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }


    @Override
    public Boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }


    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

}