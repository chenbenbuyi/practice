package thinckingInJava.part19_enum.p11;

import java.util.EnumMap;

import static thinckingInJava.part19_enum.p11.Outcome.*;

/**
 * @author chen
 * @date 2020/9/24 23:34
 * @Description
 */
public enum RoShamBo4 implements Competitor<RoShamBo4> {
    PAPER,
    SCISSORS,
    ROCK;

    static EnumMap<RoShamBo4, EnumMap<RoShamBo4, Outcome>> table = new EnumMap<>(RoShamBo4.class);

    static {
        for (RoShamBo4 roShamBo4 : RoShamBo4.values())
            table.put(roShamBo4, new EnumMap<>(RoShamBo4.class));
        initRow(PAPER, DRAW, LOSE, WIN);
        initRow(SCISSORS, WIN, DRAW, LOSE);
        initRow(ROCK, LOSE, WIN, DRAW);
    }

    static void initRow(RoShamBo4 it, Outcome o1, Outcome o2, Outcome o3) {
        EnumMap<RoShamBo4, Outcome> roShamBo4OutcomeEnumMap = RoShamBo4.table.get(it);
        roShamBo4OutcomeEnumMap.put(RoShamBo4.PAPER, o1);
        roShamBo4OutcomeEnumMap.put(RoShamBo4.SCISSORS, o2);
        roShamBo4OutcomeEnumMap.put(RoShamBo4.ROCK, o3);
    }

    @Override
    public Outcome compete(RoShamBo4 roShamBo4) {
        return table.get(this).get(roShamBo4);
    }

    @Override
    public String toString() {
        switch(this){
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
