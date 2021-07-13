package daily.circular;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2021/7/13 16:55
 * @author: chen
 * @desc: 模拟循环依赖解决
 */
public class CircularTest {

    private static Map<String, Object> cacheMap = new HashMap<>(2);

    public static void main(String[] args) throws Exception {
        // 假装扫描出来的对象
        Class[] classes = {A.class, B.class};
        // 假装项目初始化实例化所有bean
        for (Class aClass : classes) {
            getBean(aClass);
        }
        // check
        System.out.println(getBean(B.class).getA() == getBean(A.class));
        System.out.println(getBean(A.class).getB() == getBean(B.class));

    }

    private static <T> T getBean(Class<T> clz) throws Exception {
        String beanName = clz.getSimpleName().toLowerCase();
        Object instance;
        // 如果已经是一个bean，则直接返回
        if (cacheMap.containsKey(beanName)) {
            return (T) cacheMap.get(beanName);
        }
        instance = clz.getDeclaredConstructor().newInstance();
        cacheMap.put(beanName, instance);
        // 实例化属性对象
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldClass = field.getType();
            String fieldBeanName = fieldClass.getSimpleName().toLowerCase();
            field.set(instance, cacheMap.containsKey(fieldBeanName) ? cacheMap.get(fieldBeanName) : getBean(fieldClass));
        }
        return (T) instance;
    }
}
