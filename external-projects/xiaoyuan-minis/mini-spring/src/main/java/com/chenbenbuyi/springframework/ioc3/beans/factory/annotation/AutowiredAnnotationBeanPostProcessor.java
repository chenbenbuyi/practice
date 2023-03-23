package com.chenbenbuyi.springframework.ioc3.beans.factory.annotation;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.ioc3.beans.factory.BeanFactory;
import com.chenbenbuyi.springframework.ioc3.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * @author chen
 * @date 2023/3/20 22:45
 * @Description Autowired 的处理类
 * <p>
 * 基础实现：通过反射获取所有标注了注解 Autowired 的成员变量，将其初始化为一个Bean,然后在注入属性值
 */

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        Object result = bean;

        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            //对每一个属性进行判断，如果带有@Autowired注解则进行处理
            for (Field field : fields) {
                boolean isAutowired =
                        field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    //根据属性名查找同名的bean
                    String fieldName = field.getName();
                    Object autowiredObj = this.beanFactory.getBean(fieldName);
                    //设置属性值，完成注入
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                        System.out.println("autowire " + fieldName + " for bean " + beanName);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
