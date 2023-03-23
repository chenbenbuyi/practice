package com.chenbenbuyi.springframework.ioc3.beans.factory.config;

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
    private final boolean isRef;

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }
}