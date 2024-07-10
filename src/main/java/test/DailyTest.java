package test;


/**
 * @author csxian
 * @Description
 * @Date 2022/10/6 21:16
 */
public class DailyTest {

    /**
     *  三元表达式注意在涉及类型隐式转换时的空值问题
     */
    public static void main(String[] args) {
        Integer b = null;
        Integer a = false ? Integer.valueOf(3 + 3) : b;
        System.out.println(a);
    }
}
