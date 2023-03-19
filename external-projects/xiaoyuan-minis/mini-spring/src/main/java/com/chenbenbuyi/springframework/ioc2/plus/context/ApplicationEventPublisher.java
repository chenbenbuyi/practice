package com.chenbenbuyi.springframework.ioc2.plus.context;


/**
 * @author chen
 * @date 2023/3/19 16:58
 * @Description 事件发布
 */

public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}