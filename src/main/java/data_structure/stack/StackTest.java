package data_structure.stack;

import java.util.*;

/**
 * @date: 2021/6/23 9:22
 * @author: chen
 * @desc: 利用栈的特性检查括号是否成对出现
 * 给定 ( ) {}  [] 组成的的字符串，判断是否有效——左括号必须有相同类型的右括号闭合，左括号必须以正确的顺序闭合
 * 如 "()"  "()[]" "{[]}"都是有效的
 * 除此之外，栈还可以应用在比如浏览器前进回退、字符串反转等实际场景，当然，方法调用肯定是栈来实现的
 *
 */
public class StackTest {
    public static void main(String[] args) {
        boolean res = isValid("]");
        System.out.println(res);
    }


    /**
     * 思路：
     * 创建一个栈。遍历字符串，如果字符是左括号就直接加入stack中，否则将 stack 的栈顶元素与这个括号做比较，如果不相等就直接返回 false。遍历结束，如果stack为空，返回 true。
     *
     * 代码编写注意考虑异常极端情况，比如栈为空时弹栈会不会异常？
     */
    private static boolean isValid(String s) {
        HashMap<Character, Character> map = new HashMap<>(16);
        // 栈既可以通过数组实现，也可以通过链表来实现。不管基于数组还是链表，入栈、出栈的时间复杂度都为 O(1)。此处的Stack是继承于 Vector 的同步容器
        Stack<Character> stack = new Stack<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        char[] chars = s.toCharArray();
        for (Character c : chars) {
            if(map.containsKey(c)){
                stack.push(c);
            }else {
                Character top = stack.isEmpty() ? '#' : stack.pop();
                if(!Objects.equals(map.get(top),c)){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }




}
