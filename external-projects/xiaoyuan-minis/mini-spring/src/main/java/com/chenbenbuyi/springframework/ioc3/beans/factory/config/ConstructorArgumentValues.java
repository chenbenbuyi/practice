package com.chenbenbuyi.springframework.ioc3.beans.factory.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2023/3/19 19:53
 * @Description 为了更贴近Spring,将 ArgumentValues 更名为ConstructorArgumentValues
 */

public class ConstructorArgumentValues {
    private final List<ConstructorArgumentValue> constructorArgumentValueList = new ArrayList<>();

    public ConstructorArgumentValues() {
    }

    public void addArgumentValue(ConstructorArgumentValue constructorArgumentValue) {
        this.constructorArgumentValueList.add(constructorArgumentValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        ConstructorArgumentValue constructorArgumentValue = this.constructorArgumentValueList.get(index);
        return constructorArgumentValue;
    }

    public int getArgumentCount() {
        return (this.constructorArgumentValueList.size());
    }

    public boolean isEmpty() {
        return (this.constructorArgumentValueList.isEmpty());
    }
}