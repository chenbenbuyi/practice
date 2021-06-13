package jvm.classloader.spi;

/**
 * @author chen
 * @date 2021/6/13 11:54
 * @Description 基于SPI的调用接口
 */
public interface IHelloadler {
    void sayHello(String arg);
    void relayHello(String arg);
}
