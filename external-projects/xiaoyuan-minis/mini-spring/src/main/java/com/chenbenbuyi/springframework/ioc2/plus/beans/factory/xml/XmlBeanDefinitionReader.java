package com.chenbenbuyi.springframework.ioc2.plus.beans.factory.xml;


import com.chenbenbuyi.springframework.ioc2.plus.beans.factory.SimpleBeanFactory;
import com.chenbenbuyi.springframework.ioc2.plus.beans.factory.config.*;
import com.chenbenbuyi.springframework.ioc2.plus.core.Resource;
import org.dom4j.Element;

import java.util.List;

/**
 * @author chen
 * @date 2023/3/19 15:11
 * @Description 解析<property> 和<constructor-arg> 两个标签
 */


public class XmlBeanDefinitionReader {
    SimpleBeanFactory bf;

    public XmlBeanDefinitionReader(SimpleBeanFactory bf) {
        this.bf = bf;
    }

    public void loadBeanDefinitions(Resource res) {
        while (res.hasNext()) {
            Element element = (Element) res.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");

            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

            //handle properties
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                PVS.addPropertyValue(new PropertyValue(pType, pName, pValue));
            }
            beanDefinition.setPropertyValues(PVS);
            //end of handle properties

            //get constructor
            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues AVS = new ArgumentValues();
            for (Element e : constructorElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                AVS.addArgumentValue(new ArgumentValue(pType, pName, pValue));
            }
            beanDefinition.setConstructorArgumentValues(AVS);
            //end of handle constructor

            this.bf.registerBeanDefinition(beanID, beanDefinition);
        }

    }


}