package thinckingInJava.part15_genericity.p8;

/**
 * @author chen
 * @date 2020/10/15 23:55
 * @Description
 */
public class GenericArray2<T> {
    private Object[] array;

    public GenericArray2(int size) {
        this.array = new Object[size];
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        /**
         *  放入的单个元素可以正确的转型
         */
        return (T)array[index];
    }

    @SuppressWarnings("unchecked")
    public T[] rep(){
        /**
         * 此处和GenericArray中在创建数组对象时进行转换并没有本质区别，所以依然是无法转型成功的泛型参数类型数组的对象引用
         * 正确的泛型数组创建应该是
         * {@link GenericArrayWithTypeToken}
         *  Array.newInstance 的方式，传递类型标记： Class<T>
         */
        return (T[])array;
    }

}
