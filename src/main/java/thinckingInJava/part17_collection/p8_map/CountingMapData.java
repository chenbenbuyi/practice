package thinckingInJava.part17_collection.p8_map;

import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 享元 AbstractMap,有一个方法是必须实现的，就是entrySet
 *
 */
public class CountingMapData extends AbstractMap<Integer,String> {
  private int size;
  private static String[] chars =  "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
  public CountingMapData(int size) {
    if(size < 0) this.size = 0;
    this.size = size;
  }

  public Set<Map.Entry<Integer,String>> entrySet() {
    Set<Map.Entry<Integer,String>> entries = new LinkedHashSet<>();
    for(int i = 0; i < size; i++)
      entries.add(new Entry(i));
    return entries;
  }

  private static class Entry implements Map.Entry<Integer,String> {
    int index;
    Entry(int index) {
      this.index = index;
    }
    public boolean equals(Object o) {
      return Integer.valueOf(index).equals(o);
    }
    public Integer getKey() {
      return index;
    }
    public String getValue() {
      return chars[index % chars.length] +  Integer.toString(index / chars.length);
    }

    /**
     * 只读
     */
    public String setValue(String value) {
      throw new UnsupportedOperationException();
    }
    public int hashCode() {
      return Integer.valueOf(index).hashCode();
    }
  }

  public static void main(String[] args) {
    System.out.println(new CountingMapData(60));
  }
} 