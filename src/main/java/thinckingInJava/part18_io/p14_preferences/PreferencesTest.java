package thinckingInJava.part18_io.p14_preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author chen
 * @date 2021/1/6 0:12
 * @Description Preferences提供一个存储配置信息的中心知识库，与平台无关。
 * 在Windows系统中，它存储在注册表中，在Linux中存储在本地文件系统中。它的实现是透明的，程序员无需深究它的底层是如何实现的。
 * 这种持久化和对象的序列化相比，只能用于小的、受限的数据集合，且只能存储基本数据类型和字符串，每个字符串的长度不能超过 8k
 * Preferences api 常用来存储和读取用户的偏好以及程序运行的配置项
 */
public class PreferencesTest {
    public static void main(String[] args) throws BackingStoreException {
        Preferences preferences = Preferences.userNodeForPackage(PreferencesTest.class);
        preferences.put("blogs", "陈本布衣");
        preferences.putInt("age", 100);
        preferences.putBoolean("ok", false);
        System.out.println(preferences.getBoolean("ok",true));
        preferences.remove("ok");
        System.out.println(preferences.getBoolean("ok",true));
        String[] keys = preferences.keys();
        for (String key : keys) {
            System.out.println(key+":"+preferences.get(key, "默认值"));
        }
    }
}
