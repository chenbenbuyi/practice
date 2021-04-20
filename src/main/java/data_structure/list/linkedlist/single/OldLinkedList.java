package data_structure.list.linkedlist.single;

/**
 * @date: 2021/1/7 14:33
 * @author: chen
 * @desc: 手动实现单链表的增删改查 。
 *  该链表代码只实现了简单的增删操作，而且还有一些小bug，暂时保留初始代码模样，另起炉灶单独写
 *  {@link LinkedList }
 */
public class OldLinkedList {
    private Node node;

    public OldLinkedList add(Node node) {
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
        if (this.node == null || node == null)
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
        /**
         * bug 修复，要考虑一些临界值，比如删除头结点或者尾节点的情况
         *  while (!((fast = fast.getNext()).equals(node))) 的条件中，由于首节点被略过，在删除节点是首节点的时候会导致循环持续到尾节点的空指针
         *  所以，首节点需要在遍历前单独判断
         */
        if (this.node.equals(node)) {  // bug 修复新增的判断逻辑
            this.node = fast.getNext();
            return true;
        }
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
