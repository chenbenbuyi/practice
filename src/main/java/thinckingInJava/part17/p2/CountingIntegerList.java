package thinckingInJava.part17.p2;

import java.util.AbstractList;

/**
 * @author chen
 * @date 2020/9/5 21:16
 * @Description
 */
public class CountingIntegerList extends AbstractList<Integer> {
    private int size;

    public CountingIntegerList(int size) {
        this.size = size < 0 ? 0 : size;
    }

    @Override
    public Integer get(int index) {
        return index;
    }

    @Override
    public int size() {
        return size;
    }

}
