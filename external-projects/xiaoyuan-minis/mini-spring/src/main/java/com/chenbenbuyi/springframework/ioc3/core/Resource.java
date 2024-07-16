package com.chenbenbuyi.springframework.ioc3.core;

import java.util.Iterator;

/**
 * @author chen
 * @date 2023/3/18 12:33
 * @Description 外部的配置信息都当成 Resource（资源）来进行抽象
 */
public interface Resource extends Iterator<Object> {
}
