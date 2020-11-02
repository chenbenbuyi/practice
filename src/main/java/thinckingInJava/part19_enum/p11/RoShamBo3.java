package thinckingInJava.part19_enum.p11;

/**
 * @author chen
 * @date 2020/9/24 23:25
 * @Description 使用常量相关的方法为每个枚举实例提供不同的方法实现 这种方式的分发应用就更好理解
 */
public enum RoShamBo3 implements Competitor<RoShamBo3> {
    PAPER {
        @Override
        public Outcome compete(RoShamBo3 it) {
            switch (it) {
                default: // 编译需要
                case PAPER:
                    return Outcome.DRAW;
                case ROCK:
                    return Outcome.WIN;
                case SCISSORS:
                    return Outcome.LOSE;
            }
        }
    },
    SCISSORS {
        @Override
        public Outcome compete(RoShamBo3 it) {
            switch (it) {
                default:
                case PAPER:
                    return Outcome.WIN;
                case ROCK:
                    return Outcome.LOSE;
                case SCISSORS:
                    return Outcome.DRAW;
            }
        }
    },
    ROCK {
        @Override
        public Outcome compete(RoShamBo3 it) {
            switch (it) {
                default:
                case PAPER:
                    return Outcome.LOSE;
                case ROCK:
                    return Outcome.DRAW;
                case SCISSORS:
                    return Outcome.WIN;
            }
        }
    };


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

    public abstract Outcome compete(RoShamBo3 it);

    public static void main(String[] args) {
        RoShamBo.play(RoShamBo3.class, 20);
    }


}
