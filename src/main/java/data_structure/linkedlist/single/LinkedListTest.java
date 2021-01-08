package data_structure.linkedlist.single;

/**
 * @date: 2021/1/7 14:06
 * @author: chen
 * @desc:
 */
public class LinkedListTest {
    private static final int TIMES = 11;

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        System.out.println(linkedList);
        for (int i = 1; i < TIMES; i++) {
            linkedList.add(new Node(i, "第" + i + "个节点"));
        }
        System.out.println(linkedList);
        // 测试节点删除
        linkedList.remove(new Node(5,"第" + 5 + "个节点"));
        System.out.println(linkedList);
    }
}
