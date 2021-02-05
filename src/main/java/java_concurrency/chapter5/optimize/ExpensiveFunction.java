package java_concurrency.chapter5.optimize;

import java.math.BigInteger;

/**
 * @author chen
 * @date 2021/1/24 22:41
 * @Description  该类是一个业务基准类，方法执行一个开销很大的计算，因为方法计算开销大，所以后续会通过缓存来进行优化性能；
 *  因为增加了缓存，也就引出优化过程中的一些线程安全和活跃性等问题
 */
public class ExpensiveFunction implements Computable<String,BigInteger> {
    // 在方法实现的时候，子类抛出的异常不能大于父类的异常——当然，你完全可以完全不抛出异常！
    @Override
    public BigInteger compute(String arg) {
        return new BigInteger(arg);
    }
}
