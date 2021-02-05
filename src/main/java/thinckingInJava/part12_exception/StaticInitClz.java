package thinckingInJava.part12_exception;

/**
 * 类静态初始化失败导致的 NoClassDeFoundError
 * 示例代码中，由于 StaticInitClz 静态初始化时发生异常（ExceptionInInitializerError——最直接原因）,那么以后任何用到这个类的地方都会抛出 NoClassDeFoundError 异常
 */
public class StaticInitClz {
    static int data = 1 / 0;
}
