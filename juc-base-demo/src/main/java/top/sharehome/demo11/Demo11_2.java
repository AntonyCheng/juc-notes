package top.sharehome.demo11;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池示例
 * 场景火车站有3个窗口，一共10个用户买票。
 *
 * @author AntonyCheng
 */
public class Demo11_2 {

    public static void main(String[] args) {
        // 自定义线程池
        ThreadPoolExecutor customizePool = new ThreadPoolExecutor(
                // 核心线程数为2
                2,
                // 最大线程数为5
                5,
                // 空闲线程存活时间为2分钟
                2L,
                TimeUnit.SECONDS,
                // 使用数组阻塞队列缓存请求
                new ArrayBlockingQueue<>(3),
                // 使用默认线程工厂
                Executors.defaultThreadFactory(),
                // 使用默认拒绝策略
                new ThreadPoolExecutor.AbortPolicy());
        // 使用原子类定义总金额，原子类可以看作是基本类型的线程安全包装类
        AtomicInteger totalCost = new AtomicInteger(0);
        try {
            // 模拟十个人来买票
            for (int i = 0; i < 10; i++) {
                Integer costFuture = customizePool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "窗口正在办理售票业务...");
                    Thread.sleep(1000);
                    int cost = new Random().nextInt(50);
                    System.out.println(Thread.currentThread().getName() + "售出一张车票，赚取 " + cost + " 元");
                    return cost;
                }).get();
                totalCost.addAndGet(costFuture);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("总收入金额为：" + totalCost);
            customizePool.shutdown();
        }
    }

}
