package geek.designer.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author chen
 * @Description 通过一个ID生成器的案例，实践代码质量优化问题
 *
 * 通用检查项，以设计原则来看这段代码是否可读、可扩展、可维护、灵活、简洁、可复用、可测试。具体来说，即是以下几个方面：
 *
 * 目录设置是否合理、模块划分是否清晰、代码结构是否满足“高内聚、松耦合”？
 * 是否遵循经典的设计原则和设计思想（SOLID、DRY、KISS、YAGNI、LOD 等）？
 * 设计模式是否应用得当？是否有过度设计？
 * 代码是否容易扩展？如果要添加新功能，是否容易实现？
 * 代码是否可以复用？是否可以复用已有的项目代码或类库？
 * 是否有重复造轮子？
 * 代码是否容易测试？
 * 单元测试是否全面覆盖了各种正常和异常的情况？
 * 代码是否易读？
 * 是否符合编码规范（比如命名和注释是否恰当、代码风格是否一致等）？
 *
 * 从代码业务功能实现逻辑上看，代码是否有以下问题：
 *
 * 代码是否实现了预期的业务需求？
 * 逻辑是否正确？
 * 是否处理了各种异常情况？
 * 日志打印是否得当？
 * 是否方便 debug 排查问题？
 * 接口是否易用？
 * 是否支持幂等、事务等？
 * 代码是否存在并发问题？
 * 是否线程安全？
 * 性能是否有优化空间，比如，SQL、算法是否可以优化？
 * 是否有安全漏洞？
 * 比如输入输出校验是否全面？
 *
 * @Date 2022/9/11 16:55
 */
public class IdGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    public static void main(String[] args) {
        System.out.println(generate());
    }

    /**
     *  个人看这段代码，存在问题：
     *  1、太过具体的静态方法，一但写死就无法变更逻辑，如果需求要变更ID算法，需修改源码，不符合开闭原则
     *
     */

    // 静态函数影响可测试性，主要表现在:不能通过传入不同的实例，改变代码的实现方式。使得代码的灵活性降低
    public static String generate() {
        String id = "";
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String[] tokens = hostName.split("\\.");
            if (tokens.length > 0) {
                hostName = tokens[tokens.length - 1];
            }
            char[] randomChars = new char[8];
            int count = 0;
            Random random = new Random();
            while (count < 8) {
                int randomAscii = random.nextInt(122);
                if (randomAscii >= 48 && randomAscii <= 57) {
                    randomChars[count] = (char) ('0' + (randomAscii - 48));
                    count++;
                } else if (randomAscii >= 65 && randomAscii <= 90) {
                    randomChars[count] = (char) ('A' + (randomAscii - 65));
                    count++;
                } else if (randomAscii >= 97 && randomAscii <= 122) {
                    randomChars[count] = (char) ('a' + (randomAscii - 97));
                    count++;
                }
            }
            id = String.format("%s-%d-%s", hostName,
                    System.currentTimeMillis(), new String(randomChars));
        } catch (UnknownHostException e) {
            logger.warn("Failed to get the host name.", e);
        }

        return id;
    }
}
