package thinckingInJava.part17_collection.extra.linkedlist;

import lombok.Data;

/**
 * @date: 2020/12/9 9:56
 * @author: chen
 * @desc: 链表测试
 * 单向链表的一个链接点包含数据域和指针域两部分
 */

@Data
public class Node {
    /**
     * 指针域
     */
    private Node next;

    /**
     * 数据域
     */
    private int data;

    public Node(int data) {
        this.data = data;
    }
}
