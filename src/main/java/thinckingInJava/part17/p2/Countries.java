package thinckingInJava.part17.p2;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author chen
 * @date 2020/11/19 23:39
 */

@Slf4j
public class Countries {
    public static final String[][] DATA = {
            {"中国", "北京"}, {"米国", "华盛顿"}, {"日本", "东京"}, {"朝鲜", "平壤"}, {"俄罗斯", "莫斯科"}, {"巴西", "巴西利亚"}, {"英国", "伦敦"},
            {"澳大利亚", "堪培拉"}, {"埃塞俄比亚", "亚的斯亚贝巴"}, {"刚果民主共和国", "金莎萨"}, {"马达加斯加", "塔那那利佛"}, {"巴基斯坦", "伊斯兰堡"}
    };


    private static class FlyweightMap extends AbstractMap<String, String> {

        private  Set<Map.Entry<String, String>> entries = new EntrySet(DATA.length);

        /**
         * Entry<K, V> 是Map接口的内部接口，用于描述一个Map实体，通过该实体，可以获取到其键和值 常用Set(因为Set的不重复正式基于HashMap的键的唯一性)来装
         */
        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return entries;
        }

        private static class Entry implements Map.Entry<String, String> {
            int index;

            public Entry(int index) {
                this.index = index;
            }

            @Override
            public boolean equals(Object obj) {
                return DATA[index][0].equals(obj);
            }

            @Override
            public String getKey() {
                return DATA[index][0];
            }

            @Override
            public String getValue() {
                return DATA[index][1];
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int hashCode() {
                return DATA[index][0].hashCode();
            }
        }

        /**
         *  AbstractSet 的父级 AbstractCollection 有两个抽象方法
         *      size()  set 的初始数据容量
         *      iterator()  获取一个迭代器 此处的迭代器为自己内部构造的一个实现，而其迭代对象为Map 实体
         */
        static class EntrySet extends AbstractSet<Map.Entry<String, String>> {

            private int size;

            public EntrySet(int size) {
                if (size < 0)
                    this.size = 0;
                else if (size > DATA.length) {
                    this.size = DATA.length;
                } else {
                    this.size = size;
                }
            }


            @Override
            public int size() {
                return size;
            }

            private class Iter implements Iterator<Map.Entry<String, String>> {
                private FlyweightMap.Entry entry = new FlyweightMap.Entry(-1);

                @Override
                public boolean hasNext() {
                    return entry.index < size - 1;
                }

                @Override
                public Map.Entry<String, String> next() {
                    entry.index++;
                    return entry;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            }

            public Iterator<Map.Entry<String, String>> iterator() {
                return new Iter();
            }

        }
    }

    static Map<String, String> select(final int size) {
        return new FlyweightMap() {
            public Set<Map.Entry<String, String>> entrySet() {
                return new EntrySet(size);
            }
        };
    }

    static Map<String, String> map = new FlyweightMap();

    public static Map<String, String> capitals() {
        return map;
    }

    public static Map<String, String> capitals(int size) {
        return select(size);
    }

    static List<String> names = new ArrayList<>(map.keySet());

    public static List<String> names() {
        return names;
    }

    public static List<String> names(int size) {
        return new ArrayList<>(select(size).keySet());
    }

    public static void main(String[] args) {
        /**
         * 关键点在于自定义迭代器定义的迭代逻辑和对应集合抽象类实现 toString() 方法
         */
        log.info(capitals(5).toString());
        log.info(names(6).toString());
        log.info(new HashMap<>(capitals(3)).toString());
        log.info(new LinkedHashMap<>(capitals(3)).toString());
        log.info(new TreeMap<>(capitals(3)).toString());
        log.info(new Hashtable<>(capitals(3)).toString());
        log.info(new HashSet<>(names(3)).toString());
        log.info(new LinkedHashSet<>(names(3)).toString());
        log.info(new TreeSet<>(names(3)).toString());
        log.info(new ArrayList<>(names(3)).toString());
        log.info(new LinkedList<>(names(3)).toString());
        log.info(capitals().get("中国"));
    }

}
