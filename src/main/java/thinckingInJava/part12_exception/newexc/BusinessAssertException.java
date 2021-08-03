package thinckingInJava.part12_exception.newexc;

public interface BusinessAssertException extends IResponseEnum, CustomAssert {
    @Override
    default BaseException newException() {
        return newException(null, null);
    }
 
    @Override
    default BaseException newException(Object args) {
        return newException(null, args);
    }
 
    @Override
    default BaseException newException(Throwable t, Object args) {
        return new BusinessException(this, args);
    }
}
