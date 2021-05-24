package collection.set;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chen
 * @date 2021/5/23 12:59
 * @Description 一道很有意思的练习题，有点坑
 */
public class HashSetTest2 {
    public static void main(String[] args) {
        Set<Person> set = new HashSet<>();
        Person p1 = new Person(1, "A");
        Person p2 = new Person(2,"B");
        set.add(p1);
        set.add(p2);
        p1.setName("修改名字，导致哈希值改变");
        // 修改了对象，导致删除元素时计算的哈希值变化，定位不到原对象，导致原对象无法删除
        set.remove(p1);
        System.out.println(set);
        set.add(new Person(1,"C"));
        System.out.println(set);
        // 该对象和最开始添加的A对象会定位到同一个哈希槽，但由于equals比较两个对象又不相等，所以会形成链表
        set.add(new Person(1,"A"));
        System.out.println(set);
    }
}


@Data
@AllArgsConstructor
class  Person{
    private Integer id;
    private String name;
}
