package com.chenbenbuyi.springframework.ioc2;

import com.chenbenbuyi.springframework.common.exception.BeansException;
import com.chenbenbuyi.springframework.common.service.HelloService;
import com.chenbenbuyi.springframework.ioc2.plus.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @author chen
 * @date 2023/3/18 12:08
 * @Description
 */
public class ClassPathXmlApplicationContextTest {


    @Test
    public void testClassPathXmlApplicationContext() throws BeansException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("ioc2-beans.xml");
        HelloService helloService = (HelloService) classPathXmlApplicationContext.getBean("helloService");
        helloService.sayHello();
    }


    @Test
    public void testClassPathXmlApplicationContextPlus() throws BeansException {
        com.chenbenbuyi.springframework.ioc2.plus2.context.support.ClassPathXmlApplicationContext  classPathXmlApplicationContext = new com.chenbenbuyi.springframework.ioc2.plus2.context.support.ClassPathXmlApplicationContext ("ioc2-beans.xml");
        HelloService helloService = (HelloService) classPathXmlApplicationContext.getBean("helloService");
        helloService.sayHello();
    }
}
