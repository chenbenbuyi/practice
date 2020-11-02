package thinckingInJava.part15_genericity.p9;

import java.awt.*;

/**
 * @author chen
 * @date 2020/10/20 23:07
 * @Description
 */
public class InheritBounds {
}

class HoldItem<T> {
    T item;

    public HoldItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}

/**
 *  翻译过来就是：
 *      T 是从类 HoldItem继承的属于HasColor类型的实例变量
 */
class Colored2<T extends HasColor> extends HoldItem<T> {

    public Colored2(T item) {
        super(item);
    }


    Color color() {
        return item.getColor();
    }
}