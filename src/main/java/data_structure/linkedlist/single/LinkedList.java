package data_structure.linkedlist.single;

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

    @Override
    public String toString() {
        if(node==null){
            return "空链表";
        }
        Node curNode = node;
        StringBuilder sb = new StringBuilder();
        while(curNode != null) {
            sb.append(curNode.getData()).append("-->");
            curNode = curNode.getNext();
        }
        return sb.toString();
    }
}
