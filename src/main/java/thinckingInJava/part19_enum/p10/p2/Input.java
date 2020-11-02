package thinckingInJava.part19_enum.p10.p2;

import java.util.Random;

/**
 * @author chen
 * @date 2020/9/20 22:35
 *  定义各种输入的枚举
 */
public enum Input {
    NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100), TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),
    ABORT_TRANSACTION {
        public int amount() {
            throw new RuntimeException("中止");
        }
    },
    STOP {
        public int amount() {
            throw new RuntimeException("终止");
        }
    };

    private int value;

    int amount() {
        return value;
    }

    /**
     * 枚举可以根据其自描述信息需要，定义不同的构造器。当然，其构造器默认都是私有的
     */
    Input() {
    }

    Input(int value) {
        this.value = value;
    }

    static Random random = new Random(47);

    public static Input randomSelection() {
        return values()[random.nextInt(values().length - 1)];
    }
}
