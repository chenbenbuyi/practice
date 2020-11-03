package designer.factory.inter;

/**
 * @date: 2020/11/3 11:36
 * @author: chen
 * @desc: 产品抽象——手机接口
 */

public interface Phone {
    default void call() {
        System.out.println("手机都能打电话");
    }

    default void sendMsg() {
        System.out.println("手机都能发短信");
    }
}
