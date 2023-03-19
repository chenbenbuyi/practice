package com.chenbenbuyi.springframework.ioc.plus.context.support;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.ioc.beans.factory.config.BeanDefinition;
import com.chenbenbuyi.springframework.ioc.plus.beans.factory.BeanFactory;
import com.chenbenbuyi.springframework.ioc.plus.beans.factory.SimpleBeanFactory;
import com.chenbenbuyi.springframework.ioc.plus.beans.factory.xml.XmlBeanDefinitionReader;
import com.chenbenbuyi.springframework.ioc.plus.core.ClassPathXmlResource;
import com.chenbenbuyi.springframework.ioc.plus.core.Resource;

/**
 * @author chen
 * @date 2023/3/19 14:57
 * @Description 此处要理解，虽然ClassPathXmlApplicationContext和SimpleBeanFactory都继承了BeanFactory，感觉SimpleBeanFactory有些多余，但实际ClassPathXmlApplicationContext只是一个集大成者的上下文对象，不能简单的看做BeanFactory的一个实现。
 * 在此处的实现中，BeanFactory的功能实现实际上是SimpleBeanFactory，该上下文对象只做集成——我虽然是BeanFactory，但是我不是实际作用的BeanFactory，组合进来的才是真正作用的BeanFactory
 */
public class ClassPathXmlApplicationContext implements BeanFactory {


    private BeanFactory beanFactory;

    //context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
    public ClassPathXmlApplicationContext(String fileName) {
        // 从外部xml中加载成Resource
        Resource resource = new ClassPathXmlResource(fileName);
        // 解析转换为BeanDefinition，然后注册到BeanFactory中，因为要通过BeanFactory注册BeanDefinition，所以其构造参数中有BeanFactory
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    //context再对外提供一个getBean，底下就是调用的BeanFactory对应的方法
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }

}