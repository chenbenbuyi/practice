package thinckingInJava.part17_collection.p7_queue;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author chen
 * @date 2020/11/27 23:16
 * @Description 优先级队列中的元素排序根据 Comparable 进行控制，所以队列中的元素需要实现Comparable接口。
 * 元素添加进优先级队列，其顺序将根据比较规则自动排序
 */

@Slf4j
public class MyPriorityQueue extends PriorityQueue<MyPriorityQueue.Item> {
    /**
     *  添加进优先级队列中的元素如果没有实现比较接口，那么添加元素时就会抛出异常：
     *  java.lang.ClassCastException:  xxx cannot be cast to java.lang.Comparable
     */
    static class Item implements Comparable<Item> {
        private int v;

        public Item(int v) {
            this.v = v;
        }

        @Override
        public int compareTo(Item o) {
            return Integer.compare(v, o.v);
        }

        @Override
        public String toString() {
            return "v:"+v;
        }
    }


    public static void main(String[] args) {
        Random random = new Random(47);
        MyPriorityQueue queue = new MyPriorityQueue();
        for (int i = 0; i < 10; i++) {
            Item item = new Item(random.nextInt(100));
            queue.add(item);
        }
        /**
         *  注意，直接toString或者迭代器迭代打印时你看到的结果似乎并不是根据 排序规则排序的，甚至是乱序的,why?
         *  因为该队列底层是一个二进制堆，堆结构决定了，其有序性仅存在于局部，javadoc 中有以下原语：
         *  This class and its iterator implement all of the optional  methods of the {@link Collection} and {@link Iterator} interfaces.
         *  The Iterator provided in method {@link iterator()} is not guaranteed to traverse the elements of
         * the priority queue in any particular order. If you need ordered traversal, consider using {@code Arrays.sort(pq.toArray())}.
         * 方法iterator()中提供的Iterator不能保证以任何特定的顺序遍历优先级队列的元素。如果需要有序遍历，可以考虑使用Arrays.Sort(pq.toArray()。
         */
        Iterator<Item> iterator = queue.iterator();
        while (iterator.hasNext()){
            log.info("迭代 优先级队列时 item 的值：{}",iterator.next());
        }
        log.info(queue.toString());

        Object[] objects = queue.toArray();
        Arrays.sort(objects);
        log.info(Arrays.toString(objects));

        while (!queue.isEmpty()){
            log.info("依次从队列中获取并删除 item 的值：{}",queue.remove());
        }
    }
}
