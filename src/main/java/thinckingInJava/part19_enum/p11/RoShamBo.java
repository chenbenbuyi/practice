package thinckingInJava.part19_enum.p11;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part19_enum.p7.Enums;

/**
 * @author chen
 * @date 2020/9/24 7:40
 * @Description
 */
@Slf4j
public class RoShamBo {
    public static <T extends Competitor<T>> void match(T a, T b) {
        log.info(a + "对" + b + "：" + a.compete(b));
    }

    /**
     * 此处的方法语法结构指明 类型参数必须同时是枚举和Competitor类型
     */
    public static <T extends Enum<T> & Competitor<T>> void play(Class<T> clz, int size) {
        for (int i = 0; i < size; i++) {
            match(Enums.random(clz), Enums.random(clz));
        }
    }
}
