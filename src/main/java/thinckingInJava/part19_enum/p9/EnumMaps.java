package thinckingInJava.part19_enum.p9;

import thinckingInJava.part19_enum.p8.Week;

import java.util.EnumMap;

/**
 * @author chen
 * @date 2020/9/18 22:29
 * EnumMap 是为特殊的Map实现，要求其键必须是一个枚举
 *
 */
public class EnumMaps {
    public static void main(String[] args) {
        EnumMap<Week, Object> enumMap = new EnumMap<>(Week.class);
        enumMap.put(Week.WEDNESDAY, (Command) () -> System.out.println("周三划水"));
        enumMap.put(Week.MONDAY, (Command) () -> System.out.println("周一"));
        Object o = enumMap.get(Week.SUNDAY);
        System.out.println(o);
    }


    interface Command{
        void action();
    }
}
