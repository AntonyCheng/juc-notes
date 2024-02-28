package top.sharehome.demo10;

import java.util.concurrent.*;

/**
 * Executors.newCachedThreadPool创建可缓存线程池
 *
 * @author AntonyCheng
 */
public class Demo10_1 {

    public static void main(String[] args) {
        // 创建可缓存线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        try {
            // 使用线程池执行任务
            for (int i = 0; i < 10; i++) {
                cachedThreadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "线程正在运行...");
                });
            }
        } finally {
            // 关闭可缓存线程池
            cachedThreadPool.shutdown();
        }
    }

}
