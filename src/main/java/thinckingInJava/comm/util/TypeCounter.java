package thinckingInJava.comm.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author chen
 * @date 2020/9/27 23:35
 * @Description 类型计数通用工具
 */
@Slf4j
public class TypeCounter extends HashMap<Class<?>, Integer> {
    private Class<?> baseType;

    public TypeCounter(Class<?> baseType) {
        this.baseType = baseType;
    }

    public void count(Object obj) {
        Class<?> type = obj.getClass();
        // 确定由此类对象表示的类或接口是否与由指定的Class 类表示的类或接口相同或是超类或 类接口。
        if (!baseType.isAssignableFrom(type)) {
            throw new RuntimeException(obj + " 的类型" + type + " 不匹配，应该为：" + baseType + " 的类型或子类型");
        }
        countClass(type);

    }

    private void countClass(Class<?> type) {
        Integer quantity = get(type);
        put(type, quantity == null ? 1 : quantity + 1);
        Class<?> superclass = type.getSuperclass();
        log.info("superclass："+superclass);
        // 通过递归对其具体类型和基础类型都进行了计数
        if (superclass != null && baseType.isAssignableFrom(superclass)) {
            countClass(superclass);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<Class<?>, Integer> entry : entrySet()) {
            sb.append(entry.getKey().getSimpleName());
            sb.append("=").append(entry.getValue());
            sb.append(", ");
        }
        // 指定索引删除
        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        return sb.toString();
    }
}
