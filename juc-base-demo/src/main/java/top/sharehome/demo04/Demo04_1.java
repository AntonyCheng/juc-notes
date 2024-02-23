package top.sharehome.demo04;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 线程不安全集合实例代码
 *
 * @author AntonyCheng
 */
public class Demo04_1 {
    private static ArrayList<String> uuidStrings = new ArrayList<>();

    /**
     * 多个线程同时对集合进行修改 * @param args
     */
    public static void main(String[] args) {
        List list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, "线程" + i).start();
        }
    }

}
