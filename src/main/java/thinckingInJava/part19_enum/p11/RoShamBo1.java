package thinckingInJava.part19_enum.p11;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author chen
 * @date 2020/9/23 23:29
 * @Description 两路分发：优雅的解决在一个方法中判断多个对象类型 ，比如示例中的match
 * 不过，代码还是很冗长的，枚举除了表示结果状态，其实并没有起到真正的分发作用
 */
@Slf4j
public class RoShamBo1 {
    static final int SIZE = 20;
    private static Random rand = new Random(47);

    public static Item newItem() {
        switch (rand.nextInt(3)) {
            default: // 没有此项，编译不过
            case 0:
                return new Scissors();
            case 1:
                return new Paper();
            case 2:
                return new Rock();
        }
    }

    public static void match(Item a, Item b) {
        log.info(a + "对" + b + "：" + a.compele(b));
    }

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            match(newItem(), newItem());
        }
    }
}

interface Item {
    Outcome compele(Item it);

    Outcome eval(Paper p);

    Outcome eval(Scissors s);

    Outcome eval(Rock r);
}

class Paper implements Item {

    @Override
    public Outcome compele(Item it) {
        return it.eval(this);
    }

    @Override
    public Outcome eval(Paper p) {
        return Outcome.DRAW;
    }

    @Override
    public Outcome eval(Scissors s) {
        return Outcome.WIN;
    }

    @Override
    public Outcome eval(Rock r) {
        return Outcome.LOSE;
    }

    @Override
    public String toString() {
        return "布";
    }
}


class Scissors implements Item {

    @Override
    public Outcome compele(Item it) {
        return it.eval(this);
    }

    @Override
    public Outcome eval(Paper p) {
        return Outcome.LOSE;
    }

    @Override
    public Outcome eval(Scissors s) {
        return Outcome.DRAW;
    }

    @Override
    public Outcome eval(Rock r) {
        return Outcome.WIN;
    }

    @Override
    public String toString() {
        return "剪刀";
    }
}

class Rock implements Item {

    @Override
    public Outcome compele(Item it) {
        return it.eval(this);
    }

    @Override
    public Outcome eval(Paper p) {
        return Outcome.WIN;
    }

    @Override
    public Outcome eval(Scissors s) {
        return Outcome.LOSE;
    }

    @Override
    public Outcome eval(Rock r) {
        return Outcome.DRAW;
    }

    @Override
    public String toString() {
        return "石头";
    }
}