package com.chenbenbuyi.springframework.ioc3.beans.factory.config;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.ioc3.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2023/3/20 22:47
 * @Description
 */


public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 用一个列表 beanPostProcessors 记录所有的 Bean 处理器，这样可以按照需求注册若干个不同用途的处理器，然后调用处理器
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }


    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            beanProcessor.setBeanFactory(this);
            Object res = beanProcessor.postProcessBeforeInitialization(result, beanName);
            if (res == null) {
                return result;
            }
            result = res;
        }
        return result;
    }

    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            Object res = beanProcessor.postProcessAfterInitialization(result, beanName);
            if (res == null) {
                return result;
            }
            result = res;
        }
        return result;
    }

}