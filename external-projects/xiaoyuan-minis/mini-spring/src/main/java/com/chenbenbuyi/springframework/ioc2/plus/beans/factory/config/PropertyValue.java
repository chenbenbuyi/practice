package com.chenbenbuyi.springframework.ioc2.plus.beans.factory.config;

import lombok.Data;

/**
 * @author chen
 * @date 2023/3/19 19:52
 * @Description
 */


@Data
public class PropertyValue {
    private final String type;
    private final String name;
    private final Object value;

    public PropertyValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }
}