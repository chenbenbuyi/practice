package thinckingInJava.part14_class.p3;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @date 2020/9/28 20:52
 * @Description
 */
class Base {
}

class Derived extends Base {
}

@Slf4j
public class FamilyVsExactType {
    static void test(Object o) {
        log.info("o 的类型：" + o.getClass());
        log.info("o instanceof Base: " + (o instanceof Base));
        log.info("o instanceof Derived: " + (o instanceof Derived));
        // isInstance 和 instanceof 是等价的
        log.info("Base.isInstance(o): " + (Base.class.isInstance(o)));
        log.info("Derived.isInstance(o): " + (Derived.class.isInstance(o)));
        log.info("o.getClass()==Base.class: " + (o.getClass() == Base.class));
        log.info("o.getClass()==Derived.class: " + (o.getClass() == Derived.class));
        log.info("o.getClass().equals(Base.class) " + (o.getClass().equals(Base.class)));
        log.info("o.getClass().equals(Derived.class) " + (o.getClass().equals(Derived.class)));
    }

    public static void main(String[] args) {
//        test(new Base());
        test(new Derived());
    }
}