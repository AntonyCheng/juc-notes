package top.sharehome.demo04;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 对于List：CopyOnWriteArrayList类处理线程不安全问题
 *
 * @author AntonyCheng
 */
public class Demo04_4 {
    /**
     * 定义CopyOnWriteArrayList集合
     */
    private static final List<String> LIST = new CopyOnWriteArrayList<String>();

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
