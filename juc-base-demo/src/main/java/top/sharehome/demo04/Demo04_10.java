package top.sharehome.demo04;

import java.util.*;

/**
 * 对于Map：Collections工具类处理线程不安全问题
 *
 * @author AntonyCheng
 */
public class Demo04_10 {
    /**
     * 定义线程不安全集合
     */
    private static final Map<Integer, String> MAP = new HashMap<Integer, String>();

    /**
     * 多个线程同时对集合进行修改
     */
    public static void main(String[] args) {
        // 使用Collections工具类对线程不安全的集合实例进行包装
        // 包装之后得到的集合就是一个线程安全的集合
        Map<Integer, String> synchronizedMap = Collections.synchronizedMap(MAP);
        for (int i = 0; i < 100; i++) {
            int key = i;
            new Thread(() -> {
                synchronizedMap.put(key, UUID.randomUUID().toString());
                System.out.println(synchronizedMap);
            }, "线程" + i).start();
        }
    }

}
