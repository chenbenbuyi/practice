package thinckingInJava.part16.p7_arrays.compar;

import lombok.Data;

/**
 * @author chen
 * @date 2020/11/13 23:26
 * @Description 实现Comparable接口，使自定义的类具有比较能力
 * 实现了Comparable接口的类的对象的列表或数组可以通过Collections.sort或Arrays.sort进行自动排序。
 */
@Data
public class ComparableUser implements Comparable<ComparableUser> {
    private String name;
    private Integer age;

    public ComparableUser(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(ComparableUser o) {
        return this.age - o.getAge();
    }
}
