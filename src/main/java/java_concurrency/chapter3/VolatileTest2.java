package java_concurrency.chapter3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @date: 2020/3/24 13:06
 * @author: chen
 * @desc: 可见性测试2
 */
@Slf4j
public class VolatileTest2 {

    private static  boolean flag = false;
    private static  int i = 0;

    public static void main(String[] args)  {

        new Thread(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                flag=true;
                System.out.println("flag 修改为 true");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        while (!flag){
            i++;
            /**
             * 因为打印语句的存在，该测试代码并不是无限循环。主流的说法是，因为打印语句println的方法加了同步关键字。所以会同步flag的值，但真的是这样的吗？ 这也很好验证，用其它方式打印，比如日志的api等
             *  源码中的写法是 synchronized (this)，而this是指的PrintStream对象,所以，该同步只是同步了打印，并不能同步同步块之外的变量，也就是说，flag 的在多线程之间的可见性并不是因为同步而产生的，那是什么原因呢？
             *  实际上，JVM 本身是会尽力保证内存的可见性，即便这个变量没有加同步关键字。换句话说，只要 CPU 有时间，JVM 会尽力去保证变量值的更新。
             *  这种更新与 volatile 关键字的不同在于，volatile 关键字会强制的保证线程的可见性。而不加这个关键字，JVM 也会尽力去保证可见性，但是如果 CPU 一直有其他的事情在处理，它也没办法。
             *  最开始的代码，一直处于循环中，CPU 处于一直被饱受占用的时候，这个时候 CPU 没有时间，JVM 也不能强制要求 CPU 分点时间去取最新的变量值。而加了 System.out.println 之后，由于内部代码的同步关键字的存在，导致程序的输出其实是比较耗时的。这个时候CPU就有可能有时间去保证内存的可见性，于是while循环可以被终止。
             *   作为验证，可以自己在循环中尝试休眠一段时间，看效果
             */
            log.info("我在循环 i="+i);
//            System.out.println("我在循环 i="+i);
        }
        System.out.println("程序结束，i="+i);
    }
}
