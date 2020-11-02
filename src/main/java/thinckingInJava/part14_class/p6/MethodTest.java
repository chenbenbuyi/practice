package thinckingInJava.part14_class.p6;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.comm.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author chen
 * @date 2020/9/28 23:16
 * @Description 通过反射打印类的所有方法、构造器信息
 */
@Slf4j
public class MethodTest {
    public static void main(String[] args) {
        Class<User> objectClass = User.class;
        Method[] methods = objectClass.getMethods();
        for (Method method : methods) {
            log.info(method.toString());
        }

        Constructor<?>[] constructors = objectClass.getConstructors();
        log.info("==========================================");
        for (Constructor constructor : constructors) {
            log.info(constructor.toString());
        }
    }
}
