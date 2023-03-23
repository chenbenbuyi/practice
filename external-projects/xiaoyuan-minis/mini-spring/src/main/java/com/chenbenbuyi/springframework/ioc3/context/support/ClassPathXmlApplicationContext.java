package com.chenbenbuyi.springframework.ioc3.context.support;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.ioc3.beans.factory.BeanFactory;
import com.chenbenbuyi.springframework.ioc3.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.chenbenbuyi.springframework.ioc3.beans.factory.config.AutowireCapableBeanFactory;
import com.chenbenbuyi.springframework.ioc3.beans.factory.xml.XmlBeanDefinitionReader;
import com.chenbenbuyi.springframework.ioc3.context.ApplicationEvent;
import com.chenbenbuyi.springframework.ioc3.context.ApplicationEventPublisher;
import com.chenbenbuyi.springframework.ioc3.core.ClassPathXmlResource;
import com.chenbenbuyi.springframework.ioc3.core.Resource;

/**
 * @author chen
 * @date 2023/3/19 16:30
 * @Description 引入的成员变量由 SimpleBeanFactory 改为新建的 AutowireCapableBeanFactory，并在构造函数里增加上下文刷新逻辑
 * <p>
 * 1/启动 ClassPathXmlApplicationContext 容器，执行 refresh()。
 * 2/在 refresh 执行过程中，调用 registerBeanPostProcessors()，往 BeanFactory 里注册 Bean 处理器，如 AutowiredAnnotationBeanPostProcessor。
 * 3/执行 onRefresh()， 执行 AbstractBeanFactory 的 refresh() 方法。
 * 4/AbstractBeanFactory 的 refresh() 获取所有 Bean 的定义，执行 getBean() 创建 Bean 实例。
 * 5/getBean() 创建完 Bean 实例后，调用 Bean 处理器并初始化。
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    AutowireCapableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }


    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            try {
                refresh();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }


    // 注册 BeanPostProcessor，这样 BeanFactory 里就有解释注解的处理器了，然后在 getBean() 的过程中使用它
    public void refresh() throws BeansException, IllegalStateException {
        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(this.beanFactory);
        // Initialize other special beans in specific context subclasses.
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }


    private void onRefresh() {
        this.beanFactory.refresh();
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