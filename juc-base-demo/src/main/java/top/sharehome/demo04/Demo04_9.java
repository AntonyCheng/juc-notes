package top.sharehome.demo04;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 对于Map：ConcurrentHashMap集合类处理线程不安全问题
 *
 * @author AntonyCheng
 */
public class Demo04_9 {
    /**
     * 定义ConcurrentHashMap集合
     */
    private static final Map<Integer, String> MAP = new ConcurrentHashMap<Integer, String>();

    /**
     * 多个线程同时对集合进行修改
     */
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int key = i;
            new Thread(() -> {
                MAP.put(key, UUID.randomUUID().toString());
                System.out.println(MAP);
            }, "线程" + i).start();
        }
    }

}
