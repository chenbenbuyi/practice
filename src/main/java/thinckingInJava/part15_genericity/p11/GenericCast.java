package thinckingInJava.part15_genericity.p11;

/**
 * @date: 2020/11/3 14:06
 * @author: chen
 * @desc:
 */
class FixedSizeStack<T> {
	private int index = 0;
	private Object[] storage;
 
	public FixedSizeStack(int size) {
		storage = new Object[size];
	}
 
	public void push(T item) {
		storage[index++] = item;
	}
 
	@SuppressWarnings("unchecked")
	public T pop() {
		/**
		 *  泛型参数的转型是无效的，因为此处的Object数组获取的数据也是Object类型，
		 *  而编译器的泛型擦除，让 T 类型实际变回了其边界即 Object,所以此处就相当于（Object）Object
		 */
		return (T) storage[--index];
	}
}
 
public class GenericCast {
	private static final int SIZE = 4;

	public static void main(String[] args) {
		FixedSizeStack<String> strings = new FixedSizeStack<>(SIZE);
		for (String string : "陈-本-布-衣".split("-")) {
			strings.push(string);
		}
		for (int i = 0; i < SIZE; i++) {
			String s = strings.pop();
			System.out.print(s + " ");
		}
	}
}
