package thinckingInJava.part19_enum.p7;

/**
 * @author chen
 * @date 2020/8/16 9:59
 * @Description 由于枚举只能继承接口，利用这一点在接口内部对枚举元素实例进行分组
 */
public interface Food {

    enum Appetizer implements Food{
        SALAD,SOUP,SPRING_ROLIS
        ;
    }

    enum MainCourse implements Food{
        BEEF,CHICKEN,DUCK,
        ;
    }

    enum Dessert implements Food{
        TIRAMISU,GELATO,BLACK_FOREST_CAKE,FRUIT,CREME_CARAMEL
        ;
    }

    enum Coffee implements Food{
        BLACK_COFFEE,DECAF_COFFEE,ESPRESSO,LATTE,CAPPUCCINO,TEA,HERB_TEA
        ;
    }


}
