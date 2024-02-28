package top.sharehome.demo10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors.newSingleThreadExecutor创建单线程池处理器
 *
 * @author AntonyCheng
 */
public class Demo10_3 {

    public static void main(String[] args) {
        // 创建单线程池处理器
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        try {
            // 使用线程池执行任务
            for (int i = 0; i < 10; i++) {
                singleThreadExecutor.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "线程正在运行...");
                });
            }
        } finally {
            // 关闭单线程池处理器
            singleThreadExecutor.shutdown();
        }
    }

}
