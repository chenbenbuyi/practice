package data_structure.hash;

import org.junit.jupiter.api.Test;

/**
 * @date: 2021/3/30 12:00
 * @author: chen
 * @desc:
 */
public class HashTest {

    /**
     * 为什么槽位数必须使用2^n ?
     * 由于 hashCode 返回的是 int 值，在有限槽位情况下要定位元素存放的数组索引，首先想到的就是取余操作。
     * 重点来了：“取余(%)操作中如果除数是2的幂次则等价于与其除数减一的与(&)操作（也就是说 hash % length == hash & (length - 1) 的前提是 length 是 2 的 n 次方）。
     * 而二进制位操作 &，相对于 % 能够提高运算效率，这就解释了 HashMap 的长度为什么是 2 的幂次方
     */
    @Test
    public void remainderTest(){
        System.out.println(343545%16==(343545&(16-1)));
        System.out.println(343545^343545>>16);
    }

}
