package thinckingInJava.part19_enum.p11;

/**
 * @author chen
 * @date 2020/9/23 23:33
 * @Description
 */
public enum Outcome {
    WIN("赢"), LOSE("输"), DRAW("平局");
    private String desc;

    Outcome(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
