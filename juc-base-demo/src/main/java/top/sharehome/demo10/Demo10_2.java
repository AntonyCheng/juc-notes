package top.sharehome.demo10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors.newFixedThreadPool创建固定数量线程池
 *
 * @author AntonyCheng
 */
public class Demo10_2 {

    public static void main(String[] args) {
        // 创建固定数量线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        try {
            // 使用线程池执行任务
            for (int i = 0; i < 10; i++) {
                fixedThreadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "线程正在运行...");
                });
            }
        } finally {
            // 关闭固定数量线程池
            fixedThreadPool.shutdown();
        }
    }

}
