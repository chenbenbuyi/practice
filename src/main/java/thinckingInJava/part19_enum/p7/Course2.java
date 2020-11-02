package thinckingInJava.part19_enum.p7;

/**
 * @author chen
 * @date 2020/9/16 22:13
 * @Description 重构代码，将接口嵌套于枚举类 由此可以看出，枚举和接口是可以相互嵌套的
 */
public enum  Course2 {

    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class),
    ;

    public interface Food{
        enum Appetizer implements Food {
            SALAD,SOUP,SPRING_ROLIS
            ;
        }

        enum MainCourse implements Food {
            BEEF,CHICKEN,DUCK,
            ;
        }

        enum Dessert implements Food {
            TIRAMISU,GELATO,BLACK_FOREST_CAKE,FRUIT,CREME_CARAMEL
            ;
        }

        enum Coffee implements Food {
            BLACK_COFFEE,DECAF_COFFEE,ESPRESSO,LATTE,CAPPUCCINO,TEA,HERB_TEA
            ;
        }
    }

    private Food[] values;

    Course2(Class<? extends  Food> food) {
        values = food.getEnumConstants();
    }

    public Food randomSelection(){
        return Enums.random(values);
    }

    public static void main(String[] args) {
        for (int i = 0; i <4 ; i++) {
            for (Course2 course : Course2.values()) {
               Food food = course.randomSelection();
                System.out.println(food);
            }
            System.out.println("-------随机选餐----------");
        }
    }

}
