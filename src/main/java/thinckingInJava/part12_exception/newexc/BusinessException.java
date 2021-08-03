package thinckingInJava.part12_exception.newexc;

public class BusinessException extends BaseException {
    private static final long serialVersionUID = 1L;
 
    public BusinessException(IResponseEnum resp) {
        super(resp, null);
    }
 
    public BusinessException(IResponseEnum resp, Object os) {
        super(resp, os);
    }
}
