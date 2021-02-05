package java_concurrency.chapter5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * @author chen
 * @date 2021/1/19 6:43
 * @Description 同步容器的复合操作不是线程安全的
 */
public class SychronizedContainerTest {

    /**
     * 当对同步容器进行迭代操作期间有增减元素，依然会表现出fast-fail的行为。因为在同步容器(简单理解为通过synchronized来实现同步的容器)的设计中，并没有后考虑并发修改的问题。
     *  这与我们常见的 ArrayList 等的快速失败机制是一样的。
     *  所以记好了，不会快速失败的是并发容器。那么这就有疑问了，快速失败是怎么产生的，并发容器的快速失败又是如何解决的？
     */
    @Test
    public void test1() {
//        Vector<Integer> integers = new Vector<>();
//        List<Integer> integers = Collections.synchronizedList(new ArrayList<>());
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()){
            if(30==iterator.next()){
                iterator.remove();
            }
        }
//        for (Integer i : integers) {
//            if (i > 20) {
//                integers.remove(i);
//            }
//        }
    }





}
