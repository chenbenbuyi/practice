package pk1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author chen
 * @date 2020/9/20 20:43
 * @Description 测试定义实现 Iterable 接口进行foreach 迭代
 */
public class IterableTest {

    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        for (Object o : list) {
            list.remove(o);
        }
        Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.remove();
        }
    }
}
