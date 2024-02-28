package top.sharehome.demo10;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors.newWorkStealingPool创建基于ForkJoinPool实现的线程池（方法名就是ForkJoin底层算法名，即“工作窃取”算法）
 *
 * @author AntonyCheng
 */
public class Demo10_5 {

    public static void main(String[] args) {
        // 创建基于ForkJoinPool实现的线程池
        ExecutorService workStealingPool = Executors.newWorkStealingPool(5);
        try {
            // 使用线程池执行任务
            for (int i = 0; i < 10; i++) {
                workStealingPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "线程正在运行...");
                });
            }
            // 因为新开线程都是守护线程，所以让主线程睡3s看看效果
            System.out.println(Thread.currentThread().getName() + "等一下...");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "不等了!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭基于ForkJoinPool实现的线程池
            workStealingPool.shutdownNow();
        }
    }

}
