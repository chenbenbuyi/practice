package data_structure.linkedlist.single;

/**
 * @date: 2021/1/7 14:06
 * @author: chen
 * @desc:
 */
public class LinkedListTest {
    private static final int TIMES = 11;

    public static void main(String[] args) {
        OldLinkedList oldLinkedList = new OldLinkedList();
        System.out.println(oldLinkedList);
        for (int i = 1; i < 2; i++) {
            oldLinkedList.add(new Node(i, "第" + i + "个节点"));
        }
        System.out.println(oldLinkedList);
        // 测试节点删除
        oldLinkedList.remove(new Node(3,"第" + 1 + "个节点"));
        System.out.println(oldLinkedList);
    }
}
