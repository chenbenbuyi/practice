package daily.extra;

import lombok.extern.slf4j.Slf4j;

/**
 * @date: 2020/12/8 9:27
 * @author: chen
 * @desc: 关于Java 取整函数的用法测试
 */

@Slf4j
public class MathTest {


    public static void main(String[] args) {
        /**
         *  Math.floor  floor 地板，对一个浮点数向下取整，返回小于或等于参数的最大(最接近负无穷大)数学整数的double值。注意，返回的数据类型依然是 double值
         *  Math.ceil  ceil 天花板，对一个浮点数向上取整，返回大于或等于参数的最小(最接近正无穷大)数学整数的double值。
         *  如果参数值已经等于数学整数，或为NaN、正负0、无穷大等、结果与参数相同。
         */
        log.info("NaN向下取整{}", Math.floor(Double.NaN));
        log.info("正无穷大向下取整{}", Math.floor(1 / 0.0));
        log.info("参数是无穷大：{}，正穷大向下取整{}", Double.isInfinite(1 / 0.0), Math.floor(Double.POSITIVE_INFINITY));
        log.info("-0向下取整{}", Math.floor(-0));
        log.info("-2.3向下取整{}", Math.floor(-2.3));
        log.info("2.3向下取整{}", Math.floor(2.3));
        log.info("-2.3向上取整{}", Math.ceil(-2.3));
        log.info("2.3向上取整{}", Math.ceil(2.3));

        /**
         *   Math.rint 返回最接近参数的整数浮点数 对于中间数如 0.5 的情况，会舍去而不是收入
         */
        log.info("正数2.3最接近整数{}", Math.rint(2.3));
        log.info("负数-2.3最接近整数{}", Math.rint(-2.3));
        log.info("正数2.5最接近整数{}", Math.rint(2.5));
        log.info("负数-2.5最接近整数{}", Math.rint(-2.5));

        /**
         *   Math.round 四舍五入
         *   如果参数是NaN，结果为0。
         *   如果参数为负无穷大或小于或等于Long.MIN_VALUE的值，则结果等于Long.MIN_VALUE的值。
         *      但实际测试显示，Double和Float表示的无穷数舍入后是不同的。Float由于只能表示32位，所以其舍入后结果是Integer.MAX_VALUE
         *   如果参数为正无穷大或大于或等于Long.MAX_VALUE的值，则结果等于Long.MAX_VALUE的值。
         */
        log.info("正数2.3四舍五入{}", Math.round(2.3));
        log.info("负数2.3四舍五入{}", Math.round(-2.3));
        log.info("正数2.5四舍五入{}", Math.round(2.5));
        /**
         *  负数情况下不是简单的“四舍五入”，而要考虑XXX.5的情况：
         *      小数位上的数字小于等于5，舍弃小数位，保留整数位数；小数位上的数字大于5，舍弃小数位且加-1；
         *   这里顺便提一下，自己通常构造的除数为 0 的异常，其实只对整形有用，若是浮点型数据，是不存才除数为 0 异常的
         *      尝试下面代码：  double i = 1d/0   和  double i = 1/0;
         */
        log.info("负数2.5四舍五入{}", Math.round(-2.5));
        log.info("负数2.6四舍五入{}", Math.round(-2.6));
        log.info("NaN四舍五入{}", Math.round(Float.NaN));
        log.info("正无穷大四舍五入{},Long 的最大值{}", Math.round(1/0.0),Long.MAX_VALUE);
        log.info("正无穷大四舍五入{},Long 的最大值{}", Math.round(Double.POSITIVE_INFINITY),Long.MAX_VALUE);
        log.info("正无穷大四舍五入{},Long 的最大值{}", Math.round(Float.POSITIVE_INFINITY),Long.MAX_VALUE);
    }
}
