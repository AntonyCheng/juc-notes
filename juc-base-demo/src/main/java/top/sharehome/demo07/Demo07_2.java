package top.sharehome.demo07;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 辅助类：循环栅栏CyclicBarrier
 *
 * @author AntonyCheng
 */
public class Demo07_2 {

    /**
     * 集齐7颗龙珠才能召唤神龙
     */
    public static void main(String[] args) {
        // 定义域值为7的循环栅栏，并且达到域值之后召唤神龙
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println(Thread.currentThread().getName() + "：龙珠收集完成，就地召唤神龙！");
        });

        // 模拟找到七个龙珠
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "：收集到龙珠！");
                    // 等待七颗龙珠全部收集完
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }, "第" + (i + 1) + "个人").start();
        }
    }

}
