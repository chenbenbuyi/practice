package thinckingInJava.part17.p3_6;

import lombok.extern.slf4j.Slf4j;
import thinckingInJava.part17.p2.Countries;

import java.util.*;

/**
 * @author chen
 * @date 2020/11/24 23:37
 * @Description 不受支持的操作
 * 集合的公共接口Collection中有可选操作和未获支持的操作的说法
 * 可选操作，是指Collection中的接口定义，特定集合子类根据自己的特性可以选择不实现，调用此类接口会抛出 UnsupportedOperationException
 * 未获支持的操作，指对固定尺寸的数据结构进行的一些不受支持的操作。
 * 比如 Arrays.asList() 返回的固定尺寸的集合，你无法对其进行元素的增删操作，但是你可以对元素进行修改
 * Collections.unmodifiableList() 返回的固定元素的集合
 */
@Slf4j
public class Unsupported {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("陈本布衣", "陈小远");
//        list.add("s"); 不支持
        list.set(0, "chenxiaoyuan");
        log.info("{}", list);
        List<String> unmodifiableList = Collections.unmodifiableList(Arrays.asList("陈本布衣", "陈小远"));
//        unmodifiableList.set(0,"不支持修改");
        //
//        List<String> linkedList = new ArrayList<>(Countries.names());
        LinkedList<String> linkedList = new LinkedList<>(Countries.names());
        ListIterator<String> listIterator = linkedList.listIterator();
        String next = listIterator.next();
        // 在迭代器指针指向的位置进行插入
        listIterator.add("测试");
        next = listIterator.next();
        // 移除的是迭代出来的元素
        listIterator.remove();
        next = listIterator.next();
        // 替换的是当前迭代出的元素
        listIterator.set("滑石粉");

    }
}
