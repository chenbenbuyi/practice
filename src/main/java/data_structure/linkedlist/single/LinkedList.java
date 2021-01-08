package data_structure.linkedlist.single;

import java.util.Objects;

/**
 * @date: 2021/1/7 14:33
 * @author: chen
 * @desc: 手动实现单链表的增删改查
 */
public class LinkedList {
    private Node node;

    public LinkedList add(Node node) {
        // 如果链表内结点为空，简单，说明还没有数据，插入的节点即为首节点
        if (this.node == null) {
            this.node = node;
            return this;
        }
        // 如果链表内节点不为空，则需要遍历链表，找到尾节点进行节点追加。判断尾节点的依据就是尾节点的的指针域 next 为 null
        Node tmp = this.node;
        while ((tmp.getNext()) != null) {
            tmp = tmp.getNext();
        }
        tmp.setNext(node);
        return this;
    }

    public boolean remove(Node node) {
        if (this.node == null)
            return false;
        /**
         *  Node tmp = this.node;
         *  while ((tmp = tmp.getNext()) != node) {}
         * 当遍历找到相等元素时，需要将该节点的上级节点的 next 引用指向该节点的后继节点。问题的关键在于，该节点的后继节点好获取，但是上级节点如何获得呢？
         * 如果该节点有指向上级节点的引用就好——这也是为什么双向链表更常用更好用的原因
         * 寡人想到的办法就是用快慢指针，快指针遍历找到要删除的节点时，此时慢指针刚好指向上级节点
         */
        Node fast = this.node;
        Node slow = this.node;
        while (!((fast = fast.getNext()).equals(node))) {
            slow = slow.getNext();
        }
        if (fast.getNext() == null) {
            slow.setNext(null);
        } else {
            slow.setNext(fast.getNext());
        }
        return true;
    }


    @Override
    public String toString() {
        if (node == null) {
            return "空链表";
        }
        Node curNode = node;
        StringBuilder sb = new StringBuilder();
        while (curNode != null) {
            sb.append(curNode.getData()).append("-->");
            curNode = curNode.getNext();
        }
        return sb.toString();
    }
}
