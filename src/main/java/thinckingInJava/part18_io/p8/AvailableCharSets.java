package thinckingInJava.part18_io.p8;


import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * @author chen
 * @date 2020/12/27 23:41
 * @Description
 */
public class AvailableCharSets {
    public static void main(String[] args) {
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        Iterator<String> iterator = charsets.keySet().iterator();
        while (iterator.hasNext()){
            String csName = iterator.next();
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
