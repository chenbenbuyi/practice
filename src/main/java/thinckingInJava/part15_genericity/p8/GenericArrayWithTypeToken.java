package thinckingInJava.part15_genericity.p8;

import java.lang.reflect.Array;

/**
 * @author chen
 * @date 2020/10/16 0:11
 * @Description
 */
public class GenericArrayWithTypeToken<T> {
    private T[] array;

    /**
     * @param clz 类型标记
2v
      */
    public GenericArrayWithTypeToken(Class<T> clz ,int size) {
        this.array = (T[])Array.newInstance(clz,size );
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return array[index];
    }

    public T[] rep(){
        return array;
    }

    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> gen = new GenericArrayWithTypeToken<>(Integer.class, 10);
        Integer[] rep = gen.rep();
    }
}
