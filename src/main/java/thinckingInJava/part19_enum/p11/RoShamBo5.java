package thinckingInJava.part19_enum.p11;

import static thinckingInJava.part19_enum.p11.Outcome.*;

/**
 * @author chen
 * @date 2020/9/24 23:53
 * @Description 利用二维数组来实现两路分发
 */
public enum RoShamBo5 implements Competitor<RoShamBo5> {
    PAPER,
    SCISSORS,
    ROCK;

    private static Outcome[][] table = {
            {DRAW, LOSE, WIN},
            {WIN, DRAW, LOSE},
            {LOSE, WIN, DRAW},
    };

    @Override
    public Outcome compete(RoShamBo5 other) {
        return table[this.ordinal()][other.ordinal()];
    }


    @Override
    public String toString() {
        switch (this) {
            default:
            case SCISSORS:
                return "剪刀";
            case PAPER:
                return "布";
            case ROCK:
                return "石头";
        }
    }

    public static void main(String[] args) {
        RoShamBo.play(RoShamBo4.class, 20);
    }
}
