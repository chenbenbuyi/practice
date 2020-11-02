package thinckingInJava.part14_class.p2;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2020/9/27 20:20
 * @Description 定义一个泛型化的结构，如类或接口，而后通过反射创建对象
 */

@Slf4j
public class FilledList<T> {
    private Class<T> type;

    public FilledList(Class<T> type) {
        this.type = type;
    }

    public List<T> create(int count) {
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            try {
                /**
                 * newInstance 通过可访问的的默认构造器创建对象，所以，如果没有默认无参数构造器或其无参构造器不可访问，都将导致对象创建失败
                 */
                T t = type.newInstance();
                list.add(t);
            } catch (Exception e) {
                log.error("create error", e);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        FilledList<CountedInteger> filledList = new FilledList<>(CountedInteger.class);
        List<CountedInteger> countedIntegers = filledList.create(20);
        System.out.println(countedIntegers);
    }
}

class CountedInteger{
    private static int counter;
    private static final int id = counter++;


    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
