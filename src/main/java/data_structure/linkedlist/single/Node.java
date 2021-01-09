package data_structure.linkedlist.single;

import lombok.Data;

import java.util.Objects;

/**
 * @date: 2020/12/9 9:56
 * @author: chen
 * @desc: 链表测试
 * 先从最简单的单链表开始,最简单的节点玩顺溜了，才扩展双向链表、循环链表、双向循环链表、添加泛型等
 * 核心点在于：单向链表的一个链接节点包含数据域和指针域两部分，指针域指向下一个链接节点。
 * 所以，在设计对象的时候，应该有两个对象：
 *  结点对象——存储数据本身和结点指针/引用
 *  链表对象——操纵串联数据结点对象，以便对链表结点进行增删改查等操作
 *
 *  ps: 使用层面来说，我们使用链表是直接使用链表对象来操作串联数据结点，外部直接操作结点无多大意义的。所以很多链表的源码中，链表的结点对象都是作为内部类来进行声明的
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
    private int num;
    private String data;

    public Node() {
    }

    public Node(int num, String data) {
        this.num = num;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return num == node.num ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(next, num, data);
    }

    public Node(Node next) {
        this.next = next;
    }


}
