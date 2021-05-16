package jvm.memory.method;


/**
 * @author chen
 * @date 2021/5/16 11:23
 * @Description 方法区测试
 * 运行程序，可以通过jps命令查看虚拟机进程，通过类似jinfo -flag PermSize pid 查看内存参数，比较设置参数和不设置参数以及不同版本下运行查看相应参数的默认值
 *  参考(以实际测试为准)：
 *   1.7版本，默认方法区初始分配空间20.75M,最大64M(32位机器)或82M(64位机器)
 *   1.8版本，默认元空间初始分配空间21M
 *
 * jdk1.7及以前方法区参数（1.8之后废弃）
 *       -XX:PermSize=100m -XX:MaxPermSize=100m
 * jdk1.8及以后，方法区参数
 *      -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=100m
 */
public class MethodAreaTest {
    public static void main(String[] args) {
        while (true){

        }
    }
}
