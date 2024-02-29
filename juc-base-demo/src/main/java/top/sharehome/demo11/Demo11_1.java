package top.sharehome.demo11;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池示例
 * 场景火车站有3个窗口，一共10个用户买票。
 *
 * @author AntonyCheng
 */
public class Demo11_1 {

    public static void main(String[] args) {
        // 定义线程数量为3的固定线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        // 使用原子类定义总金额，原子类可以看作是基本类型的线程安全包装类
        AtomicInteger totalCost = new AtomicInteger(0);
        try {
            // 模拟十个人来买票
            for (int i = 0; i < 10; i++) {
                Integer costFuture = fixedThreadPool.submit(() -> {
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
            fixedThreadPool.shutdown();
        }
    }

}
