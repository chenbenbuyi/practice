package thinckingInJava.part12_exception.newexc;


import lombok.Data;

@Data
public class BaseException extends RuntimeException {
    /**
     * 错误码
     */
    private int code;
 
    /**
     * 错误信息
     */
    private String msg;
 
    /**
     * 传参
     */
    private Object result;
 
    public BaseException(IResponseEnum resp, Object os) {
        super(resp.getMessage());
        this.code = resp.getCode();
        this.msg = resp.getMessage();
        this.result = os;
    }

}
