package data_structure.linkedlist;

import java.util.HashMap;

/**
 * 通过HashMap + 双向链表自己实现一个LRU Cache
 * 空间复杂度O(k),k表示容量
 * 小贴士:在双向链表的实现中，使用一个伪头部（dummy head）和伪尾部（dummy tail）标记界限，这样在添加节点和删除节点的时候就不需要检查相邻的节点是否存在。
 */
class LRUCache {
    //使用hashmap可以根据key一次定位到value
    HashMap<Integer, LNode> cache = new HashMap<>();
    //容量
    int capacity = 0;
    int size = 0;
    //采用双链表
    LNode head;
    LNode tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        //初始化双链表
        head = new LNode();
        tail = new LNode();
        head.next = tail;
        tail.prev = head;
    }

    //时间复杂度:O(1)
    public int get(int key) {
        //先从缓存里面查，不存在返回-1；存在则将该节点移动到头部，表示最近使用过，且返回该节点的value
        LNode lNode = cache.get(key);
        if (lNode == null) return -1;
        moveToHead(lNode);
        return lNode.value;
    }

    //时间复杂度O(1)
    public void put(int key, int value) {
        LNode lNode = cache.get(key);
        //如果hashmap中不存在该key
        if (lNode == null) {
            size++;
            //如果已经超过容量了，需要先删除尾部节点，且从hashmap中删除掉该元素
            if (size > capacity) {
                cache.remove(tail.prev.key);
                removeNode(tail.prev);
                size--;
            }
            //将新的节点存入hashmap，并添加到链表的头部
            lNode = new LNode(key, value);
            cache.put(key, lNode);
            addToHead(lNode);
        } else {
            //如果hashmap中存在该key，则修改该节点的value，且将该节点移动到头部
            lNode.value = value;
            removeNode(lNode);
            addToHead(lNode);
        }
    }

    /**
     * 将节点移动到头部
     */
    public void moveToHead(LNode lNode) {
        removeNode(lNode);
        addToHead(lNode);
    }

    /**
     * 移除节点
     */
    public void removeNode(LNode lNode) {
        lNode.prev.next = lNode.next;
        lNode.next.prev = lNode.prev;
        lNode.next = null;
        lNode.prev = null;
    }

    /**
     * 在头部添加节点
     */
    private void addToHead(LNode lNode) {
        head.next.prev = lNode;
        lNode.next = head.next;
        head.next = lNode;
        lNode.prev = head;
    }
}

class LNode {
    int key;
    int value;
    LNode prev;
    LNode next;

    public LNode() {
    }

    public LNode(int key, int value) {
        this.key = key;
        this.value = value;
    }
}