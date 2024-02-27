package top.sharehome.demo07;

import java.util.concurrent.CountDownLatch;

/**
 * 辅助类：减少计数CountDownLatch
 *
 * @author AntonyCheng
 */
public class Demo07_1 {

    /**
     * 6个同学陆续离开教室后值班同学才可以关门
     */
    public static void main(String[] args) {
        // 定义数值为6的计数器
        CountDownLatch countDownLatch = new CountDownLatch(6);

        // 模拟6名同学离开
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "：已经离开");
                countDownLatch.countDown();
            }, "同学" + (i + 1)).start();
        }

        // 模拟关门操作
        try {
            // 等待6名同学离开
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName()+"：负责关门");
    }

}
