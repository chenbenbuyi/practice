package daily.newer;

import lombok.extern.slf4j.Slf4j;

/**
 * @date: 2020/11/19 9:49
 * @author: chen
 * @desc: StringJoiner 适用于需要拼接分隔符的字符串，since  Java 8
 */

@Slf4j
public class StringJoiner {

    public static void main(String[] args) {
        /**
         *   对于带分割符号的字符串拼接，如"[chen-ben-buyi]"常见的普通拼接方式
         */
        StringBuilder sb = new StringBuilder();
        sb.append("[").append("chen").append("-").append("ben").append("-").append("buyi").append("]");
        log.info("StringBuilder 拼接结果：{}",sb);
        // 利用分隔符、前后缀构造对象
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner("-","[","]");
        stringJoiner.add("chen").add("ben").add("buyi");
        log.info("StringJoiner 拼接结果：{}",stringJoiner);
    }
}
