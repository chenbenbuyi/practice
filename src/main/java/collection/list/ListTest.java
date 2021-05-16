package collection.list;

import collection.list.model.Book;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/4/3 11:03
 * @Description
 */
public class ListTest {
    private static List<Book> books = new ArrayList<>();

    static {
        books.add(new Book().setAuthor("陈本布衣").setName("Java 编程思想").setPrice(88.9));
        books.add(new Book().setAuthor("随你的便").setName("并发编程实战").setPrice(36.0));
        books.add(new Book().setAuthor("布衣之陈").setName("随便的").setPrice(59.9));
        books.add(new Book().setAuthor("苦寒").setName("你好水电费").setPrice(55.9));
        books.add(new Book().setAuthor("作者牛逼").setName("集合可以这么玩").setPrice(55.9));
        books.add(new Book().setAuthor("Jams").setName("df双方都").setPrice(22.9));
        books.add(new Book().setAuthor("aab").setName("说着玩的").setPrice(15.9));
    }


    /**
     *  克隆前提条件：
     *      1、实现标记接口 Cloneable
     *      2、重写 clone 方法[注意，clone 方法是Object中的方法]
     */
    @Test
    public void cloneTest() {
        ArrayList<Book> arrayList = (ArrayList<Book>) ListTest.books;
        Object clone = arrayList.clone();
        System.out.println(clone==arrayList);
        System.out.println(arrayList);
        System.out.println(clone);
    }


    @Test
    public void forTest() {
        for (Book book : books) {
            if("随你的便".equals(book.getAuthor())){
                books.remove(book);
            }
        }
    }

    /**
     *  fail-fast机制
     *  即使是迭代器删除，在并发修改下，一样会可能抛出并发修改异常，因为fail-fast抛出的本质就是
     *      expectedModCount != modCount;
     *   而在并发的环境下，如果没有明显的同步机制，一切皆有可能.因此，阿里巴巴编码规范中明确说明，在并发情况下，请对迭代器对象加锁
     */
    @Test
    public void iteratorTest() throws InterruptedException {
        Iterator<Book> iterator = books.iterator();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                countDownLatch.await();
                synchronized (iterator){
                    while (iterator.hasNext()){
                        Book book = iterator.next();
                        if("Jams".equals(book.getAuthor()))
                            iterator.remove();
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                countDownLatch.await();
                synchronized (iterator){
                    while (iterator.hasNext()){
                        Book book = iterator.next();
                        if("作者牛逼".equals(book.getAuthor()))
                            iterator.remove();
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                countDownLatch.await();
                while (iterator.hasNext()){
                    while (iterator.hasNext()){
                        Book book = iterator.next();
                        if("苦寒".equals(book.getAuthor()))
                            iterator.remove();
                    }
                }

            }
        }).start();
        TimeUnit.SECONDS.sleep(2);
        countDownLatch.countDown();
    }


}
