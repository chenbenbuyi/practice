package thinckingInJava.part15_genericity.p3;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part15_genericity.p3.clz.*;

import java.util.Iterator;
import java.util.Random;

/**
 * @author chen
 * @date 2020/10/11 21:47
 * @Description
 */
@Slf4j
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {

    private Class[] types = {Latte.class, Mocha.class, Cappuccino.class, Americano.class, Breve.class};
    private Random rand = new Random(47);
    private int size = 0;

    public CoffeeGenerator() {
    }

    public CoffeeGenerator(int size) {
        this.size = size;
    }

    /**
     * 实现 Iterable接口后，主要为了foreach 时通过迭代器遍历 可以查看其反编译后的代码
     */
    @Override
    public Iterator<Coffee> iterator() {
        return new CoffeeIterator();
    }

    @Override
    public Coffee next() {
        try {
            return (Coffee) types[rand.nextInt(types.length)].newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class CoffeeIterator implements Iterator<Coffee> {

        int count = size;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Coffee next() {
            count--;
            // 这里用外部类引用指代的是外部类的next方法
            return CoffeeGenerator.this.next();
        }
    }

    public static void main(String[] args) {
        CoffeeGenerator coffees = new CoffeeGenerator();
        for (int i = 0; i < 5; i++) {
            log.info(coffees.next().toString());
        }
        for (Coffee coffee : new CoffeeGenerator(5)) {
            log.info(coffee.toString());
        }
    }
}
