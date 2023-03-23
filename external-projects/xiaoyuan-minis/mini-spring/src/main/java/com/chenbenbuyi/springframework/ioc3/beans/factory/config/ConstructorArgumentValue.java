package com.chenbenbuyi.springframework.ioc3.beans.factory.config;

import lombok.Data;

/**
 * @author chen
 * @date 2023/3/19 19:50
 * @Description 为了更贴近Spring,将 ArgumentValue 更名为ConstructorArgumentValue
 */


@Data
public class ConstructorArgumentValue {
    private String type;
    private String name;
    private Object value;

    public ConstructorArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ConstructorArgumentValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}