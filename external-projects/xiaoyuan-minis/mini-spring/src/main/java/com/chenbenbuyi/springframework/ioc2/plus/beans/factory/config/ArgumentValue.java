package com.chenbenbuyi.springframework.ioc2.plus.beans.factory.config;

import lombok.Data;

/**
 * @author chen
 * @date 2023/3/19 19:50
 * @Description  可以参见以下配置节点来理解类定义
    <beans>
        <bean id="user" class="com.minis.test.User">
            <constructor-arg type="String" name="name" value="abc"/>
            <constructor-arg type="int" name="level" value="3"/>
            <property type="String" name="property1" value="Someone says"/>
            <property type="String" name="property2" value="Hello World!"/>
        </bean>
    </beans>
 */


@Data
public class ArgumentValue {
    private String type;
    private String name;
    private Object value;

    public ArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ArgumentValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}