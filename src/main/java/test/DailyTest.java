package test;

import org.junit.Test;

/**
 * @author csxian
 * @Description
 * @Date 2022/10/6 21:16
 */
public class DailyTest {

    /**
     *  三元表达式注意在涉及类型隐式转换时的空值问题
     */
    @Test
    public void testNpe() {
        Integer b = null;
        Integer a = false ? 3 + 3 : b;
        System.out.println(a);
    }
}
