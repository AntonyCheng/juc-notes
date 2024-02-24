package top.sharehome.demo04;

import java.util.*;

/**
 * Collections工具类处理线程不安全问题
 *
 * @author AntonyCheng
 */
public class Demo04_3 {
    /**
     * 定义线程不安全集合
     */
    private static final List<String> LIST = new ArrayList<>();

    /**
     * 多个线程同时对集合进行修改
     */
    public static void main(String[] args) {
        // 使用Collections工具类对线程不安全的集合实例进行包装
        // 包装之后得到的集合就是一个线程安全的集合
        List<String> synchronizedList = Collections.synchronizedList(LIST);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                synchronizedList.add(UUID.randomUUID().toString());
                System.out.println(synchronizedList);
            }, "线程" + i).start();
        }
    }

}
