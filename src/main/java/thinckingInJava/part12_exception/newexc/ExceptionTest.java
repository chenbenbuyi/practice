package thinckingInJava.part12_exception.newexc;

/**
 * @Description
 * @Date 2021/8/3 15:56
 * @Created chen
 */
public class ExceptionTest {

    public static void main(String[] args) {
        try {
            ErrorCode.ERROR.assertNotNull(null);
        } catch (Exception e) {
            BusinessException ex = (BusinessException) e;
            System.out.println(ex.getCode() + " : " + ex.getMsg());
            System.out.println("传入的参数为：" + ex.getResult());
        }
    }
}
