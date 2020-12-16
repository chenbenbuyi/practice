package thinckingInJava.part18_io.p1_file;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author chen
 * @date 2020/12/16 7:39
 * @Description
 */

public class PPrint {
    public static String pformat(Collection<?> c) {
        if (c.isEmpty())
            return "[]";
        StringBuilder result = new StringBuilder("[");
        for (Object elem : c) {
            if (c.size() != 1) {
                result.append("\n ");
            }
            result.append(elem);
        }
        if (c.size() != 1) {
            result.append("\n");
        }
        result.append("]");
        return result.toString();
    }

    public static void pprint(Collection<?> c) {
        System.out.println(pformat(c));
    }

    public static void pprint(Object[] c) {
        System.out.println(pformat(Arrays.asList(c)));
    }
}
