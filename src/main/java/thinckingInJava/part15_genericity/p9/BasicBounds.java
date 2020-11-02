package thinckingInJava.part15_genericity.p9;

import java.awt.*;

/**
 * @author chen
 * @date 2020/10/20 22:53
 * @Description 泛型边界
 */

public class BasicBounds {}

interface HasColor {
    Color getColor();
}

/**
 * 你可以看到，泛型的边界限制使用的是 extends 关键字，即使表示边界的是一个接口
 * 因为这里只是简单的表示边界关系，并没有实际语法中的继承或实现接口的含义
 */
class Colored<T extends HasColor> {
    T item;

    public Colored(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }

    // 因为指定了泛型边界，所以这里能够明确调用方法
    Color color() {
        return item.getColor();
    }
}


class Dimension {
    public int x, y, z;
}

/**
 *  这里表示的意思是，T 对象表示的类需要继承Dimension并且同时实现HasColor接口
 * 语法上：
 * ① 边界实际可以用 & 连接符执行一个或多个
 * ② 先类后接口
 */
class ColoredDimension<T extends Dimension & HasColor> {
    T item;

    int getX() {
        return item.x;
    }
}