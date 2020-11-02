package thinckingInJava.part15_genericity.p7;

/**
 * @author chen
 * @date 2020/10/14 23:43
 * @Description
 */
public class CastHolder {
    private Object obj;

    public void set(Object object) {
        this.obj = object;
    }

    public Object getObj() {
        return obj;
    }

    /**
     * 在没有使用泛型的时候，需要进行显示的强转，强转代码会在运行时候进行类型检查
     * 添加泛型约束后，编译器在编译相应的代码段时就会进行类型 检查，提前预防错误，然后在编译成字节码后类型擦除，
     *  在get取值的时候，隐式的帮你完成在没有泛型的情况下你需要显示的进行的类型检查和转换
     *
     */
    public static void main(String[] args) {
        CastHolder castHolder = new CastHolder();
        castHolder.set("陈本布衣");
        String obj = (String) castHolder.getObj();

        CastHolder2<String> castHolder2 = new CastHolder2<>();
        castHolder.set("陈本布衣");
        String obj2 = castHolder2.getObj();
    }

}

class CastHolder2<T> {
    private T obj;

    public void set(T object) {
        this.obj = object;
    }

    public T getObj() {
        return obj;
    }
}

