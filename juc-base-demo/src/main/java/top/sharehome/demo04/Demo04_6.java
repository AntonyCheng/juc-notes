package top.sharehome.demo04;

import java.util.*;

/**
 * 对于Set：Collections工具类处理线程不安全问题
 *
 * @author AntonyCheng
 */
public class Demo04_6 {
    /**
     * 定义线程不安全集合
     */
    private static final Set<String> SET = new HashSet<String>();

    /**
     * 多个线程同时对集合进行修改
     */
    public static void main(String[] args) {
        // 使用Collections工具类对线程不安全的集合实例进行包装
        // 包装之后得到的集合就是一个线程安全的集合
        Set<String> synchronizedSet = Collections.synchronizedSet(SET);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                synchronizedSet.add(UUID.randomUUID().toString());
                System.out.println(synchronizedSet);
            }, "线程" + i).start();
        }
    }

}
