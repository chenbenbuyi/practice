package com.chenbenbuyi.springframework.ioc3.beans.factory.support;

import com.chenbenbuyi.springframework.ioc3.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @date 2023/3/19 15:47
 * @Description 默认单例bean注册实现
 */

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //容器中存放所有bean的名称的列表
    protected List<String> beanNames = new ArrayList<>();
    /**
     * Synchronization on a non-final field
     * 示例代码中，给了这样的提示，表示同步作用于了非final的变量身上
     * 对于synchronized关键字来说，如果加锁的对象是一个可变的对象，那么当这个变量的引用发生了改变，不同的线程可能锁定不同的对象，进而导致看起来加锁的对象实际上存在线程安全问题
     */
//    protected Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
    protected final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);


    public void registerSingleton(String beanName, Object singletonObject) {
        // 确保在多线程并发的情况下，我们仍然能安全地实现对单例 Bean 的管理
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    public boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }

    public String[] getSingletonNames() {
        return this.beanNames.toArray(new String[0]);
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjects) {
            this.beanNames.remove(beanName);
            this.singletonObjects.remove(beanName);
        }
    }
}