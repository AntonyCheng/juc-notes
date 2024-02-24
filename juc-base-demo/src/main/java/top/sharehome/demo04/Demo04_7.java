package top.sharehome.demo04;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 对于Set：CopyOnWriteArraySet类处理线程不安全问题
 *
 * @author AntonyCheng
 */
public class Demo04_7 {
    /**
     * 定义CopyOnWriteArraySet集合
     */
    private static final Set<String> SET = new CopyOnWriteArraySet<String>();

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
