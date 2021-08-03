package thinckingInJava.part12_exception.newexc;

public interface IResponseEnum {
    /**
     * 错误编码
     * @return 错误编码
     */
    int getCode();
 
    /**
     * 错误信息
     * @return 错误信息
     */
    String getMessage();
}
