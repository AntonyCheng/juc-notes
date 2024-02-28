package top.sharehome.demo10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors.newScheduledThreadPool创建支持可定时任务线程池
 *
 * @author AntonyCheng
 */
public class Demo10_4 {

    public static void main(String[] args) {
        // 创建支持可定时任务线程池
        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        try {
            // 使用线程池执行任务
            for (int i = 0; i < 10; i++) {
                scheduledThreadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "线程正在运行...");
                });
            }
        } finally {
            // 关闭支持可定时任务线程池
            scheduledThreadPool.shutdown();
        }
    }

}
