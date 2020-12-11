package thinckingInJava.part17_collection.p11_collections;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * @author chen
 * @date 2020/12/12 0:19
 * @Description
 */
@Slf4j
public class ListSortSearch {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(CollectionsTest.list);
        list.addAll(CollectionsTest.list);
        log.info("列表:{}", list);
        ListIterator<String> listIterator = list.listIterator(10);
        while (listIterator.hasNext()) {
            /**
             *  注意，这里要删除元素，必须先移动迭代指针
             */
            listIterator.next();
            listIterator.remove();
        }
        log.info("列表2:{}", list);
        /**
         *  未经过排序的二分查找，结果几乎不可能正确
         *
         */
        Collections.sort(list);
        log.info("排序后的列表：{}", list);
        int index = Collections.binarySearch(list, "one");
        log.info("查找到的元素索引：{}", index);
    }
}
