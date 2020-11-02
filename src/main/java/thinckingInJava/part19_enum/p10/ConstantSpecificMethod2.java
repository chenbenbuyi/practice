package thinckingInJava.part19_enum.p10;

/**
 * @author chen
 * @date 2020/9/17 23:47
 * @Description 实际上，根据默认原则，如果不是必须每个常量元素都强制自定义实现，完全可以定义普通方法，不同的常量按照需求实现即可
 */
public enum ConstantSpecificMethod2 {
    DATE_TIME,
    CLASSPATH {
        @Override
        String getInfo2() {
            return "自定义实现2";
        }
    },
    VERSION {
        @Override
        String getInfo() {
            return "自定义实现";
        }
    };

    // 枚举内可以定义普通方法，每个
    String getInfo() {
        return "默认实现";
    }

    String getInfo2() {
        return "默认实现2";
    }

    public static void main(String[] args) {
        for (ConstantSpecificMethod2 constantSpecificMethod : values()) {
            String info = constantSpecificMethod.getInfo2();
            System.out.println(info);
        }
    }
}
