package com.chenbenbuyi.springframework.ioc.context.support;

/**
 * @author chen
 * @date 2023/3/19 14:50
 * @Description
 */

import com.chenbenbuyi.springframework.ioc.beans.factory.config.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @date 2023/3/18 11:58
 * @Description 在类路径下读取xml文件获取bean定义并实例化为的上下文对象。
 * 其主要职能如下：
 * 1、解析 XML 文件中的内容。加载解析的内容，构建 BeanDefinition。
 * 2、读取 BeanDefinition 的配置信息，实例化 Bean
 * 3、然后把它注入到 BeanFactory 容器中。
 * <p>
 * 该类存在的问题：承担了太多的功能，这并不符合我们常说的对象单一功能的原则。更优的方式就是将每种功能都抽离出去，便于单功能的扩展，而该类通过组合的方式实现功能
 * 参见{@link com.chenbenbuyi.springframework.ioc.plus.context.support.ClassPathXmlApplicationContext}
 */

public class ClassPathXmlApplicationContext {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    /**
     * 构造器获取外部配置，解析出Bean的定义，形成内存映像
     */
    public ClassPathXmlApplicationContext(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();
    }

    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlPath =
                    this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            //对配置文件中的每一个<bean>，进行处理
            for (Element element : (List<Element>) rootElement.elements()) {
                //获取Bean的基本信息
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
                //将Bean的定义存放到beanDefinitions
                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //利用反射创建Bean实例，并存储在singletons中
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                singletons.put(beanDefinition.getId(),
                        Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //这是对外的一个方法，让外部程序从容器中获取Bean实例，会逐步演化成核心方法
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}