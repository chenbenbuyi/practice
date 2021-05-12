package java_concurrency.extra;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * SimpleDateFormat 线程不安全验证
 * SimpleDateFormat 是线程不安全的类，一般不要定义为 static 变量，如果定义为 static ，必须加锁，或者使用 DateUtils 工具类，比如做如下处理：
 *
 *      private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
 *          @Override protected DateFormat initialValue() {
 *              return new SimpleDateFormat("yyyy-MM-dd");
 *          }
 *      };
 * 如果是 JDK8 的应用，可以使用 Instant 代替 Date，LocalDateTime 代替 Calendar， DateTimeFormatter 代替 SimpleDateFormat，官方给出的解释：simple beautiful strong immutable thread-safe。
 */
public class SimpleDateFormatTest {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {

        String[] dateStringArray = new String[]{"2020-09-10", "2020-09-11", "2020-09-12", "2020-09-13", "2020-09-14"};

        DateFomatThread[] threads = new DateFomatThread[5];

        for (int i = 0; i < 5; i++) {
            threads[i] = new DateFomatThread(sdf, dateStringArray[i]);
        }

        for (int i = 0; i < 5; i++) {
            threads[i].start();
        }
    }
}


class DateFomatThread extends Thread {

    private SimpleDateFormat simpleDateFormat;
    private String dateString;

    public DateFomatThread(SimpleDateFormat simpleDateFormat, String dateString) {
        this.simpleDateFormat = simpleDateFormat;
        this.dateString = dateString;
    }

    @Override
    public void run() {
        try {
            /**
             * 线程不安全的原因在于 {@link SimpleDateFormat#format(java.util.Date, java.lang.StringBuffer, java.text.Format.FieldDelegate)} 方法对共享变量 calendar 的写操作
             *
             *
             */

            Date date = simpleDateFormat.parse(dateString);
            String newDate = simpleDateFormat.format(date);
            if (!newDate.equals(dateString)) {
                System.out.println("ThreadName=" + Thread.currentThread().getName()
                        + " 报错了，日期字符串：" + dateString
                        + " 转换成的日期为：" + newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}


/**
 * 通过每次操作单独创建对象实例解决先按全问题。肯定会造成对象实例频繁的创建和销毁
 */
class DateUtils {
    public static Date parse(String formatPattern, String dateString) throws ParseException {
        return new SimpleDateFormat(formatPattern).parse(dateString);
    }

    public static String format(String formatPattern, Date date) {
        return new SimpleDateFormat(formatPattern).format(date);
    }
}

/**
 *  利用 线程隔离技术来解决线程安全问题
 */
class ThreadLocalDateUtils {

    private static ThreadLocal<DateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    public static Date parse(String formatPattern, String dateString) throws ParseException {
        return threadLocal.get().parse(dateString);
    }

    public static String format(String formatPattern, Date date) {
        return threadLocal.get().format(date);
    }
}


/**
 *  利用 Java8 提供的不可变对象来解决线程安全问题。推荐使用这种方式
 *  Java 8 引入了新的日期时间 API，并引入了线程安全的日期类。
 *      Instant：瞬时实例。
 *      LocalDate：本地日期，不包含具体时间 例如：2014-01-14 可以用来记录生日、纪念日、加盟日等。
 *      LocalTime：本地时间，不包含日期。
 *      LocalDateTime：组合了日期和时间，但不包含时差和时区信息。
 *      ZonedDateTime：最完整的日期时间，包含时区和相对UTC或格林威治的时差。
 *
 */
class DateUtilsJava8 {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parse(String dateString) {
        return LocalDate.parse(dateString, DATE_TIME_FORMATTER);
    }

    public static String format(LocalDate target) {
        return target.format(DATE_TIME_FORMATTER);
    }
}