package top.sharehome.demo04;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

/**
 * Vector集合类处理线程不安全问题
 *
 * @author AntonyCheng
 */
public class Demo04_2 {
    /**
     * 定义Vector集合
     */
    private static final List<String> LIST = new Vector<String>();

    /**
     * 多个线程同时对集合进行修改
     */
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                LIST.add(UUID.randomUUID().toString());
                System.out.println(LIST);
            }, "线程" + i).start();
        }
    }

}
