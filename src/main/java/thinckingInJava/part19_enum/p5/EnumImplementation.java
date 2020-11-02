package thinckingInJava.part19_enum.p5;

import thinckingInJava.part15_genericity.p3.Generator;

import java.util.Random;

/**
 * @author chen
 * @date 2020/9/16 21:05
 * @Description 枚举由于隐式的继承了Enum类，所以不能在继承任何其它的类，但可以实现接口，定义方法
 *  下面的示例，将枚举看成一个普通的接口实现类即可，只不过其创建对象的方式，相比普通类的 new 的方法，更加语义化的简洁
 */
enum CartoonCharacter implements Generator<CartoonCharacter> {
    SLAPPY, SPANKY, PUNCHY, SILLY, BOUNCY, NUTTY, BOB;

    private Random rand = new Random(47);

    @Override
    public CartoonCharacter next() {
        return values()[rand.nextInt(values().length)];
    }
}



public class EnumImplementation {

    public static <T> void printNext(Generator<T> rg){
        System.out.print(rg.next()+",");
    }

    public static void main(String[] args) {
        // 枚举的实例化 任意指定一个枚举常量进行实例化即可获取枚对象
        CartoonCharacter cartoonCharacter = CartoonCharacter.BOB;
        for (int i = 0; i <10 ; i++) {
            printNext(cartoonCharacter);
        }

    }
}
