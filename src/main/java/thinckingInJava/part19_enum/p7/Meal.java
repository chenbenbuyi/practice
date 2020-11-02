package thinckingInJava.part19_enum.p7;

/**
 * @author chen
 * @date 2020/9/16 22:01
 * @Description
 */
public class Meal {
    public static void main(String[] args) {
        for (int i = 0; i <4 ; i++) {
            for (Course course : Course.values()) {
                Food food = course.randomSelection();
                System.out.println(food);
            }
            System.out.println("-------随机选餐----------");
        }
    }
}
