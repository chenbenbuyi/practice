package thinckingInJava.part15_genericity.p2;

/**
 * @author chen
 * @date 2020/10/10 21:37
 * @Description
 */
public class LinkedStack<T> {
    /**
     * 链表中每个结点包括两个部分：一个是存储数据元素的数据域，另一个是存储下一个结点地址的指针域
     * 链表有很多种不同的类型：单向链表，双向链表以及循环链表
     * 该嵌套的 Node 模拟的是一个单向的链表
     */
    private static class Node<U> {
        U item;
        Node<U> next;

        public Node() {
            item = null;
            next = null;
        }

        public Node(U item, Node<U> next) {
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }

    // 构造 LinkedStack 对象的时候同时创建 末端哨兵节点，当pop的时候如果当前指针指向 的 top 是末端节点，则停止移动
    private Node<T> top = new Node<>();

    /**
     * 对数据元素来说，除了存储其本身的信息之外，还需存储一个指示其直接后继的信息（即直接后继的存储位置）
     * 因此，在push 向链表结构增加数据节点的时候，每次都在 new 一个新的节点，新的节点要存储当前数据本身，还要存储上一个节点的指针引用，以此，不同的节点之间形成链接串联
     * 这和 ArrayList 那种内部基于数组对象来存储元素有很大的不同
     */
    public void push(T item) {
        top = new Node<>(item, top);
    }

    public T pop() {
        T result = top.item;
        if (!top.end()) {
            top = top.next;
        }
        return result;
    }

    public static void main(String[] args) {
        LinkedStack<String> lss = new LinkedStack<>();
        for (String s : "chen ben bu yi".split(" ")) {
            lss.push(s);
        }
        String s;
        while ((s = lss.pop()) != null) {
            System.out.println(s);
        }
    }
}
