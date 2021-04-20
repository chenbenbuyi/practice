package data_structure.list;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *  虽然List都是线性表结构，但Java的两种常见的List实现 ArrayList和 LikedList 由于底层数据结构的不同，不适当的使用，性能对比相差严重,以下为循环遍历对比
 *  在不断增大List容量LIST_SIZE时，ArrayList的遍历耗时表现平稳，而LinkedList的遍历几乎是爆发式的增长（1000 时 1ms,10000 时 45ms,100000 时 5201ms，1000000时恁是没等出来结果）
 */
public class ArrayListAndLinkedList {

    private static final int LIST_SIZE = 100000;

    public static void main(String[] args) {
        final StopWatch stopWatch = new StopWatch();

        List<Integer> arrayList = new ArrayList<>(LIST_SIZE);
        List<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < LIST_SIZE; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        stopWatch.start();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i);
        }
        System.out.println("容量为"+LIST_SIZE+"时ArrayList的for循环遍历耗时：" + (stopWatch.getTime()) + "ms");

        stopWatch.reset();
        stopWatch.start();
        /**
         *  对于链表结构的LinkedList来说，普通的for循环遍历过程是这样的：
         *  get(0)，直接拿到0位的Node0的地址，拿到Node0里面的数据
         *  get(1)，直接拿到0位的Node0的地址，从0位的Node0中找到下一个1位的Node1的地址，找到Node1，拿到Node1里面的数据
         *  get(2)，直接拿到0位的Node0的地址，从0位的Node0中找到下一个1位的Node1的地址，找到Node1，从1位的Node1中找到下一个2位的Node2的地址，找到Node2，拿到Node2里面的数据。
         *  也就是说，LinkedList在 get 任何一个位置的数据的时候，都会把前面的数据走一遍。假如我有10个数据，那么将要查询1+2+3+4+5+5+4+3+2+1=30次数据，相比ArrayList，却只需要查询10次数据就行了，随着LinkedList的容量越大，差距会越拉越大
         *  即便是索引取值时，LinkedList底层分了链表前半段还是后半段来减半实际遍历的数据量，但并没有改变遍历耗时的本质。实际上，通过普通for循环去遍历LikedList，其时间复杂度会达到惊人的 O(N*N)
         *  所以，基于链表实现的LikedList，千万不要使用基于索引的普通for循环去遍历，而应该使用foreach（语法糖）或显示的迭代器进行遍历,可以达到类似数组一样对等的性能
         */
        for (int i = 0; i < linkedList.size(); i++) {
            linkedList.get(i);
        }
        System.out.println("容量为"+LIST_SIZE+"时LinkedList的for循环遍历耗时：" + (stopWatch.getTime()) + "ms");

        stopWatch.reset();
        stopWatch.start();
        Iterator<Integer> iterator = linkedList.iterator();
        while (iterator.hasNext()){
            iterator.next();
        }
        System.out.println("容量为"+LIST_SIZE+"时LinkedList的迭代器循环遍历耗时：" + (stopWatch.getTime()) + "ms");

        stopWatch.reset();
        stopWatch.start();
        while (!linkedList.isEmpty()){
            // pollLast ，removeLast 等倒序输出也可以
//            ((LinkedList<Integer>) linkedList).removeFirst();
            ((LinkedList<Integer>) linkedList).pollFirst();
        }
        System.out.println("容量为"+LIST_SIZE+"时LinkedList的通过队列的方式遍历数据耗时：" + (stopWatch.getTime()) + "ms");

    }
}