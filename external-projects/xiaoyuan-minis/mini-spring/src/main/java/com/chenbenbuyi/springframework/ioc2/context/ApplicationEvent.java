package com.chenbenbuyi.springframework.ioc2.context;

import java.util.EventObject;

/**
 * @author chen
 * @date 2023/3/19 16:59
 * @Description 事件监听 参见源码，Spring的事件机制也是继承了Java原生的事件模型（Java 工具包内的 EventObject）
 */

public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 14345453544L;

    public ApplicationEvent(Object arg0) {
        super(arg0);
    }
}