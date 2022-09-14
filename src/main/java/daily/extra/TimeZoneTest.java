package daily.extra;

import util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.TimeZone;

/**
 * @Description 时区转换测试
 * @Date 2022/2/22 13:41
 * @Created by csxian
 */
public class TimeZoneTest {

    public static void main(String[] args) {

        String dateStr = "2022-02-22T02:11:10.443";
        LocalDateTime oldLocalDateTime = LocalDateTime.parse(dateStr);
        ZoneCovert( oldLocalDateTime);
        System.out.println("Basic " + Base64Utils.encodeToString(String.format("%s:%s", "admin", "cov-platform@2021").getBytes(StandardCharsets.UTF_8)));
    }

    private static void ZoneCovert(LocalDateTime oldLocalDateTime) {
        // 获取当前时区
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        System.out.println(offset);
        TimeZone timeZone = TimeZone.getDefault();
        System.out.println(timeZone.getID());
        // 将指定时区时间转换为当前时区时间
        ZoneId oldZoneId = ZoneId.of("UTC+0");
        LocalDateTime localDateTime = oldLocalDateTime.atZone(oldZoneId).withZoneSameInstant(ZoneId.ofOffset("UTC", offset)).toLocalDateTime();
        System.out.println(localDateTime);
    }
}
