package thinckingInJava.part19_enum.p11;


import static thinckingInJava.part19_enum.p11.Outcome.*;

/**
 * @author chen
 * @date 2020/9/24 7:27
 * @Description
 */
public enum RoShamBo2 implements Competitor<RoShamBo2>{
    // 通过枚举分组 其平、输、赢是根据枚举的定义顺序，循环依次比较而来
    PAPER(DRAW, LOSE, WIN),
    SCISSORS(WIN, DRAW, LOSE),
    ROCK(LOSE, WIN, DRAW);
    private Outcome vPAPER, vSCISSORS, vROCK;

    // 通过构造器初始化每个枚举实例 而每个枚举实例中的vPAPER, vSCISSORS, vROCK 对应的就是上面枚举中的罗列的值
    RoShamBo2(Outcome paper, Outcome scissors, Outcome rock) {
        this.vPAPER = paper;
        this.vSCISSORS = scissors;
        this.vROCK = rock;
    }

    public Outcome compete(RoShamBo2 it) {
        switch (it) {
            default:
            case PAPER:
                return vPAPER;
            case ROCK:
                return vROCK;
            case SCISSORS:
                return vSCISSORS;
        }
    }



    public static void main(String[] args) {
        RoShamBo.play(RoShamBo2.class,20);
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
}
