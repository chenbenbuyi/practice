package thinckingInJava.part15_genericity.p8;

/**
 * @author chen
 * @date 2020/10/15 23:16
 * @Description
 */
public class GenericArray<T> {
    private T[] array;

    @SuppressWarnings("unchecked")
    public GenericArray(int size) {
        /**
         * 泛型擦除，使得所有在运行时需要知道确切类型的操作都无法正常进行，
         *  比如 arg instanceof T ,new T(),new T[SIZE]等等
         *  所以，下面代码中，要创建泛型数组，无法直接通过new T[]的方式进行，只能通过new Object[]的形式，然后将其转型
         *   但是，由于泛型擦除，实际上的转型是并不成功的 ,因为表示参数类型的泛型参数擦除之后，实际运行时的数组类型就是Object[]
         *   所以在主方法的测试中，虽然泛型参数的指定，在编译期有助于往数组中放入正确的类型，但是编译成的字节码中并没有泛型参数存在，
         *      以下代码编译后实际为：this.array = (Object[])(new Object[size])
         *   所以创建的数组并不能转换成对应的泛型类型数组
         */
        this.array = (T[]) new Object[size];
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] rep(){
        return array;
    }

    public static void main(String[] args) {
        GenericArray<Integer> val = new GenericArray<>(10);
        val.put(0, 21);
//        val.put(1,"字符串" ); 无法编译
        Integer integer = val.get(0);
        Integer[] rep = val.rep();
    }
}
