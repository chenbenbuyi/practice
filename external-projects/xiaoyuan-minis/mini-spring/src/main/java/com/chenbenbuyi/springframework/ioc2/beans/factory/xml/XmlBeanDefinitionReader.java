package com.chenbenbuyi.springframework.ioc2.beans.factory.xml;


import com.chenbenbuyi.springframework.ioc2.beans.factory.SimpleBeanFactory;
import com.chenbenbuyi.springframework.ioc2.beans.factory.config.BeanDefinition;
import com.chenbenbuyi.springframework.ioc2.core.Resource;
import org.dom4j.Element;

/**
 * @author chen
 * @date 2023/3/19 15:11
 * @Description 将解析的XML 转换为 BeanDefinition
 */


public class XmlBeanDefinitionReader {
    private SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
