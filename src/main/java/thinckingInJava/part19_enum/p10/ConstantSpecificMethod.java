package thinckingInJava.part19_enum.p10;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author chen
 * @date 2020/9/17 23:47
 * @Description java枚举可以为枚举编写常量相关的方法来定义其行为
 */
public enum  ConstantSpecificMethod {
    DATE_TIME{
        @Override
        String getInfo(){
            return DateFormat.getDateInstance().format(new Date());
        }
    },
    CLASSPATH{
        @Override
        String getInfo(){
            return System.getenv("CLASSPATH");
        }
    },
    VERSION{
        @Override
        String getInfo() {
            return System.getenv("java.version");
        }
    }
    ;

    // 枚举内可以定义抽象方法。但由于枚举是final的，所以其抽象方法实际是需要在枚举值定义中进行具体实现的
    abstract String getInfo();

    public static void main(String[] args) {
        for (ConstantSpecificMethod constantSpecificMethod : values()) {
            String info = constantSpecificMethod.getInfo();
            System.out.println(info);
        }
    }


}
