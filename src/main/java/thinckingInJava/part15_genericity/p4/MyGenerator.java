package thinckingInJava.part15_genericity.p4;

import thinckingInJava.part15_genericity.p3.Generator;

/**
 * @author chen
 * @date 2020/10/11 23:54
 *  观察发现，如果只是泛型的体系扩展，不指定具体类型，也就是子类没有具化，则声明的类也要执行泛型结构，
 *  而如果声明类的时候已经有指定具体的泛型参数，则声明类上不用再指定，我怀疑是方式两种相同的写法，指定的泛型参数会被当成泛型声明
 */
public class MyGenerator<T> implements Generator<T> {
    @Override
    public T next() {
        return null;
    }
}

class MyGenerator2 implements Generator<String>{

    @Override
    public String next() {
        return null;
    }
}
