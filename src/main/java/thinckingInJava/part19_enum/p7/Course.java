package thinckingInJava.part19_enum.p7;

/**
 * @author chen
 * @date 2020/8/16 10:01
 * @Description
 */
public enum  Course {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class),
    ;

    private Food[] values;

    Course(Class<? extends  Food> food) {
        values = food.getEnumConstants();
    }

    public Food randomSelection(){
        return Enums.random(values);
    }
}
