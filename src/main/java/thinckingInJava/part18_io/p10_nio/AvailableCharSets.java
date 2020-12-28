package thinckingInJava.part18_io.p10_nio;


import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * @author chen
 * @date 2020/12/27 23:41
 * @Description 字符集合
 */
public class AvailableCharSets {
    public static void main(String[] args) {
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        Iterator<String> iterator = charsets.keySet().iterator();
        while (iterator.hasNext()){
            String csName = iterator.next();
            System.out.print(csName);
            Iterator<String> iterator1 = charsets.get(csName).aliases().iterator();
            if(iterator1.hasNext()){
                System.out.print("：");
            }
            while (iterator1.hasNext()){
                System.out.print(iterator1.next());
                if(iterator1.hasNext()){
                    System.out.print(", ");
                }
            }
            System.out.println("");

        }
    }
}
