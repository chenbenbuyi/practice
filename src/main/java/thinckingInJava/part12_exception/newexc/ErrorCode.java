package thinckingInJava.part12_exception.newexc;

/**
 * @Description
 * @Date 2021/8/3 15:58
 * @Created by csxian
 */
public enum ErrorCode implements BusinessAssertException {

    ERROR(999, "服务器内部错误"),
    UNAUTHORIZED(300, "用户无访问权限"),
    UN_LOGIN(310, "用户未登录"),
    EXPIRED_TOKEN(320, "用户登录过期"),
    TWICE_AUTHORIZED(330, "需二次验证信息");


    private final int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }}
