package top.sharehome.demo04;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 对于Set：ConcurrentSkipListSet集合类处理线程不安全问题
 *
 * @author AntonyCheng
 */
public class Demo04_5 {
    /**
     * 定义ConcurrentSkipListSet集合
     */
    private static final Set<String> SET = new ConcurrentSkipListSet<String>();

    /**
     * 多个线程同时对集合进行修改
     */
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                SET.add(UUID.randomUUID().toString());
                System.out.println(SET);
            }, "线程" + i).start();
        }
    }

}
