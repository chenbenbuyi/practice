package thinckingInJava.part18_io.p9;

/**
 * @author chen
 * @date 2020/12/21 23:46
 * @Description 自定义异常
 */
public class OSExcuteException extends RuntimeException {
    public OSExcuteException(String message) {
        super(message);
    }
}
